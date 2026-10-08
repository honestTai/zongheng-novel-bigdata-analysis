package com.python.api.bean.vto;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * 后台用户登录参数接受类
 */
@Data
public class BackLoginVto {

    @NotNull
    private String rentPcUserNum;

    @NotNull
    private String rentPcUserPwd;
}
