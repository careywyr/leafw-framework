package cn.leafw.framework.dto;

import lombok.Data;

/**
 * 请求返回包装
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/16
 */
@Data
public class ResultDTO<T> {

    private Boolean success = true;

    private Integer code;

    private String msg;

    private T data;

    public ResultDTO() {
    }

    public ResultDTO(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

