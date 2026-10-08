package com.python.api.service.impl;

import com.python.api.entity.Click;
import com.python.api.mapper.ClickMapper;
import com.python.api.service.ClickService;
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
public class ClickServiceImpl extends ServiceImpl<ClickMapper, Click> implements ClickService {

}
