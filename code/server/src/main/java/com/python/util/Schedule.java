package com.python.util;

import com.python.api.entity.Task;
import com.python.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Schedule {

    @Autowired
    TaskService taskService;

    /**
     * 每天12点执行的任务
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void dailyTaskOne() {
        // 每天12点执行的任务逻辑
        Task task = new Task("点击榜每天定时获取数据任务", 8, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每天12点执行的第二个任务
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void dailyTaskTwo() {
        // 每天12点执行的第二个任务逻辑
        Task task = new Task("推荐榜每天数据获取任务", 15, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每周一12点执行的任务
     */
    @Scheduled(cron = "0 0 12 ? * MON")
    public void weeklyTaskOne() {
        // 每周一12点执行的任务逻辑
        Task task = new Task("点击榜每周数据获取任务", 11, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每周一12点执行的第二个任务
     */
    @Scheduled(cron = "0 0 12 ? * MON")
    public void weeklyTaskTwo() {
        // 每周一12点执行的第二个任务逻辑
        Task task = new Task("推荐榜每周数据获取任务", 17, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每月第一天12点执行的任务
     */
    @Scheduled(cron = "0 0 12 1 * ?")
    public void monthlyTaskOne() {
        // 每月第一天12点执行的任务逻辑
        Task task = new Task("月票数据获取", 14, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每月第一天12点执行的第二个任务
     */
    @Scheduled(cron = "0 0 12 1 * ?")
    public void monthlyTaskTwo() {
        // 每月第一天12点执行的第二个任务逻辑
        Task task = new Task("推荐榜每月数据获取任务", 10, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }

    /**
     * 每月第一天12点执行的第三个任务
     */
    @Scheduled(cron = "0 0 12 1 * ?")
    public void monthlyTaskThree() {
        // 每月第一天12点执行的第三个任务逻辑
        Task task = new Task("点击榜每月数据获取任务", 16, new Date());
        taskService.save(task);
        taskService.start(task.getTaskId());
    }
}
