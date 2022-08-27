package com.example.security.inout.mapper;

import com.example.security.inout.dao.SysUser;
import org.springframework.stereotype.Repository;

/**
 * 表操仓储对象
 *
 * @author phone
 */
@Repository
public interface SysUserMapper {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(SysUser record);

    /**
     * 选择性新增
     *
     * @param record
     * @return
     */
    int insertSelective(SysUser record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(Long id);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUser record);
}