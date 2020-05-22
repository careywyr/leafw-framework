package cn.leafw.framework.utils;

import cn.leafw.framework.dto.ResultDTO;

/**
 * ResultHelper
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/17
 */
public class ResultHelper<T> {

    public static <T> ResultDTO<T> returnFalse(String msg){
        return new ResultDTO<>(false, msg);
    }

    public static <T> ResultDTO<T> returnOk(T data){
        return new ResultDTO<>(data);
    }

}

