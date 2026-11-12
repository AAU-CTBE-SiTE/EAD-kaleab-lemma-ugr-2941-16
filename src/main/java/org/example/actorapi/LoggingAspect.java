package org.example.actorapi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    //
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // The before annotation runs before the execution of the API call in ActorController
    @Before("execution(* org.example.actorapi.ActorController.*(..))")
    public void logBefore(org.aspectj.lang.JoinPoint joinPoint) {
        log.info("Calling: {}", joinPoint.getSignature().getName());
    }

    // Runs before and after the PointCall
    @Around("execution(* org.example.actorapi.ActorController.*(..))")
    public Object logTiming(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // executes the PointCall the function
        long duration = System.currentTimeMillis() - start;
        log.info("{} took {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }

    @Around("@annotation(org.example.actorapi.LogExecution)")
    public Object logAnnotated(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Annotated method started: {}", joinPoint.getSignature().toShortString());
        return joinPoint.proceed();
    }

    // Runs when a function throws an exception
    @AfterThrowing(
            pointcut = "execution(* org.example.actorapi.ActorController.*(..))",
            throwing = "ex") // PointCut is the function executed and the throwing is the exception object thrown from the function
    public void logFailure(org.aspectj.lang.JoinPoint joinPoint, Throwable ex) {
        log.error("{} failed: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    // Runs after the function whether the function succeeds or fails
    @After("execution(* org.example.actorapi.ActorController.*(..))")
    public void logAfter(org.aspectj.lang.JoinPoint joinPoint) {
        log.info("Finished (success of failure): {}", joinPoint.getSignature().getName());
    }

    // Runs after a function if it only succeeds and returns some value
    @AfterReturning(
            pointcut = "execution(* org.example.actorapi.ActorController.*(..))",
            returning = "result" // The returned value
    )
    public void logSuccess(org.aspectj.lang.JoinPoint joinPoint, Object result) {
        log.info("Succeeded: {} returned {}", joinPoint.getSignature().getName(), result);
    }
}