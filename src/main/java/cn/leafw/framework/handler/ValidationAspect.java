package cn.leafw.framework.handler;

import cn.leafw.framework.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * TODO
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2020/3/20
 */
@Aspect
@Component
public class ValidationAspect {

    /**
     * 在控制器处理之前，如果有验证bresult的异常。则直接按标准方式返回，不执行控制单元。
     */
    @Around("execution(public * cn..controller.*.*(..,org.springframework.validation.BindingResult))")
    public Object checkBindingResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BindingResult bresult = (BindingResult)args[args.length-1];
        if(bresult.hasErrors()){
            throw BusinessException.of(bresult.getFieldErrors().get(0).getDefaultMessage());
        }else {
            return joinPoint.proceed(args);
        }
    }
}

