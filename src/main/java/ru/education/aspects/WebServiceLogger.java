package ru.education.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {

    private static Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * ru.education.service.ProductService.*(..))")
    public void serviceMethod(){}

    @Pointcut("@annotation(ru.education.annotation.Loggable)")
    public void loggableMethod(){}

    @Pointcut("@annotation(ru.education.annotation.BeforeLoggable)")
    public void beforeLoggableMethod(){}

    @Pointcut("@annotation(ru.education.annotation.AfterLoggable)")
    public void afterLoggableMethod(){}

    @Around(value = "serviceMethod() && loggableMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));

        Object result = thisJoinPoint.proceed();

        LOG.info("Method " + methodName + " returns " + result);

        return result;
    }

    @Before(value = "serviceMethod() && beforeLoggableMethod()")
    public void logBeforeServiceCall(JoinPoint thisJoinPoint){
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));
    }

    @After(value = "serviceMethod() && afterLoggableMethod()")
    public void logAfterServiceCall(JoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();

        LOG.info("Method " + methodName + " ends");
    }
}
