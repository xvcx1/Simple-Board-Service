package com.example.b2bservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

// 전사적인 로깅 AOP
@Aspect
@Component
public class ParameterAop {
    // * com.example.aop.controller..*.*(..)) 란 이러한 경로의 하위에 있는 메소드에 대해
    @Pointcut("execution(* com.example.b2bservice.controller..*.*(..))")
    private void methodLogging(){}

    // 해당 경로의 하위 메소드들이 실행되기 전에 이 메소드를 실행하겠다.
    @Before(value = "methodLogging()")
    public void before(JoinPoint joinPoint){
        // aop가 적용된 메소드들이 실행될 때 해당 메소드의 이름을 출력
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // 메소드 이름 획득
        System.out.println("===== " + method.getName() + " method 실행 =====");

        // 그리고 해당 메소드가 전달하는 객체의 타입과 값을 출력
        // System.out.println("전달하는 객체의 타입, 값 체크");
        Object[] args = joinPoint.getArgs();

        for(Object obj : args){
            System.out.print("type : " + obj.getClass().getSimpleName() + ", ");
            System.out.println("value : " + obj);
        }
    }

    // 정상 실행 후 return이 끝난 뒤에는 이 메소드 실행
    @AfterReturning(value = "methodLogging()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        // 반환 값이 무엇인지 로그를 남김.
        System.out.println("return obj : " + returnObj);
        // 메소드 종료 알림.
        System.out.println("======  method 종료 ======");
    }

}
