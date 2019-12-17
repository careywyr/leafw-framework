package cn.leafw.framework.exception;

/**
 * 异常处理
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/16
 */
public class BusinessException extends RuntimeException {

    private BusinessException(String message){
        super(message);
    }

    public static BusinessException of(String msg){
        return new BusinessException(msg);
    }
}

