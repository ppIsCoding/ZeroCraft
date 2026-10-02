package com.pp.zerocraft.ratelimit.annotation;

import com.pp.zerocraft.ratelimit.enums.RateLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    
    /**
     * 限流key前缀@Aspect
     * @Component
     * @Slf4j
     * public class RateLimitAspect {
     *     @Resource
     *     private RedissonClient redissonClient;
     *     @Resource
     *     private UserService userService;
     * }
     */
    String key() default "";
    
    /**
     * 每个时间窗口允许的请求数
     */
    int rate() default 10;
    
    /**
     * 时间窗口（秒）
     */
    int rateInterval() default 1;
    
    /**
     * 限流类型
     */
    RateLimitType limitType() default RateLimitType.USER;
    
    /**
     * 限流提示信息
     */
    String message() default "请求过于频繁，请稍后再试";
}
