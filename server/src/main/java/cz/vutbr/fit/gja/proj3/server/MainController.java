package cz.vutbr.fit.gja.proj3.server;

import cz.vutbr.fit.gja.proj3.server.user.entity.CustomUserPrincipal;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import cz.vutbr.fit.gja.proj3.server.utils.GuiUtils;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Log
@Scope(value = "session")
@Component(value = "mainController")
@ManagedBean(name = "mainController")
@ELBeanName(value = "mainController")
@ViewScoped
public class MainController {
    @Getter
    private User loggedUser;
    
    @Getter
    private int year;
    
    @PostConstruct
    public void init() {
        loggedUser = GuiUtils.getLoggedUser();
        year = Calendar.getInstance().get(Calendar.YEAR);
    }
}
