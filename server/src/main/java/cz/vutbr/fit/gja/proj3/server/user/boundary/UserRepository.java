package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public static final Map<String, String> ROLES = new HashMap<String, String>(){{;
        put("ROLE_USER", "User");
        put("ROLE_ADMIN", "Administrator");
    }};
    

    User findByLogin(String login);
    
    User findById(Long id);


}
