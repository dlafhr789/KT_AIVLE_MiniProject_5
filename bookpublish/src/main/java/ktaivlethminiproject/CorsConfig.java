// package ktaivlethminiproject;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// @Configuration
// public class CorsConfig {

//     @Bean
//     public CorsFilter corsFilter() {
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);      // 쿠키/인증정보 허용
//         config.addAllowedOrigin("*");          // 모든 출처 허용 (addAllowedOriginPattern 대신)
//         config.addAllowedHeader("*");          // 모든 헤더 허용
//         config.addAllowedMethod("*");          // 모든 메서드 허용

//         // config.setAllowCredentials(true);
//         // config.addAllowedOriginPattern("*");  // ★ addAllowedOriginPattern
//         // config.addAllowedHeader("*");
//         // config.addAllowedMethod("*");

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config); // 모든 경로에 적용

//         return new CorsFilter(source);
//     }
// }