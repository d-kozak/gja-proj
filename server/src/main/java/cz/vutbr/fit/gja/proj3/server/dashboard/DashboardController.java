package cz.vutbr.fit.gja.proj3.server.dashboard;

import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Log
@Scope(value = "session")
@Component(value = "dashboardController")
@ELBeanName(value = "dashboardController")
@Join(path = "/", to = "/dashboard.jsf")
@ManagedBean(name = "dashboardController")
@ViewScoped
public class DashboardController {
}
