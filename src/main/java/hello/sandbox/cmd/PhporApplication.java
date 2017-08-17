package hello.sandbox.cmd;


import hello.interfaces.IHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * 一个命令行版本的spring应用
 * web 环境下也会执行CommandLineRunner的，而且是在应用部署完成之后运行的，所以，可以通过CommandLineRunner做一些启动后的事情
 * 比如： 服务注册
 */

@SpringBootApplication
@Configuration
@ComponentScan("hello.beans")
public class PhporApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PhporApplication.class);
        app.setWebEnvironment(false);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    @Order
    public CommandLineRunner demo1() {
        System.out.println("test");
        return args -> {
            System.out.println(String.join(" ", args));
        };
    }

    @Autowired
    IHttp http;


    public void test_http(IHttp http) {
        try {
            System.out.println(http.request("http://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void test_sql() {

    }
}
