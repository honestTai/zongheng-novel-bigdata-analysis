package com.python.api.mapper;

import com.python.api.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

}
