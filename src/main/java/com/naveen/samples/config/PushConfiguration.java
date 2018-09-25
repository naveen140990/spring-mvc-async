package com.naveen.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableWebMvc
@EnableAsync
@PropertySource("classpath:executor.properties")
public class PushConfiguration implements WebMvcConfigurer {


    @Value("${http.request.timeout.millisec}")
    private String timeout;

    @Value("${http.threadpool.coresize}")
    private String poolCoreSize;

    @Value("${http.threadpool.maxsize}")
    private String poolMaxSize;

    @Value("${http.threadpool.queuecapacity}")
    private String poolQueueCapacity;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }


    @Bean
    public AsyncTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor(){
            private static final long serialVersionUID = 1L;

            {
                setCorePoolSize(Integer.parseInt(poolCoreSize));
                setMaxPoolSize(Integer.parseInt(poolMaxSize));
                setQueueCapacity(Integer.parseInt(poolQueueCapacity   ));
                setRejectedExecutionHandler(new RejectedExecutionHandler() {

                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        logger.error("Request Rejected, Check for executor values");
                    }
                });
            }
        };

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new RequestInterceptor());
    }



    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(asyncTaskExecutor);
        configurer.setDefaultTimeout(Integer.parseInt(timeout));
    }


}
