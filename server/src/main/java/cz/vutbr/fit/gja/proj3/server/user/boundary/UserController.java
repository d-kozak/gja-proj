package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;

@Log
@Scope(value = "session")
@Component(value = "userController")
@ELBeanName(value = "userController")
@Join(path = "/user", to = "/user-form.jsf")

@ViewScoped
@ManagedBean(name = "userController")
public class UserController {


    @Getter
    private User user = new User();
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public String save() {
        if (customUserDetailsService.saveUser(user)) {
            log.info("user registered successfully");
            user = new User();
            return "/user-list.xhtml?faces-redirect=true";
        } else {
            showError("Login already taken");
            log.severe("registration failed");
            return null;
        }
    }
}
