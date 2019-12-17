package cn.leafw.framework.handler;

import cn.leafw.framework.dto.ResultDTO;
import cn.leafw.framework.exception.BusinessException;
import cn.leafw.framework.utils.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/16
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultDTO exceptionHandle(HttpServletRequest request, BusinessException e){
        log.error("Catch a exception in API[{}]", request.getRequestURL().toString(), e);
        return ResultHelper.returnFalse(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDTO exceptionHandle(HttpServletRequest request, Exception e){
        log.error("Catch a exception in API[{}]", request.getRequestURL().toString(), e);
        return ResultHelper.returnFalse(e.getMessage());
    }
}

