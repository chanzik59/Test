package com.example.on_java.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

/**
 * @author chenzhiqin
 * @date 2022/9/29 16:52
 * @info XX
 */
@Component
@Slf4j
public class MyScheduledConfig implements SchedulingConfigurer {

    private volatile ScheduledTaskRegistrar taskRegistrar;

    private final Map<Integer, ScheduledFuture<?>> scheduledMap = new ConcurrentHashMap<>();

    private final Map<Integer, CronTask> cronTasks = new ConcurrentHashMap<>();

    @Resource
    private TaskChooser taskChooser;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
        this.taskRegistrar = taskRegistrar;
    }


    public void batchRefreshTasks(List<TaskEntity> taskEntities) {
        Set<Integer> taskIds = scheduledMap.keySet();
//      不在列表中的任务关停
        taskIds.stream().filter(v -> !existsInTasks(taskEntities, v)).forEach(v -> scheduledMap.get(v).cancel(false));
        for (TaskEntity taskEntity : taskEntities) {
            refreshTasks(taskEntity);
        }
    }

    /**
     * 单个加载定时任务
     *
     * @param taskEntity
     */
    public void refreshTasks(TaskEntity taskEntity) {
        if (!StringUtils.hasLength(taskEntity.getExpression())) {
            return;
        }
        if (scheduledMap.containsKey(taskEntity.getTaskId()) && cronTasks.get(taskEntity.getTaskId()).getExpression().equals(taskEntity.getExpression())) {
            return;
        }
        if (scheduledMap.containsKey(taskEntity.getTaskId())) {
            scheduledMap.get(taskEntity.getTaskId()).cancel(false);
            scheduledMap.remove(taskEntity.getTaskId());
            cronTasks.remove(taskEntity.getTaskId());
        }
        CronTask cronTask = makeCronTask(taskEntity);
        if (Objects.isNull(cronTask)) {
            log.warn("该定时任务不存在{}", taskEntity);
            return;
        }
        ScheduledFuture<?> schedule = taskRegistrar.getScheduler().schedule(cronTask.getRunnable(), cronTask.getTrigger());
        cronTasks.put(taskEntity.getTaskId(), cronTask);
        scheduledMap.put(taskEntity.getTaskId(), schedule);

    }


    /**
     * 批量停止任务
     *
     * @param taskEntities
     */
    public void batchStopTasks(List<TaskEntity> taskEntities) {
        taskEntities.stream().map(TaskEntity::getTaskId).forEach(this::stopTasks);
    }


    /**
     * 停止任务 并且移除
     *
     * @param taskId
     */
    public void stopTasks(Integer taskId) {
        if (scheduledMap.containsKey(taskId)) {
            scheduledMap.get(taskId).cancel(false);
            scheduledMap.remove(taskId);
        }
    }

    /**
     * 判断已存在任务是否在列表中
     *
     * @param taskEntities
     * @param taskId
     * @return
     */
    private boolean existsInTasks(List<TaskEntity> taskEntities, Integer taskId) {
        return taskEntities.stream().filter(v -> v.getTaskId().equals(taskId)).count() > 0;
    }

    /**
     * 包装逻辑
     *
     * @param taskEntity
     * @return
     */
    private CronTask makeCronTask(TaskEntity taskEntity) {
        return Optional.ofNullable(taskChooser.getTaskServiceById(taskEntity.getTaskId())).map(v -> new CronTask(() -> v.handleJob(), taskEntity.getExpression())).orElse(null);

    }

    @PreDestroy
    public void destroy() {
        this.taskRegistrar.destroy();
    }
}
