package hello.cmds;


import hello.interfaces.ICmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("jdbcCmd")
public class Jdbc implements ICmd{

    @Autowired
    DataSource ds;

    @Override
    public void run(String[] args) {
        System.out.print("haha");
//        createTable();
        query();
    }

    public void createTable() {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("CREATE TABLE t1 (id int(10) , name VARCHAR(20))");
            st.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void query() {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("select * from t1");
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                System.out.printf("%d: %s", rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
