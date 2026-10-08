package com.python.api.bean.dto;

import com.python.api.entity.Script;
import lombok.Data;

import java.util.List;

@Data
public class InfoDto {


    private List<Script> scripts;


    public InfoDto( List<Script> scripts) {
        this.scripts = scripts;
    }
}
