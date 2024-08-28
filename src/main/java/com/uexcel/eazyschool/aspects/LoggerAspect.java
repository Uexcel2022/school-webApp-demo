package com.uexcel.eazyschool.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
    @Around(value = "execution(* com.uexcel.eazyschool..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Aspect Logging Started: Method being executed: {}", joinPoint.getSignature());
        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();
        log.info("Execution Ended: {}; Time elapse: {} (Millis)", joinPoint.getSignature(), Duration.between(start,end).toMillis());
        return result;
    }

    @AfterThrowing(value = "execution(* com.uexcel.eazyschool..*.*(..))", throwing= "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        log.error("Exception in Method: {}; {}", joinPoint.getSignature(), "due to "+ e.getMessage());
    }
}
