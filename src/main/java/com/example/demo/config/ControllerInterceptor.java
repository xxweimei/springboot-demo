package com.example.demo.config;

import com.example.demo.annotation.DataSource;
import com.example.demo.annotation.Log;
import com.example.demo.commons.constants.CommonConstants;
import com.example.demo.commons.utils.GsonUtils;
import com.example.demo.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * functional description
 * Created by Sandy
 * on 5/26/16.
 */
@Component
@Aspect
@Slf4j
public class ControllerInterceptor {

    @Around("execution(public * com.example.demo.controller.*Controller.*(..))")
    public Object controllerAspect(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String methodName = pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName();
        if (method.isAnnotationPresent(Log.class) && !method.getAnnotation(Log.class).params()) {
            log.info("request|" + methodName);
        } else {
            log.info("request|" + methodName + "|params:" + GsonUtils.toJson(pjp.getArgs()));
        }
        Object result;
        try {
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource e = method.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(e.value());
            } else {
                //默认采用写库
                DynamicDataSourceHolder.putDataSource(CommonConstants.DATA_SOURCE_WRITE);
            }
            result = pjp.proceed();
            if (method.isAnnotationPresent(Log.class) && method.getAnnotation(Log.class).result()) {
                log.info("response|" + methodName + "|result:" + GsonUtils.toJson(result));
            } else {
                log.info("response|" + methodName);
            }
        } catch (ServiceException var8) {
            result = method.getReturnType().getConstructor(new Class[]{Integer.TYPE}).newInstance(var8.getCode());
            log.info("serviceException|" + methodName + "|code:" + var8.getCode());
        } catch (Exception var9) {
            result = method.getReturnType().getConstructor(new Class[]{Integer.TYPE}).newInstance(-2);
            log.error("otherException|" + methodName, var9);
        }
        return result;
    }
}
