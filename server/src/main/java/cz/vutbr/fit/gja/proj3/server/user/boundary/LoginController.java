package cz.vutbr.fit.gja.proj3.server.user.boundary;


import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Log
@Scope(value = "session")
@Component(value = "loginController")
@ELBeanName(value = "loginController")
@Join(path = "/login", to = "/login.jsf")
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public LoginController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }
}
