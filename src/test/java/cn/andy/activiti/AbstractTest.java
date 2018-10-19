package cn.andy.activiti;

import org.activiti.engine.*;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Rule;

/**
 * @Author: zhuwei
 * @Date:2018/10/19 11:55
 * @Description: 抽象了流程测试中相关的方法
 */
public abstract class AbstractTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    protected IdentityService identityService;

    protected FormService formService;

    protected HistoryService historyService;

    protected ManagementService managementService;

    protected RepositoryService repositoryService;

    protected RuntimeService runtimeService;

    protected TaskService taskService;

    @Before
    public void setUp() throws Exception{
        identityService = activitiRule.getIdentityService();
        formService = activitiRule.getFormService();
        historyService = activitiRule.getHistoryService();
        managementService = activitiRule.getManagementService();
        repositoryService = activitiRule.getRepositoryService();
        runtimeService = activitiRule.getRuntimeService();
        taskService = activitiRule.getTaskService();
    }
}
