package com.python.api.service.impl;

import com.python.api.entity.Month;
import com.python.api.mapper.MonthMapper;
import com.python.api.service.MonthService;
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
public class MonthServiceImpl extends ServiceImpl<MonthMapper, Month> implements MonthService {

}
