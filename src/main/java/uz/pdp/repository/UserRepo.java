package uz.pdp.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import uz.pdp.model.AuthUser;
import java.util.Map;

@Component
public class UserRepo {

    private NamedParameterJdbcTemplate template;
    private RowMapper<AuthUser> rowMapper =  (rs, rowNum) ->{
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        return AuthUser.builder().id(id).name(name).username(username).build();
    };
    private BeanPropertyRowMapper<AuthUser> autoMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);

    public UserRepo(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void save(AuthUser user){
        String query =  "insert into users(name,username) values(:name,:username) ";
        Map<String, Object> name = Map.of(
                "name", user.getName(),
                "username", user.getUsername()
        );
        template.update(query,name);
    }
    public AuthUser get(Integer id){
        String query =  "select * from users where id = :id limit 1";
        AuthUser user = template.queryForObject(query,Map.of("id",id), autoMapper);
        return user;
    }
    public Integer saveReturnId(AuthUser user){
        String query =  "insert into users(name,username) values(:name,:username) returning id,name";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name",user.getName())
                .addValue("username",user.getUsername());

        template.update(query,source, keyHolder,new String[]{"id","name"});
        return  (Integer)(keyHolder.getKeys().get("id"));
    }
}
