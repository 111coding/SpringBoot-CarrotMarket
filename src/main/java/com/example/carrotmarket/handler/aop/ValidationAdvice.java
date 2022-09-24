package com.example.carrotmarket.handler.aop;

import com.example.carrotmarket.handler.exception.CustomValidationApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){}

    @Around("postMapping() || putMapping()")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web api 컨트롤러 =====================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    System.out.println(errorMap);
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }

            }
        }
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // profile 함수보다 먼저 실행

        return proceedingJoinPoint.proceed(); // profile 함수가 실행됨.
    }

}