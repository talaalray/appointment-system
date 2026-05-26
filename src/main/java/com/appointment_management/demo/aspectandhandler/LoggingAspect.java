package com.appointment_management.demo.aspectandhandler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.appointment_management.demo.service..*(..)) || " +
            "execution(* com.appointment_management.demo.controller..*(..))")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        String method = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();

        log.info("{} args={}", method, safeArgs(args));

        try {
            Object result = pjp.proceed();
            long took = System.currentTimeMillis() - start;
            log.info("{} took={}ms", method, took);
            return result;

        } catch (Exception ex) {
            long took = System.currentTimeMillis() - start;
            log.error("{} took={}ms ex={}", method, took, ex.toString());
            throw ex;
        }
    }

    private String safeArgs(Object[] args) {
        return Arrays.toString(args);
    }
}
