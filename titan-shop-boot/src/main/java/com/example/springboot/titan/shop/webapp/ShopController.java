package com.example.springboot.titan.shop.webapp;

import com.example.springboot.titan.shop.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Eric.Mo
 * @since 2020/3/8
 */
@Slf4j
@RestController
public class ShopController {

    @Value("${user.getUserUrl}")
    private String userGetUserUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable String userId) {
        log.info("/getUser, userId: " + userId);

        return restTemplate.getForObject(userGetUserUrl + userId, User.class);
    }
}
