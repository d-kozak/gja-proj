package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.CustomUserPrincipal;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import java.util.Arrays;
import java.util.HashSet;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserPrincipal(user);
    }

    public boolean saveUser(User user) {
        if (userExists(user.getLogin())) {
            return false;
        }
        
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<String>(Arrays.asList("ROLE_USER")));
        }else{
            user.getRoles().add("ROLE_USER");
        }
        
        user.setPassword(user.getPassword());
        log.info("saving new user " + user);
        userRepository.save(user);
        return true;
    }

    private boolean userExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
}

