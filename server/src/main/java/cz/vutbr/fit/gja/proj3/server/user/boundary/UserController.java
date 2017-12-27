package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import cz.vutbr.fit.gja.proj3.server.utils.GuiUtils;
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
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
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
    
    @Getter
    @Setter
    private String oldPassword;
    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.user = GuiUtils.getLoggedUser();
    }
    
    @PostConstruct
    public void init() {
        user.setRoles(new HashSet<String>(Arrays.asList("ROLE_USER")));
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
    
    public void update() {
        User oldUser = customUserDetailsService.getUserById(user.getId());
        
        if (!"".equals(user.getPassword())) {
            log.info(oldUser.getPassword());
            log.info(passwordEncoder.encode(oldPassword));
            
            if ("".equals(oldPassword)) {
                showError("Old password is required for changing current one.");
                return;
            }
            
            if (!passwordEncoder.matches(oldPassword, oldUser.getPassword())) {
                showError("Old passwords doesn\'t match");
                log.severe("update failed");
                return;
            }
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if ("".equals(user.getPassword())) {
            user.setPassword(oldUser.getPassword());
        }
        
        user.setId(oldUser.getId());
        if (customUserDetailsService.saveUser(user)) {
            log.info("user update successful");
            showInfo("Update succesful");
        } else {
            showError("Login already taken");
            log.severe("update failed");
        }
    }
    
    public Map<String, String> getConstRoles() {
        return UserRepository.ROLES;
    }
}
