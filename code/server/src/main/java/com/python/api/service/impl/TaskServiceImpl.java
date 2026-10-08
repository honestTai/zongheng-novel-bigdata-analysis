package com.python.api.service.impl;

import com.python.api.entity.Script;
import com.python.api.entity.Task;
import com.python.api.mapper.ScriptMapper;
import com.python.api.mapper.TaskMapper;
import com.python.api.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    ScriptMapper scriptMapper;

    @Override
    public void start(Integer id) {
        try {
            //读取任务信息
            Task task = this.getById(id);
            task.setTaskStartTime(new Date());
            //读取脚本
            Script script = scriptMapper.selectById(task.getTaskScriptId());
            // 设置要执行的Python脚本路径

            // 创建进程构建器,这里使用python的虚拟环境
            Process process = Runtime.getRuntime().exec("python " + script.getScriptFilePath());

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 读取进程错误输出
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
                task.setInfo(errorLine);
            }
            // 等待进程结束
            int exitCode = process.waitFor();
            // 判断进程执行是否成功
            if (exitCode == 0) {
                //执行成功
                task.setTaskStatus("成功");
            } else {
                //执行失败
                // 设置失败标识
                task.setTaskStatus("失败");
            }
            task.setTaskEndTime(new Date());
            this.updateById(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
