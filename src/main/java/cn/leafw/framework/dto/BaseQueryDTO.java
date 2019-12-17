package cn.leafw.framework.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/16
 */
@Data
public class BaseQueryDTO {

    private Integer currentPage;
    private Integer pageSize;
    private Long totalSize;
    private Integer totalPage;
}

