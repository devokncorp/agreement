package com.example.demo.core.montior;

import com.example.demo.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class MonitoringAspect {
    private final LogService logService;

    @Around("@annotation(com.example.demo.core.api.OvmExecutionTime)")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        double start = System.currentTimeMillis();

        Object proceed = null;
        try {
            proceed = pjp.proceed();
        } catch (Exception e) {
            log.error("{} {} ms {} {}", getClassSimpleName(pjp), (System.currentTimeMillis() - start) / 1000, pjp.getSignature().getName(), e.getMessage());
            throw e;
        }

        log.debug("{} {} ms {}", getClassSimpleName(pjp), (System.currentTimeMillis() - start) / 1000, pjp.getSignature().getName());
        return proceed;
    }


    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        double start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        log.debug("{} {} ms {}", getClassSimpleName(pjp), (System.currentTimeMillis() - start) / 1000, pjp.getSignature().getName());
        return proceed;
    }

    private String getClassSimpleName(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass().getSimpleName();
    }

    //    @Pointcut("execution(* com.example.demo.core.base.*.*(..))")
    public void baseExecutionbaseExecution() {
    }

    ;
}
