package com.example.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenzhiqin
 * @date 12/4/2023 15:55
 * @info XX
 */
@SpringBootTest
@Slf4j
public class TestApplication {

    static private ProcessEngine processEngine;

    static private RepositoryService repositoryService;


    private final String PROCESS_ID = "45001";

    private static StandaloneProcessEngineConfiguration configuration;

    private static TaskService taskService;


    @BeforeAll
    static public void createFlowAble() {
        configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcUrl("jdbc:mysql://192.168.75.128:3306/flowable?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2b8&nullCatalogMeansCurrent=true");
        configuration.setJdbcDriver("com.mysql.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = configuration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
    }

    @Test
    public void deploy() {
        DeploymentBuilder deployment = repositoryService.createDeployment();
        Deployment deploy = deployment.addClasspathResource("holiday-request.bpmn20.xml").category("人事流程管理").name("请假流程").key("holiday-request").deploy();
        log.info("deploy id  " + deploy.getId());
        log.info("deploy name  " + deploy.getName());
        log.info("deploy  category " + deploy.getCategory());

    }


    @Test
    public void query() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId(PROCESS_ID).singleResult();
        log.info("Found process definition : " + processDefinition.getName());
        log.info("Found process definition : " + processDefinition.getKey());
        log.info("Found process definition : " + processDefinition.getCategory());
        log.info("Found process definition : " + processDefinition.getId());
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(PROCESS_ID).singleResult();
        log.info("Found process deployment : " + deployment.getName());
        log.info("Found process deployment : " + deployment.getKey());
        log.info("Found process deployment : " + deployment.getCategory());
        log.info("Found process deployment : " + deployment.getId());
    }


    @Test
    public void delete() {
        repositoryService.deleteDeployment(PROCESS_ID, true);
    }


    /**
     * 开启一个流程实例
     */
    @Test
    public void startInstance() {
        RuntimeService runtimeService = configuration.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "wangwu");
        variables.put("duration", 5);
        variables.put("description", "go climbing");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        String deploymentId = processInstance.getDeploymentId();
        String activityId = processInstance.getActivityId();
        System.out.println("Deployment Id: " + deploymentId + " Activity Id: " + activityId);
    }


    @Test
    public void queryInstance() {
        taskService = configuration.getTaskService();
        List<Task> list = taskService.createTaskQuery().processDefinitionKeyLike("holidayRequest").taskAssignee("zhangsan").list();

        for (Task task : list) {
            String processInstanceId = task.getProcessInstanceId();
            String name = task.getName();
            String assignee = task.getAssignee();
            String description = task.getDescription();
            String id = task.getId();
            System.out.println("processInstanceId: " + processInstanceId + " name: " + name + " assignee: " + assignee + " description: " + description + " id: " + id);
        }

    }

    @Test
    public void rejectTask() {
        taskService = configuration.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKeyLike("holidayRequest").taskAssignee("zhangsan").singleResult();
        Map<String, Object> variables = new HashMap<>();

        Object employee = variables.get("employee");
        if ("wangwu".equals(employee)) {
            variables.put("approved", true);
        } else {
            variables.put("approved", false);
        }
        TestApplication.taskService.complete(task.getId(), variables);
    }

    @Test
    public void queryHistory() {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery activityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        // 查询结束的流程信息
        List<HistoricActivityInstance> activityInstances = activityInstanceQuery.processDefinitionId("holidayRequest:1:4").finished().orderByHistoricActivityInstanceEndTime().desc().list();
        for (HistoricActivityInstance instance : activityInstances) {
            String activityName = instance.getActivityName();
            String activityId = instance.getActivityId();
            Long durationInMillis = instance.getDurationInMillis();
            System.out.println("Activity: " + activityName + " activityId:" + activityId + " durationInMillis:" + durationInMillis);
        }
    }


}
