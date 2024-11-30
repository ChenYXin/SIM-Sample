package org.itzixi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class StaticResourceConfig extends WebMvcConfigurationSupport {
    /**
     * 添加静态资源映射路径，图片、视频、音频等都放在classpath下的static中
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * addResourceHandler:指的是对外暴露的访问路径映射
         * addResourceLocations：指的本地文件所在的目录
         */
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/Users/chenyuexin/Downloads/1/");

        // http://127.0.0.1:55/static/face/1862444433958567937.jpeg
        super.addResourceHandlers(registry);
    }
}
