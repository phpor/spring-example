package hello.sandbox.context;

import hello.beans.HttpClientImpl;
import hello.cmds.BookCmd;
import hello.dao.interfaces.IBook;
import hello.interfaces.ICmd;
import hello.interfaces.IHttp;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;

public class ContextApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(cmdconfig.class);
        ICmd cmd = ctx.getBean("bookCmd", ICmd.class);
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
    @ComponentScan({ "hello.cmds", "hello.beans", "hello.dao"})
    static class cmdconfig {

        // 数据源的定义
        @Bean
        public DataSource getDataSource() {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://172.16.22.37/test?characterEncoding=UTF-8");
            ds.setUsername("spring");
            ds.setPassword("spring-password");
            return ds;
        }

        @Bean
        @Primary
        @Autowired
        public SqlSession getSqlSession(DataSource dataSource) {
            TransactionFactory transactionFactory = new JdbcTransactionFactory();

            Configuration configuration = new Configuration(new Environment("default", transactionFactory, dataSource));
            configuration.addMapper(IBook.class); // todo: 如何自动扫描mapper？

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            return sqlSessionFactory.openSession();
        }

        @Bean("bookCmd")
        public ICmd getIcmd() {
            return new BookCmd();
        }
    }
}


