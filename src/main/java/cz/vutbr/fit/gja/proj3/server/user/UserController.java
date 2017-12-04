package cz.vutbr.fit.gja.proj3.server.user;

import lombok.Getter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "userController")
@ELBeanName(value = "userController")
@Join(path = "/user", to = "/user-form.jsf")
public class UserController {

    private final UserRepository userRepository;

    @Getter
    private User user = new User();

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String save() {
        userRepository.save(user);
        user = new User();
        return "/product-list.xhtml?faces-redirect=true";
    }
}
