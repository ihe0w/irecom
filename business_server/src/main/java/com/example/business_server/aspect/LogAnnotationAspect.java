package com.example.business_server.aspect;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
@Slf4j
@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAnnotationAspect {
    @Autowired
    HttpServletRequest request;

    @Around("@annotation(com.example.business_server.aspect.OpLog)")
    public Object log(ProceedingJoinPoint joinPoint) {
        Method method=((MethodSignature)joinPoint.getSignature()).getMethod();
        OpLog opLog=method.getAnnotation(OpLog.class);

        Object response=null;
        try {
            response=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        if (!StringUtils.isEmpty(opLog.opItemExpression())){
            SpelExpressionParser parser=new SpelExpressionParser();
            Expression expression=parser.parseExpression(opLog.opItemExpression());

            EvaluationContext context=new StandardEvaluationContext();

            // 获取方法运行时参数
            Object[] args=joinPoint.getArgs();
            LocalVariableTableParameterNameDiscoverer discoverer=new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames=discoverer.getParameterNames(method);

            if (parameterNames!=null){
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i],args[i]);
                }
            }

            // 将方法的resp当做变量放到context中，变量名称为该类名转化为小写字母开头的驼峰形式
            if (response!=null){
                context.setVariable(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,response.getClass().getSimpleName()),
                        response);
            }

            // 解析表达式，获取结果
            String itemId=String.valueOf(expression.getValue(context));

            handle(opLog.opType(),opLog.opItem(),itemId);

        }
        return response;
    }

    private void handle(OpType opType, String opItem, String itemId) {
        log.info("opType = " + opType.name() +",opItem = " +opItem + ",opItemId = " +itemId);
    }
}
