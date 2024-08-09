package uz.pdp.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import uz.pdp.model.AuthUser;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepo {

    private NamedParameterJdbcTemplate template;
    private PasswordEncoder passwordEncoder;
    private RowMapper<AuthUser> rowMapper =  (rs, rowNum) ->{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        return AuthUser.builder().id(id).name(name).username(username).password(password).roles(role).build();
    };
    private BeanPropertyRowMapper<AuthUser> autoMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);

    public UserRepo(NamedParameterJdbcTemplate template, PasswordEncoder passwordEncoder) {
        this.template = template;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(AuthUser user){
        String query =  "insert into users(name,username,password) values(:name,:username,:password) ";
        String encode = passwordEncoder.encode(user.getPassword());
        Map<String, Object> name = Map.of(
                "name", user.getName(),
                "username", user.getUsername(),
                "password", encode
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

    public Optional<AuthUser> getByUsername(String username) {
        String query =  "select * from users where username = :username limit 1";
        try {
            AuthUser user = template.queryForObject(query, Map.of("username", username), rowMapper);
            return Optional.ofNullable(user);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
