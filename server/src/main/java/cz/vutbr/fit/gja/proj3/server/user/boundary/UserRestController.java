package cz.vutbr.fit.gja.proj3.server.user.boundary;

import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepositoy;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepositoy = userRepository;
    }

    @GetMapping
    public List<User> get() {
        return userRepositoy.findAll();
    }
}
