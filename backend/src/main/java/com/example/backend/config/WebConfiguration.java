package com.example.backend.config;

import com.example.backend.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 添加token拦截/跨域
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    //构造方法
    public WebConfiguration(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        // 排除拦截，除了注册登录(此时还没token)，其他都拦截
        // 除了注册登陆和静态资源的请求，其他都拦截，后面部署的时候可能还要加上favicon和模板
        excludePath.add("/user/register");  //登录
        excludePath.add("/user/login");     //注册
        excludePath.add("/static/**");      //静态资源
        excludePath.add("/file/upload");    //文件上传
        excludePath.add("/file/download"); // 文件下载 文件上传下载上三方组件做的，所以不能拦截

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
