package hello.cmd;


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


/**
 * 一个命令行版本的spring应用
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

    @Bean
    @Order
    public CommandLineRunner demo2() {
        return args -> {
            System.out.println(http.request("http://baidu.com"));
        };
    }
}
