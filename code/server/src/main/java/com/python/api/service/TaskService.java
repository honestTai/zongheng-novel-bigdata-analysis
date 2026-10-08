package com.python.api.service;

import com.python.api.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * 
 */
public interface TaskService extends IService<Task> {

    void start(Integer id);
}
