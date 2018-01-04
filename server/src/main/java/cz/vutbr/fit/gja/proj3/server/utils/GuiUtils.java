package cz.vutbr.fit.gja.proj3.server.utils;

import cz.vutbr.fit.gja.proj3.server.user.entity.CustomUserPrincipal;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Helper utility class.
 */
public class GuiUtils {
    
    /**
     * Retrieves logged-in user information.
     * @return Logged user object
     */
    public static User getLoggedUser() {
        return (User)((CustomUserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    /**
     * Shows Info message.
     * @param header Message header
     * @param description Message description
     */
    public static void showInfo(String header, String description) {
        FacesMessage msg = new FacesMessage(header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    /**
     * Shows Info message.
     * @param header Message header
     */
    public static void showInfo(String header) {
        showInfo(header, "");
    }

    /**
     * Shows Warning message.
     * @param header Message header
     * @param description Message description
     */
    public static void showWarning(String header, String description) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    /**
     * Shows Warning message.
     * @param header Message header
     */
    public static void showWarning(String header) {
        showWarning(header, "");
    }

    /**
     * Shows Error message.
     * @param header Message header
     * @param description Message description
     */
    public static void showError(String header, String description) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, header, description);
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    /**
     * Shows Error message.
     * @param header Message header
     */
    public static void showError(String header) {
        showError(header, "");
    }
    
    /**
     * Retrieves URL parameter.
     * @param key Parameter name
     * @return Parameter value
     */
    public static String getParam(String key) {
        return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }
}
