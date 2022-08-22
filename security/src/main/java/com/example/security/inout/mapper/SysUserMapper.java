package com.example.security.inout.mapper;

import com.example.security.inout.dao.SysUser;

/**
 * @author phone
 */
public interface SysUserMapper {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(SysUser record);

    /**
     * 字段选择插入
     *
     * @param record
     * @return
     */
    int insertSelective(SysUser record);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(Long id);

    /**
     * 字段选择更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUser record);
}