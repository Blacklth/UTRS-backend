package com.blacklth.utrs.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : LiaoTianHong
 * @date : 2019/7/26 8:43
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);paginationInterceptor.setLimit
        return new PaginationInterceptor();
    }
}
