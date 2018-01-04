package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.Getter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User list section backend bean.
 */
@Scope(value = "session")
@Component(value = "userListController")
@ELBeanName(value = "userListController")
@Join(path = "/admin/user", to = "/user-list.jsf")
public class UserListController {

    private final UserRepository userRepository;
    
    @Getter
    private List<User> users;

    @Autowired
    public UserListController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads database data on page load.
     */
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        users = userRepository.findAll();
    }
    
    /**
     * Removes user from database.
     * @param u User entity to be removed
     * @return URL redirect
     */
    public String remove(User u) {
        userRepository.delete(u);
        return "/user-list.xhml?faces-redirect=true";
    }

}
