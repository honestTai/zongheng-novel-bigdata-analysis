package com.python.api.mapper;

import com.python.api.entity.Task;
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
public interface TaskMapper extends BaseMapper<Task> {

}
