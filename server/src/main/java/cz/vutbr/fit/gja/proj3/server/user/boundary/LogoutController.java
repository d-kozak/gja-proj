package cz.vutbr.fit.gja.proj3.server.user.boundary;

import lombok.extern.java.Log;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Log
@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest request) {
        log.info("performing logout");

        new SecurityContextLogoutHandler().logout(request, null, null);

        return "login";
    }
}
