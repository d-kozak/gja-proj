package cz.vutbr.fit.gja.proj3.server.utils;

import cz.vutbr.fit.gja.proj3.server.user.entity.CustomUserPrincipal;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class GuiUtils {
    
    public static User getLoggedUser() {
        return (User)((CustomUserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    public static void showInfo(String header, String description) {
        FacesMessage msg = new FacesMessage(header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public static void showInfo(String header) {
        showInfo(header, "");
    }

    public static void showWarning(String header, String description) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public static void showWarning(String header) {
        showWarning(header, "");
    }

    public static void showError(String header, String description) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public static void showError(String header) {
        showError(header, "");
    }
}
