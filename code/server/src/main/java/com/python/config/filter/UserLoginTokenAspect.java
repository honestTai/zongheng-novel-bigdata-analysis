package com.python.config.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.python.util.jwt.JwtUtil;
import com.python.api.bean.general.result.ResultException;
import com.python.api.bean.general.result.ResultStatus;
import com.python.api.bean.threadLocal.UserLocal;
import com.python.api.entity.User;
import com.python.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * 登录拦截实现类，实现登录拦截
 */

public class UserLoginTokenAspect implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws ResultException {
        // 判断 object 是否为 HandlerMethod 类型
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        // 将 object 强制转换为 HandlerMethod 类型
        HandlerMethod handlerMethod = (HandlerMethod) object;

        // 获取 handlerMethod 对应的 Method 对象
        Method method = handlerMethod.getMethod();

        // 判断 method 是否存在 isLogin 注解
        if (method.isAnnotationPresent(isLogin.class)) {
            // 获取 isLogin 注解的 required 属性
            if (method.getAnnotation(isLogin.class).required()) {
                // 从 http 请求头中取出 token
                String token = httpServletRequest.getHeader("token");

                // 判断是否存在 token
                if (token == null) {
                    throw new ResultException(ResultStatus.NO_LOGIN);
                }

                // 验证 token
                verifyToken(token);

                return true;
            }
        }

        return true;
    }


    //验证Token是否正确，是否有效
    private void verifyToken(String token) throws ResultException {
        //解析得到用户账号
        String number = JwtUtil.getNumber(token);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", number));
        if (user != null) {
            if (!JwtUtil.verify(token, user.getUsername(), user.getPassword())) {
                throw new ResultException(ResultStatus.NO_LOGIN);
            }
            UserLocal.setUser(user);
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
