package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;

@Aspect
@Named
public class MethodDuration {
    @Around("execution(public * *(..))")
    public Object getMethodDuration(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object returnedObject = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Method " + joinPoint.getSignature() + " took " + (endTime-startTime) + " ms");
        return returnedObject;
    }
}
