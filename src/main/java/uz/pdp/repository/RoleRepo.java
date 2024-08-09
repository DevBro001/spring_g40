package uz.pdp.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.model.Permission;
import uz.pdp.model.Role;

import java.util.List;
import java.util.Map;

@Repository
public class RoleRepo {

    private NamedParameterJdbcTemplate template;
    private RowMapper<Role> rowMapper =  (rs, rowNum) ->{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String code = rs.getString("code");

        return Role.builder().id(id).name(name).code(code).build();
    };

    public RoleRepo(NamedParameterJdbcTemplate template) {
        this.template = template;

    }


    public List<Role> getByUser(Integer userId){
        String query =  """
                    select r.* from role r
                    inner join user_role ur on r.id = ur.role_id 
                    where ur.user_id = :userId
                    """;
        List<Role> roles = template.query(query, Map.of("userId", userId), rowMapper);
        return roles;
    }
}
