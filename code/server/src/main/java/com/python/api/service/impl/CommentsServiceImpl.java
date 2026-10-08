package com.python.api.service.impl;

import com.python.api.entity.Comments;
import com.python.api.mapper.CommentsMapper;
import com.python.api.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author arthur
 * @since 2023-12-04
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

}
