package hello.cmds;


import hello.interfaces.ICmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("jdbcTmplCmd")
public class JdbcTmpl implements ICmd{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void query() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select 1");
        list.forEach(args -> {
            args.forEach((k,v) -> {
                System.out.printf("%-10s%5d\n", k, (long)v);
            });
        });
    }

    @Override
    public void run(String[] args) {
        this.query();
    }
}
