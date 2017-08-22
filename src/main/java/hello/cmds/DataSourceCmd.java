package hello.cmds;


import hello.interfaces.ICmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository("dataSourceCmd")
public class DataSourceCmd implements ICmd{
    @Autowired
    private DataSource ds;

    @Override
    public void run(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select 1");
        list.forEach(arg -> {
            arg.forEach((k, v) -> {
                System.out.printf("%-10s%5d\n", k, (long) v);
            });
        });
    }
}
