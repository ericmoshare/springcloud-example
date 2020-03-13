package com.example.springboot.titan.shop.webapp;

import com.example.springboot.titan.shop.client.UserClient;
import com.example.springboot.titan.shop.entity.Resp;
import com.example.springboot.titan.shop.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private UserClient userClient;

    @GetMapping("/v1/user/{userId}")
    public User getUser2(@PathVariable String userId) {
        log.info("/v1/user/" + userId);

        return restTemplate.getForObject(userGetUserUrl + userId, User.class);
    }

    /**
     * 使用 ribbon 负载均衡重新获取 用户信息
     */
    @GetMapping("/v2/user/{userId}")
    public User getUser(@PathVariable String userId) {
        log.info("/v2/user/" + userId);

        return restTemplate.getForObject("http://titan-user-boot/findByUserId/" + userId, User.class);
    }

    @GetMapping("/log-user-instance")
    public void logUserInstance() {

        ServiceInstance serviceInstance = loadBalancerClient.choose("titan-user-boot");
        log.info("/log-user-instance 当前调用链 {}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());

    }

    @GetMapping("/v3/user/{userId}")
    public Resp getUser3(@PathVariable String userId) {
        log.info("/v3/user/" + userId);

        return new Resp(userClient.findByUserId(userId));
    }

}
