package cn.andy.activiti;

import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;


public class DemoTestRule implements TestRule {
    public Statement apply(final Statement base,
                           final org.junit.runner.Description description) {
        return new Statement(){
            @Override
            public void evaluate() throws Throwable{
                try{
                    base.evaluate();
                }
                catch(Throwable t){
                    System.out.println("Test rule suggest that "+description.getMethodName()+" failed");
                    throw t;
                }
            }
        };
    }
}