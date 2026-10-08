package com.python.api.mapper;

import com.python.api.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * 
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
