package cn.andy.activiti.deployment;

import cn.andy.activiti.AbstractTest;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: zhuwei
 * @Date:2018/10/19 13:25
 * @Description: 用classpath方式部署流程定义文件
 */
public class ClasspathDeploymentTest extends AbstractTest {

    @Test
    public void testClasspathDeployment() throws Exception {
        //定义classpath
        String bpmnClasspath = "cn.andy.activiti/diagrams/userAndGroupInUserTask.bpmn";
        String pngClasspath = "cn.andy.activiti/diagrams/userAndGroupInUserTask.png";
        //创建部署构建器
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //添加资源
        deploymentBuilder.addClasspathResource(bpmnClasspath);
        deploymentBuilder.addClasspathResource(pngClasspath);
        //执行部署
        deploymentBuilder.deploy();
        //验证是否部署成功
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        long count = processDefinitionQuery.processDefinitionKey("userAndGroupInUserTask").count();
        assertEquals(1,count);
        //读取图片文件
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        String diagramResourceName = processDefinition.getDiagramResourceName();
        assertEquals(pngClasspath,diagramResourceName);

    }
}
