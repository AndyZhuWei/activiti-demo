package cn.andy.activiti.designer;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * @Author: zhuwei
 * @Date:2018/10/18 16:59
 * @Description:
 */
public class ProcessTestLeave {

//    private String filename = "F:\\IT\\activiti-demo\\src\\main\\resources\\cn.andy.activiti\\diagrams\\Leave.bpmn";

    private String filename = "E:\\workspace\\activiti-demo\\src\\main\\resources\\cn.andy.activiti\\diagrams\\Leave.bpmn";
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void startProcess() throws Exception {
        RepositoryService repositoryService = activitiRule.getRepositoryService();

       repositoryService.createDeployment().addInputStream(
               "leave.bpmn20.xml",new FileInputStream(filename)).deploy();

        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("name","Activiti");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave",variableMap);
        assertNotNull(processInstance.getId());
        System.out.println("id"+processInstance.getId()+" "+processInstance.getProcessDefinitionId());
    }


}
