package com.example.springboot.titan.shop.webapp;

import com.example.springboot.titan.shop.client.UserClient;
import com.example.springboot.titan.shop.entity.Resp;
import com.example.springboot.titan.shop.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
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

    /**
     * 基于 普通 http 调用方式的 fallback 回退机制.
     * <p>
     * * 启用 fallbackFactory 后, 接口返回
     * {
     * "code": "200",
     * "msg": "success",
     * "data": {
     * "userId": "-1",
     * "username": "默认用户"
     * }
     * }
     * curl http://localhost:8081/v4/user/10087
     */
    @HystrixCommand(fallbackMethod = "getUserV4FallbackWithThrowable", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS
                    , value = "1000"),
            @HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS
                    , value = "10000")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
    }
    )
    @GetMapping("/v4/user/{userId}")
    public Resp getUserV4(@PathVariable String userId) {
        log.info("/v4/user/" + userId);

        return new Resp(restTemplate.getForObject("http://titan-user-boot/findByUserId/" + userId, User.class));
    }

    /**
     * 默认回退方法, 当用户服务不可用/获取用户信息接口不可用时, 会调用该方法
     */
    public Resp getUserV4Fallback(String userId) {
        log.info("fallback /v4/user/" + userId);
        User user = new User();
        user.setUserId("-1");
        user.setUsername("默认用户");
        return new Resp(user);
    }

    /**
     * 默认回退方法, 当用户服务不可用/获取用户信息接口不可用时, 会调用该方法
     */
    public Resp getUserV4FallbackWithThrowable(String userId, Throwable throwable) {
        log.info("fallback /v4/user/" + userId + ", " + throwable.getMessage(), throwable);

        User user = new User();
        user.setUserId("-1");
        user.setUsername("默认用户");
        return new Resp(user);
    }

    /**
     * 基于 普通 http 调用方式的 fallback 回退机制.
     * curl http://localhost:8081/v4/user/10087
     */
    @HystrixCommand(fallbackMethod = "getUserV5FallbackWithThrowable", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS
                    , value = "1000"),
            @HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS
                    , value = "10000")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
    }
    )
    @GetMapping("/v5/user/{userId}")
    public Resp getUserV5(@PathVariable String userId) {
        log.info("/v5/user/" + userId);

        return new Resp(userClient.findByUserId(userId));
    }

    /**
     * 默认回退方法, 当用户服务不可用/获取用户信息接口不可用时, 会调用该方法
     */
    public Resp getUserV5FallbackWithThrowable(String userId, Throwable throwable) {
        log.info("fallback /v5/user/" + userId + ", " + throwable.getMessage(), throwable);

        User user = new User();
        user.setUserId("-1");
        user.setUsername("默认用户");
        return new Resp(user);
    }
}
