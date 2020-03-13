package com.example.springboot.titan.shop.webapp;

import com.example.springboot.titan.shop.entity.Resp;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 异常拦截器
 * VO 校验失败后处理
 * Created by sheng on 2019/6/20.
 */
@Slf4j
@ControllerAdvice
public class ExceptionInterceptor {

    /**
     * 处理异常
     */
    @ExceptionHandler
    public ResponseEntity<Resp> handleException(Throwable throwable) {

        Resp resp = null;

        //判断错误类型. 指定异常的类别
        if (throwable instanceof BindException) {
            //如果是spring自带的校验处理器抛出的错误.
            //如果明确是哪里报错, 就不用将错误对象throwable写在 log里面
            log.error("catch BindException, " + throwable.getMessage());

            BindException bindException = (BindException) throwable;
            List<FieldError> list = bindException.getFieldErrors();

            //返回第一个错误
            resp = new Resp("400", list.get(0).getDefaultMessage());

            //instanceof指是否ConnectionException这个类型
        } else if (throwable instanceof FeignException) {
            //捕获业务异常 ConnectionException
            log.error("catch FeignException, " + throwable.getMessage(), throwable);
            resp = new Resp("400", throwable.getMessage());
        } else {
            //else 捕获未知异常
            //know -> knew -> known
            //如果不明确是哪里报错, 就可以将错误对象throwable 写在 log里面
            log.error("catch unknown Exception, " + throwable.getMessage(), throwable);
            resp = new Resp("401", "系统异常");
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
