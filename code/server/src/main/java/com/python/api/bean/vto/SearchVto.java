package com.python.api.bean.vto;

import lombok.Data;

import java.util.List;

@Data
public class SearchVto {

    private Integer pageSize;

    private Integer pageIndex;

    private Integer id;

    private String likeString;

    private String orderBy;

    private String rankNo;

    private String type;

    private String isPython;

    private String bookId;
}