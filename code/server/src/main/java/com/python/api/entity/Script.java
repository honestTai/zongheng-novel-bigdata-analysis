package com.python.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
      private Integer scriptId;

    private String scriptName;

    private String scriptDescription;

    private String scriptFilePath;


}
