package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class MethodLogger {
    @Pointcut("@annotation(Loggable)")
    public void webServiceMethod() { }

    @Around("webServiceMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        log.info("Call method " + methodName + " with args " + methodArgs);

        Object result = thisJoinPoint.proceed();

        log.info("Method " + methodName + " returns " + result);

        return result;
    }
}