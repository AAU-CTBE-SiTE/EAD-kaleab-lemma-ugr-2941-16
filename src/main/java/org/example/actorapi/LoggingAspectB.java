package org.example.actorapi;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


// Instead of just logging for every function in ActorController, this works on specific functions or cluster of function only
@Aspect
@Component
public class LoggingAspectB {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all controller methods
    @Pointcut("execution(* org.example.actorapi.ActorController.*(..))")
    public void allControllerMethods() {}

    // Pointcut for only the "get" methods
    @Pointcut("execution(* org.example.actorapi.ActorController.get*(..))")
    public void getMethods() {}

    // Pointcut for methods annotated with @LogExecution
    @Pointcut("@annotation(org.example.actorapi.LogExecution)")
    public void annotatedMethods() {}

    @Before("allControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Calling: {}", joinPoint.getSignature().getName());
    }

    @Around("allControllerMethods()")
    public Object logTiming(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("{} took {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "ex")
    public void logFailure(JoinPoint joinPoint, Throwable ex) {
        log.error("{} failed: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @After("allControllerMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Finished (success or failure): {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "getMethods()", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        log.info("Succeeded: {} returned {}", joinPoint.getSignature().getName(), result);
    }

    // Custom annotation: only methods marked @LogExecution
    @Around("annotatedMethods()")
    public Object logAnnotated(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Annotated method started: {}", joinPoint.getSignature().toShortString());
        return joinPoint.proceed();
    }

    // Logs the arguments sent in (for the exercise)
    @Before("allControllerMethods()")
    public void logArgs(JoinPoint joinPoint) {
        log.info("Arguments for {}: {}", joinPoint.getSignature().getName(), joinPoint.getArgs().length);
    }
}