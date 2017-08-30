import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dingcheng.confirms.publish.PublishService;  
  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:application-context.xml"})  
public class TestConfirm {  
    @Autowired  
    private PublishService publishService;  
    
    private static String exChange = "DIRECT_EX";
      
    @Test
    public void testPool() {
      long start = System.currentTimeMillis();
      ExecutorService threadpool= Executors.newCachedThreadPool();  
      
      for(int i=1;i<=100;i++){  
            final int task = i;  
            threadpool.execute(  
                 new Runnable(){  
                     public void run(){  
                          for(int j=1;j<=100;j++){  
                              try {  
                                publishService.send(exChange,"CONFIRM_TEST","message-->" + task + j); 
                              } catch (Exception e) {  
                                  e.printStackTrace();  
                              }  
                          }  
                     }  
                 }  
                );  
      }  
      System.out.println("发送结束" + (System.currentTimeMillis() - start));

      BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));
      try {
        String str = fileReader.readLine();
        System.out.println(str+"->>>>>>>>>>    ending");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
  }  
    
    
    @Test  
    public void test1() throws InterruptedException{  
    	String message = "currentTime:"+System.currentTimeMillis();
    	System.out.println("test1---message:"+message);
    	//exchange,queue 都正确,confirm被回调, ack=true
    	publishService.send(exChange,"CONFIRM_TEST",message);  
        Thread.sleep(1000);
    }  
    
    @Test  
    public void test2() throws InterruptedException{  
    	String message = "currentTime:"+System.currentTimeMillis();
    	System.out.println("test2---message:"+message);
    	//exchange 错误,queue 正确,confirm被回调, ack=false
    	publishService.send(exChange+"NO","CONFIRM_TEST",message);  
        Thread.sleep(1000);
    }  
    
    @Test  
    public void test3() throws InterruptedException{  
    	String message = "currentTime:"+System.currentTimeMillis();
    	System.out.println("test3---message:"+message);
    	//exchange 正确,queue 错误 ,confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
    	publishService.send(exChange,"",message);      
        Thread.sleep(1000);
    }  
    
    @Test  
    public void test4() throws InterruptedException{  
    	String message = "currentTime:"+System.currentTimeMillis();
    	System.out.println("test4---message:"+message);
    	//exchange 错误,queue 错误,confirm被回调, ack=false
    	publishService.send(exChange+"NO","CONFIRM_TEST",message);  
        Thread.sleep(1000);
    }  
}  