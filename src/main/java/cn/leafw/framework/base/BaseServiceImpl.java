package cn.leafw.framework.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.BaseMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础服务实现类
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/22
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public T selectByPrimaryKey(Object id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByPrimaryKey(Object id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return baseMapper.insertSelective(t);
    }

}

