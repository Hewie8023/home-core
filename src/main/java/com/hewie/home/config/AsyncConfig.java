package com.hewie.home.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 参考：SpringBoot 如何实现异步编程
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * 采用外部配置的形式将线程池参数进行初始化，然后注入到Spring容器中
     *
     * @return
     */
    @Bean(name = "asyncPoolTaskExecutor")
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最小数量
        executor.setCorePoolSize(2);
        //最大线程数
        executor.setMaxPoolSize(3);
        //允许空闲时间 默认TimeUnit.SECONDS
        executor.setKeepAliveSeconds(200);
        //缓冲队列大小 队列大小>0缓冲阻塞队列(LinkedBlockingQueue);<=0 无数据缓冲队列(SynchronousQueue)---具体看源码
        executor.setQueueCapacity(1000);
        //线程名前缀定义
        executor.setThreadNamePrefix("asyn-executor-");

        //拒绝task处理策略 - 四种处理策略
        //(1)ThreadPoolExecutor.AbortPolicy策略，是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
        //(2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
        //(3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
        ///(4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）.
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        executor.initialize();
        return executor;
    }

    /**
     * 指定默认线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> LOGGER.error("AsyncConfig-error 异步方法处理异常, method: {},ex:{}", method.getName(), ex.getMessage());

    }
}