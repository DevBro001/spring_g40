package uz.pdp.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import uz.pdp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserRepo {

    private JdbcTemplate template;
    private RowMapper<User> rowMapper =  (rs, rowNum) ->{
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String username = rs.getString("username");
            return User.builder().id(id).name(name).username(username).build();
    };
    private BeanPropertyRowMapper<User> autoMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserRepo(JdbcTemplate template) {
        this.template = template;
    }

    public void save(User user){
        String query =  "insert into users(name,username) values(?,?) ";
        template.update(query,user.getName(),user.getUsername());
    }
    public Integer saveReturnId(User user){
        String query =  "insert into users(name,username) values(?,?) returning id,name";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator statementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(query,new String[]{"id","name"});
                preparedStatement.setObject(1,user.getName());
                preparedStatement.setObject(2,user.getUsername());
                return preparedStatement;
            }
        };

        template.update(statementCreator, keyHolder);
        System.out.println(keyHolder.getKeys().get("name"));
        return  (Integer)(keyHolder.getKeys().get("id"));
    }
    public void update(User user){
        String query =  "update users set name = ?, username = ? where id = ?";
        template.update(query,user.getName(),user.getUsername(),user.getId());
    }
    public void delete(Integer id){
        String query =  "delete from users where id = ?";
        template.update(query,id);
    }
    public List<User> getAll(){
        String query =  "select * from users where username = ?";

        List<User> query1 = template.query(query, rowMapper,"salom");
        return query1;
    }
    public User get(Integer id){
        String query =  "select * from users where id = ? limit 1";
        User user = template.queryForObject(query, autoMapper, id);
        return user;
    }
}
