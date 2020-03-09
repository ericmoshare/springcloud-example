package com.example.springboot.titan.user.webapp;

import com.example.springboot.titan.user.entity.User;
import com.example.springboot.titan.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eric.Mo
 * @since 2020/3/8
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findByUserId/{userId}")
    public User findByUserId(@PathVariable String userId) {
        log.info("/findByUserId, userId: " + userId);
        User model = new User();
        model.setUserId(userId);

        return userRepository.findOne(Example.of(model));
    }
}
