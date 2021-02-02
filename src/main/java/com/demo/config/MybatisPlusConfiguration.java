package com.demo.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.sf.jsqlparser.expression.Expression;
@Configuration
public class MybatisPlusConfiguration {
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
			@Override
			public String getTenantIdColumn() {
				return "manager_id";
			}
			//设置租户的值
			@Override
			public Expression getTenantId() {
				return new LongValue(1088248166370832385L);
			}
			// 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
			@Override
			public boolean ignoreTable(String tableName) {
				return !"user".equalsIgnoreCase(tableName);
			}
		}));
		// 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
		// 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}

	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}

}
