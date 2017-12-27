package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import cz.vutbr.fit.gja.proj3.server.MainController;
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
@Component(value = "userSettingsController")
@ELBeanName(value = "userSettingsController")
@Join(path = "/user/settings", to = "/user-settings.jsf")

@ManagedBean(name = "userSettingsController")
@ViewScoped
public class UserSettingsController extends UserController {

    @Autowired
    public UserSettingsController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        super(customUserDetailsService, passwordEncoder);
    }

}
