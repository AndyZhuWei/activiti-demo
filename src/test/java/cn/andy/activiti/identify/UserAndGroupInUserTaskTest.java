package cn.andy.activiti.identify;

import cn.andy.activiti.AbstractTest;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * @Author: zhuwei
 * @Date:2018/10/19 11:54
 * @Description: 单元测试中封装了用户、组的初始化及清理方法
 */
public class UserAndGroupInUserTaskTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
        //初始化7个Service实例
        super.setUp();
        //创建并保存组对象
        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);
        //创建并保存用户对象
        User user = identityService.newUser("henryyan");
        user.setFirstName("Henry");
        user.setLastName("Yan");
        user.setEmail("yanhonglei@gmail.com");
        identityService.saveUser(user);
        //把用户henryyan加入到组deptLeader中
        identityService.createMembership("henryyan","deptLeader");
    }

    //每个方法执行完后清理用户与组
    @After
    public void afterInvokeTestMethod() throws Exception {
        identityService.deleteMembership("henryyan","deptLeader");
        identityService.deleteGroup("deptLeader");
        identityService.deleteUser("henryyan");
    }

    //用户任务中的候选组
    @Test
    @Deployment(resources = {"cn.andy.activiti/diagrams/userAndGroupInUserTask.bpmn"})
    public void testUserAndGroupInUserTask() throws Exception {
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
        assertNotNull(processInstance);
        //根据角色查询任务
        //过滤包含候选人”henryyan“的用户任务
        Task task = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        taskService.claim(task.getId(),"henryyan");
        taskService.complete(task.getId());
    }

    //用户组中包含多个用户时在用户任务中的应用
    @Test
    @Deployment(resources = {"cn.andy.activiti/diagrams/userAndGroupInUserTask.bpmn"})
    public void testUserTaskWithGroupContainsTwoUser() throws Exception {
        //在setUp()的基础上再添加一个用户，然后加入到组deptLeader中
        User user = identityService.newUser("jackchen");
        user.setFirstName("Jack");
        user.setLastName("Chen");
        user.setEmail("jackchen@gmail.com");
        identityService.saveUser(user);
        //把用户jackchec加入到deptLeader中
        identityService.createMembership("jackchen","deptLeader");
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
        assertNotNull(processInstance);
        //jackchen作为候选人的任务
        Task jackchenTask = taskService.createTaskQuery()
                .taskCandidateUser("jackchen").singleResult();
        assertNotNull(jackchenTask);
        //henryyan作为候选人的任务
        Task henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        assertNotNull(henryyanTask);
        //jackchen签收任务
        taskService.claim(jackchenTask.getId(),"jackchen");
        //再次查询用户henryyan是否拥有刚刚的候选任务
        henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        assertNull(henryyanTask);
    }


}
