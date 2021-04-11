package com.Bootcamp.Project.Application.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingImplementation {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingImplementation.class);

    @Pointcut("execution(* com.Bootcamp.Project.Application.services*..*(..))")
    private void everythingInMyApplication() {}

    @Before("com.Bootcamp.Project.Application.logger.LoggingImplementation.everythingInMyApplication()")
    public void logMethodName(JoinPoint joinPoint) {
        LOG.info("Called {} method", joinPoint.getSignature().getName());
    }
}
