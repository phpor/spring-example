package hello.cmd.context;

import hello.beans.HttpClientImpl;
import hello.interfaces.IHttp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.URISyntaxException;

// @SpringBootConfiguration 是 @Configuration 的子注解

@SpringBootApplication
public class ContextApplication {
    public static void main(String[] args) {
        test1();
    }

    // 关于Bean被上下文管理的逻辑
    public static void test1() {
        // 通过@ComponentScan的方式向上下文注册Bean
        ApplicationContext ctx = new AnnotationConfigApplicationContext(appconfig.class);

        IHttp http = ctx.getBean(IHttp.class); // 这个写法比较正规些, 不好的写法： IHttp http = (IHttp)ctx.getBean("httpClientImpl");

        try {
            System.out.print(http.request("http://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    // 注意AnnotationConfigApplicationContext 与 ApplicationContext 的差异，前者有register方法，后者没有
    public static void test2() {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ContextApplication.class);
        // 通过register方法向上下文注册Bean
        ctx.register(HttpClientImpl.class);

        IHttp http = ctx.getBean(IHttp.class); // 这个写法比较正规些

        try {
            System.out.print(http.request("http://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @ComponentScan("hello.beans")
    static class appconfig {}   // 这个类需要是static的，可以不需要Configuration注解
}
