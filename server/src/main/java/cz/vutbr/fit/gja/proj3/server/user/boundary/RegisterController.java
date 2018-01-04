package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Collections;
import java.util.HashSet;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
import lombok.Getter;
import lombok.Setter;

/**
 * Registration section backend controller.
 */
@Log
@Scope(value = "session")
@Component(value = "registerController")
@ELBeanName(value = "registerController")
@Join(path = "/register", to = "/register.jsf")
@ManagedBean(name = "registerController")
@ViewScoped
public class RegisterController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    
    @Getter @Setter
    private String login;
    
    @Getter @Setter
    private String firstName;
    
    @Getter @Setter
    private String lastName;
    
    @Getter @Setter
    private String password;
    
    @Getter @Setter
    private String passwordCheck;

    @Autowired
    public RegisterController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers user and saves him into database.
     * @return Redirect URL
     */
    public String register() {
        log.info("called with " + this);
        User newUser = User.builder()
                           .login(login)
                           .firstName(firstName)
                           .lastName(lastName)
                           .password(passwordEncoder.encode(password))
                           .roles(new HashSet<>(Collections.singletonList("ROLE_USER")))
                           .build();

        if (customUserDetailsService.saveUser(newUser)) {
            log.info("user registered successfully");
            return "/login.xhtml?faces-redirect=true";
        } else {
            showError("Login already taken");
            log.severe("registration failed");
            return null;
        }
    }

    @Override
    public String toString() {
        return "RegisterController{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                '}';
    }
}
