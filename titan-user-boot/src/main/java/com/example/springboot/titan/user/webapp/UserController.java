package com.example.springboot.titan.user.webapp;

import com.example.springboot.titan.user.entity.User;
import com.example.springboot.titan.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

        // 随机休息
        try {
            long sleep = new Random().nextInt(5);
            sleep = 500;
            log.info("随机休息 {} 毫秒", sleep);
            TimeUnit.MILLISECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userRepository.findOne(Example.of(model));
    }
}
