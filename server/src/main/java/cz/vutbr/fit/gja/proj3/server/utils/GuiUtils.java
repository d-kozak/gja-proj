package cz.vutbr.fit.gja.proj3.server.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GuiUtils {

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
