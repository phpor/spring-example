package hello.config;

import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
/**
 * It only a rabish
 */

public class RedisHttpSessionConfig {


    private String host;


    private int port;


    private String password;


    private int db;



    public RedisHttpSessionConfiguration getRedisHttpSessionConfiguration() {
        RedisHttpSessionConfiguration r = new RedisHttpSessionConfiguration();
        r.setMaxInactiveIntervalInSeconds(600);
        return r;
    }


    public JedisPoolConfig getJedisPoolConfig() {

        JedisPoolConfig j =  new JedisPoolConfig();
        j.setMaxTotal(50);
        j.setMaxIdle(10);
        return j;
    }


    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory j = new JedisConnectionFactory();

        j.setHostName(host);
        j.setPort(port);
        if (password != "") {
            j.setPassword(password);
        }
        j.setDatabase(db);

        return j;
    }
}
