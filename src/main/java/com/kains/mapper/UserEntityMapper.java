package com.kains.mapper;

/**
 * @author kains
 * @date 2021/12/20
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kains.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 这里是UserEntityMapper的实现类
 */
@Mapper
public interface UserEntityMapper extends BaseMapper<UserEntity> {


}
