package cn.andy.activiti.identify;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * @Author: zhuwei
 * @Date:2018/10/19 11:13
 * @Description: 用户管理的API演示
 */
public class IdentifyServiceTest  {

    @Rule
    //当创建ActivitiRule对象时会自动创建一个引擎对象ProcessEngine
    //使用默认的activiti.cfg.xml作为参数
    public ActivitiRule activitiRule = new ActivitiRule();

    //用户管理的API演示
    @Test
    public void testUser() throws Exception {
        //获取IdentifyService实例
        IdentityService identifyService = activitiRule.getIdentityService();
        //创建一个用户对象
        User user = identifyService.newUser("henryyan");//ID为henryyan
        user.setFirstName("Henry");
        user.setLastName("Yan");
        user.setEmail("yanhonglei@gmail.com");
        //保存用户到数据库
        identifyService.saveUser(user);
        //验证用户是否保存成功
        User userInDb = identifyService.createUserQuery()
                        .userId("henryyan").singleResult();
        assertNotNull(userInDb);
        //删除用户
        identifyService.deleteUser("henryyan");
        //验证是否删除成功
        userInDb = identifyService.createUserQuery().userId("henryyan").singleResult();
        assertNull(userInDb);
    }

    //组管理的API演示
    //在Activiti中，组的类型分为两种，即assignment和security-role。
    // 前者为一种普通的岗位角色，是用户分配业务中的功能权限
    //后者是安全角色，可以从全局管理用户组织及整个流程的状态
    @Test
    public void testGroup() throws Exception {
        //获取IdentifyService实例
        IdentityService identifyService = activitiRule.getIdentityService();
        //创建一个组对象
        Group group = identifyService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        //保存组
        identifyService.saveGroup(group);
        //验证组是否保存成功，首先需要创建组查询对象
        List<Group> groupList = identifyService.createGroupQuery().groupId("deptLeader").list();
        assertEquals(1,groupList.size());
        //删除组
        identifyService.deleteGroup("deptLeader");
        //验证是否删除成功
        groupList = identifyService.createGroupQuery().groupId("deptLeader").list();
        assertEquals(0,groupList.size());
    }

    //组和用户关联关系API演示
    @Test
    public void testUserAndGroupMembership() throws Exception {
        //获取IdentifyService实例
        IdentityService identifyService = activitiRule.getIdentityService();
        //创建并保存组对象
        Group group = identifyService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identifyService.saveGroup(group);

        //创建并保存用户对象
        User user = identifyService.newUser("henryyan");
        user.setFirstName("Henry");
        user.setLastName("Yan");
        user.setEmail("yanhonglei@gmail.com");
        identifyService.saveUser(user);
        //把用户henryyan加入到组deptLeader中
        identifyService.createMembership("henryyan","deptLeader");
        //查询属于组deptLeader的用户
        User userInGroup = identifyService.createUserQuery().memberOfGroup("deptLeader").singleResult();
        assertNotNull(userInGroup);
        assertEquals("henryyan",userInGroup.getId());
        //查询henryyan所属组
        Group groupContainsHenryyan =
                identifyService.createGroupQuery().groupMember("henryyan").singleResult();
        assertNotNull(groupContainsHenryyan);
        assertEquals("deptLeader",groupContainsHenryyan.getId());
    }
}
