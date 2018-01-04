/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.getParam;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * User edit section backend bean.
 */
@Log
@Scope(value = "session")
@Component(value = "userEditController")
@ELBeanName(value = "userEditController")
@Join(path = "/admin/user/edit", to = "/user-edit.jsf")

@ManagedBean(name = "userEditController")
@ViewScoped
public class UserEditController extends UserController {
    @Getter @Setter
    private String id;
    
    public UserEditController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        super(customUserDetailsService, passwordEncoder, userRepository);
        this.oldPasswordRequired = false;
    }
    
    /**
     * Handles URL parameter.
     */ 
    public String onload() {
        if (getParam("id") == null) {
            id = null;
        }
        
        if (id != null) {
            long uid = Long.parseLong(id);
            user = userRepository.findOne(uid);
            
            if (user == null) {
                showError("User with that ID doesn't exist.");
                return "user-list.xhtml?faces-redirect=true";
            }
            
            return null;
        }else{
            showError("User with that ID doesn't exist.");
            return "user-list.xhtml?faces-redirect=true";
        }
    }
}
