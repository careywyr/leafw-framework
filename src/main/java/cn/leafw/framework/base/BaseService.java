package cn.leafw.framework.base;

import java.util.List;

/**
 * 基础服务
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/22
 */
public interface BaseService<T> {

    /**
     * 根据主键查询
     * @param id id
     * @return T
     */
    T selectByPrimaryKey(Object id);

    /**
     * 查询
     * @param t t
     * @return T
     */
    List<T> select(T t);

    int selectCount(T t);

    /**
     * 根据主键更新
     * @param t t
     * @return int
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键删除
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(Object id);

    /**
     * 删除
     * @param t t
     * @return int
     */
    int delete(T t);

    /**
     * 保存
     * @param t t
     * @return int
     */
    int insert(T t);

    /**
     * 查询全部
     * @return 所有数据
     */
    List<T> selectAll();

}

