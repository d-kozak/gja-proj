package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.getParam;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import lombok.Setter;

@Log
@Scope(value = "session")
@Component(value = "userController")
@ELBeanName(value = "userController")
@Join(path = "/admin/user/new", to = "/user-form.jsf")

@ManagedBean(name = "userController")
@ViewScoped
public class UserController {
    @Getter
    @Setter
    private User user = new User();
    
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Getter
    @Setter
    private String id;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    
    @PostConstruct
    public void init() {
        user.setRoles(new HashSet<String>(Arrays.asList("ROLE_USER")));
    }
    
    public void onload() {
        if (getParam("edit") == null) {
            id = null;
        }
        
        if (id != null) {
            log.info("ID IS "+id);
            long uid = Long.parseLong(id);
            user = userRepository.findOne(uid);
        }else{
            if (user.getId() != 0) {
                user = new User();
                init();
                log.info("null");
            }
        }
    }

    public String save() {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (customUserDetailsService.saveUser(user)) {
            log.info("user registered successfully");
            user = new User();
            init();
            return "/user-list.xhtml?faces-redirect=true";
        } else {
            showError("Login already taken");
            log.severe("registration failed");
            return null;
        }
    }
    
    public Map<String, String> getConstRoles() {
        return UserRepository.ROLES;
    }
}
