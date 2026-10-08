package com.python.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
      private Integer taskId;

    private String taskName;

    private Integer taskScriptId;

    @TableField(exist = false)
    private Script script;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date taskStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date taskEndTime;

    private String taskStatus;

    private String info;


    public Task(String taskName, Integer taskScriptId, Date taskStartTime) {
        this.taskName = taskName;
        this.taskScriptId = taskScriptId;
        this.taskStartTime = taskStartTime;
        this.taskStatus = "待执行";
    }

    public Task() {
    }
}
