package com.example.on_java.scheduled;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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


    public void refreshTasks(List<TaskEntity> taskEntities) {
        Set<Integer> taskIds = scheduledMap.keySet();
//      不在列表中的任务关停
        taskIds.stream().filter(v -> !existsInTasks(taskEntities, v)).forEach(v -> scheduledMap.get(v).cancel(false));
        for (TaskEntity taskEntity : taskEntities) {
            if (!StringUtils.hasLength(taskEntity.getExpression())) {
                continue;
            }
            if (scheduledMap.containsKey(taskEntity.getTaskId()) && cronTasks.get(taskEntity.getTaskId()).getExpression().equals(taskEntity.getExpression())) {
                continue;
            }
            if (scheduledMap.containsKey(taskEntity.getTaskId())) {
                scheduledMap.get(taskEntity.getTaskId()).cancel(false);
                scheduledMap.remove(taskEntity.getTaskId());
                cronTasks.remove(taskEntity.getTaskId());
            }
            CronTask cronTask = makeCronTask(taskEntity);
            ScheduledFuture<?> schedule = taskRegistrar.getScheduler().schedule(cronTask.getRunnable(), cronTask.getTrigger());
            cronTasks.put(taskEntity.getTaskId(), cronTask);
            scheduledMap.put(taskEntity.getTaskId(), schedule);
        }
    }

    /**
     * 停止任务 并且移除
     *
     * @param taskEntities
     */
    public void stopTasks(List<TaskEntity> taskEntities) {
        taskEntities.stream().map(TaskEntity::getTaskId).filter(scheduledMap::containsKey).forEach(v -> {
            scheduledMap.get(v).cancel(false);
            scheduledMap.remove(v);
        });
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
        return new CronTask(() -> {
            taskChooser.getTaskServiceById(taskEntity.getTaskId()).handleJob();
        }, taskEntity.getExpression());
    }

    @PreDestroy
    public void destroy() {
        this.taskRegistrar.destroy();
    }
}
