package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;

@Aspect
@Named
public class MethodDuration {
    @Around("execution(public * *(..))")
    public Object getMethodDuration(ProceedingJoinPoint joinPoint){
        long startTime = System.currentTimeMillis();
        Object returnedObject = null;
        try {
            returnedObject = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Method " + joinPoint.getSignature() + " took " + (endTime-startTime) + " ms");
        return returnedObject;
    }
}
