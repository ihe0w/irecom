//package com.example.business_server.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.unit.DataSize;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.MultipartConfigElement;
//import java.util.Objects;
//
//@Configuration
//public class MultiPartConfig extends WebMvcConfigurationSupport {
//    @Value("${constant.imagePath}")
//    private String imagePath;
//
//    @Value("${constant.videoPath}")
//    private String videoPath;
//
//    @Value("${constant.resPath}")
//    private String resPath;
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory=new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.parse("100MB"));
//        factory.setMaxRequestSize(DataSize.parse("100MB"));
//        return factory.createMultipartConfig();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        if (resPath.equals("") || resPath.equals("${constant.resPath}")){
//            String path= Objects.requireNonNull(MultiPartConfig.class.getClassLoader().getResource("")).getPath();
//            if (path.indexOf(".jar")>0){
//                path=resPath.substring(0,path.indexOf(".jar"));
//            }else if (path.indexOf("classes")>0){
//                path="file:"+path.substring(0,path.indexOf("classes"));
//            }
//            path=path.substring(0,path.lastIndexOf("/"))+"/images/";
//            resPath=path;
//        }
//        registry.addResourceHandler("/res/**").addResourceLocations(resPath);
//        super.addResourceHandlers(registry);
//    }
//
//}
