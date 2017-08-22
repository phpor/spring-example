package hello.sandbox.context;

import hello.beans.HttpClientImpl;
import hello.interfaces.ICmd;
import hello.interfaces.IHttp;
import org.apache.commons.cli.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContextApplication {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(Arrays.asList(args));
        list.add("-c");
        list.add("dataSourceCmd");
        list.remove("--spring.output.ansi.enabled=always");
        args = list.toArray(new String[0]);

        Options options = new Options();
        options.addOption(new Option("c", "help", true, "case of test"));
        options.addOption(new Option("h", false, "show this help"));

        CommandLineParser commandLineParser = new PosixParser();
        CommandLine cmdline = null;
        try {
            cmdline = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        String what = cmdline.getOptionValue("c");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(cmdconfig.class);
        ICmd cmd = ctx.getBean(what, ICmd.class);
        cmd.run(args);
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

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 通过register方法向上下文注册Bean， 可以直接注册需要的Bean也可以注册一个带有注解（@ComponentScan）的类
        ctx.register(HttpClientImpl.class);
        //ctx.register(appconfig.class);
        ctx.refresh();      // register 完需要refresh
        IHttp http = ctx.getBean(IHttp.class);

        try {
            System.out.print(http.request("http://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @ComponentScan("hello.beans")
    static class appconfig {}   // 这个类需要是static的，可以不需要Configuration注解


    @EnableAutoConfiguration
    @ComponentScan({ "hello.cmds", "hello.beans"})
    static class cmdconfig {

        // 数据源的定义
        @Bean
        public DataSource getDataSource() {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://172.16.22.37/test");
            ds.setUsername("spring");
            ds.setPassword("spring-password");
            return ds;
        }
    }
}
