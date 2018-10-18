package cn.andy.activiti;

import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import static org.junit.Assert.assertEquals;

public class MathTest {

    @Rule
	public TestRule testRule = new DemoTestRule();
	
	public MathTest(){
		System.out.println("Create MathTest instance ...");
	}
 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Call @BeforeClass");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Call @AfterClass");
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Call @Before before a test case!");
	}
 
	@After
	public void tearDown() throws Exception {
		System.out.println("Call @After after a test case!");
	}
 
	@Test
	public void testAbs() {
		Math math = new Math();
        assertEquals(200, math.abs(200));
        assertEquals(100, math.abs(-100));
        assertEquals(0, math.abs(0));
	}
 
	@Test
	public void testDiv() {
		Math math = new Math();
		assertEquals(2,math.div(9, 4));
		assertEquals(5,math.div(100, 20));
	}
 
	@Test
	public void testExp() {
		 Math math = new Math();
		 assertEquals(32f, math.exp(2, 5), 0.001f);
	     assertEquals(1f, math.exp(2, 0), 0.001f);
	     assertEquals(0.5f, math.exp(2, (-1)), 0.001f);
	}
	
	@Ignore("Not for the test class")
	@Test(timeout=1)
	public void testTimeLimitedExceeded() {
		double d = 0;
		for(int i = 0;i < 10000000; i ++)
			d += i;
	}
	
	@Test(expected=ArithmeticException.class)
	public void testDivByZero() {
		System.out.println("Started test divided by 0...");
		new Math().div(1, 0);
	}
	
	public static void main(String args[]){
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new RunListener() {
			public void testStarted(Description description) throws Exception {
				System.out.println(description.getDisplayName() + "...started");
			}
			public void testIgnored(Description description) throws Exception {
				System.out.println(description.getDisplayName() + "...failed");
			}
			});
		Result result = junitCore.runClasses(MathTest.class);
		for(Failure failure : result.getFailures())
			System.out.println(failure.getTestHeader()+":"+failure.getMessage());
	}
 
}