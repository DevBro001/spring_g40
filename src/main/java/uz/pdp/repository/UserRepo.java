package uz.pdp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserRepo {

    private JdbcTemplate template;
    private RowMapper<User> rowMapper =  (rs,rowNum) ->{
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String username = rs.getString("username");
            return User.builder().id(id).name(name).username(username).build();
    };

    public UserRepo(JdbcTemplate template) {
        this.template = template;
    }

    public void save(User user){
        String query =  "insert into users(name,username) values(?,?) ";
        template.update(query,user.getName(),user.getUsername());
    }
    public List<User> getAll(){
        String query =  "select * from users where username = ?";

        List<User> query1 = template.query(query, rowMapper,"salom");
        return query1;
    }
}
