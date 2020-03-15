package com.example.springboot.titan.shop.client;

import com.example.springboot.titan.shop.entity.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Eric.Mo
 * @since 2020/3/15
 */
@Slf4j
@Service
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    /**
     * 启用 fallbackFactory 后, 接口返回
     * {
     * "code": "200",
     * "msg": "success",
     * "data": {
     * "userId": "-2",
     * "username": "默认用户"
     * }
     * }
     *
     * @param cause
     * @return
     */
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {

            @Override
            public User findByUserId(@PathVariable("userId") String userId) {
                log.warn("fallback catch FeignException, " + cause);

                User user = new User();
                user.setUserId("-2");
                user.setUsername("默认用户");
                return user;
            }
        };
    }
}
