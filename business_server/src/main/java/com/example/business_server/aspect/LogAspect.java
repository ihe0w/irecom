package com.example.business_server.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(* com.example.business_server.dao..*(..))")
    public void dao(){

    }

    @Pointcut("execution(* com.example.business_server.service..*(..))")
    public void servicePointCut(){

    }


    @Pointcut("execution(* com.example.business_server.controller..*(..))")
    public void controller(){

    }

    @Before("servicePointCut() AND dao()")
    public void logState(JoinPoint joinPoint) throws Throwable{
        log.info("------------service dao------------------");
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        // 打印请求入参
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(joinPoint.getArgs());
        log.info("Request Args   : {}", json);
    }

    @AfterReturning(returning = "result", pointcut = "servicePointCut() AND dao()")
    public void logResultState(Object result) {
        log.info("----------------after service or dao-----------------");
        log.info("return result {}",result.toString());
    }

    @Before("controller()")
    public void logBeforeController(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();

        log.info("--------------controller---------------------");
        log.info("URL {}",request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(joinPoint.getArgs());
        log.info("Request Args   : {}", json);
    }

}

// //    @Pointcut("execution(* com.example.business_server.service.RecommendService.getCollaborativeFilteringRecommendations(..))")
////    @Pointcut("execution(* com.example.business_server.dao.PostMapper.deletePostById(Long) )")
//    @Pointcut("execution(* com.example.business_server..*(..) )")
////    @Pointcut("execution(* *.PostMapper.*(Long) )")
////    @Pointcut("execution(public * *(..))")
//    public void enterExit(){
//    }
