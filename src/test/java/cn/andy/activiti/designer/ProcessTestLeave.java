package cn.andy.activiti.designer;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @Author: zhuwei
 * @Date:2018/10/18 16:59
 * @Description:
 */
public class ProcessTestLeave {

    private String filename = "F:\\IT\\activiti-demo\\src\\main\\resources\\cn.andy.activiti\\diagrams\\Leave.bpmn";

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void startProcess() throws Exception {
        RepositoryService repositoryService = activitiRule.getRepositoryService();

//        repositoryService.createDeployment().addInputStream();
    }


}
