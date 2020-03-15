package com.example.springboot.titan.shop.client;

import com.example.springboot.titan.shop.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Eric.Mo
 * @since 2020/3/8
 */
@FeignClient(name = "titan-user-boot", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/findByUserId/{userId}", method = RequestMethod.GET)
    public User findByUserId(@PathVariable("userId") String userId);
}
