package cn.andy.activiti.deployment;

import cn.andy.activiti.AbstractTest;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

/**
 * @Author: zhuwei
 * @Date:2018/10/19 13:25
 * @Description: 用InputStream方式部署流程定义文件
 */
public class InputStreamDeploymentTest extends AbstractTest {

    //从一个绝对文件流部署
    @Test
    public void testInptStreamFromAbsoluteFilePath() throws Exception {
       String filePath = "F:\\IT\\activiti-demo\\src\\main\\resources\\cn.andy.activiti\\diagrams\\userAndGroupInUserTask.bpmn";
       //读取filePath的资源为一个输入流
        FileInputStream fileInputStream = new FileInputStream(filePath);
        repositoryService.createDeployment().addInputStream("userAndGroupInUserTask.bpmn",fileInputStream).deploy();

        //验证是否部署成功
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        long count = pdq.processDefinitionKey("userAndGroupInUserTask").count();
        assertEquals(1,count);

    }
}
