// src/main/java/.../config/StaticResourceConfig.java
package ktaivlethminiproject.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1) 절대경로로 변환
        Path uploadDir = Paths.get("book_covers").toAbsolutePath();
        String location = "file:" + uploadDir.toString() + "/";

        System.out.println("📂 정적 리소스 위치 = " + location);

        // 2) /images/** 로 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations(location);
                // .setCacheControl(CacheControl.noCache()); // 캐시 끄기(선택)

    }
}