package uz.pdp.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import uz.pdp.model.Permission;

import java.util.List;
import java.util.Map;

@Repository
public class PermissionRepo {

    private NamedParameterJdbcTemplate template;
    private PasswordEncoder passwordEncoder;
    private RowMapper<Permission> rowMapper =  (rs, rowNum) ->{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String code = rs.getString("code");

        return Permission.builder().id(id).name(name).code(code).build();
    };

    public PermissionRepo(NamedParameterJdbcTemplate template, PasswordEncoder passwordEncoder) {
        this.template = template;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Permission> getByRole(Integer roleId){
        String query =  """
                    select p.* from permission p
                    inner join role_permission rp on p.id = rp.permission_id 
                    where rp.role_id = :roleId
                    """;
        List<Permission> permissions = template.query(query, Map.of("roleId", roleId), rowMapper);
        return permissions;
    }

}
