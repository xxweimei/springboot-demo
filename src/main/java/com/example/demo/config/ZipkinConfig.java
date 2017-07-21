package com.example.demo.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpAdapter;
import brave.http.HttpSampler;
import brave.http.HttpTracing;
import brave.okhttp3.TracingCallFactory;
import brave.spring.webmvc.TracingHandlerInterceptor;
import com.example.demo.commons.utils.ConfigUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * functional description
 * Created by Sandy
 * on 27/05/2017.
 */
@Configuration
@Import({TracingHandlerInterceptor.class})
public class ZipkinConfig extends WebMvcConfigurerAdapter {

    @Resource
    private TracingHandlerInterceptor serverInterceptor;

    @Bean
    Sender sender() {
        return OkHttpSender.create(ConfigUtils.conf().getString("zipkin.server"));
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    Reporter<Span> reporter() {
        return AsyncReporter.builder(sender()).build();
    }

    /**
     * Controls aspects of tracing such as the name that shows up in the UI
     */
    @Bean
    Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName(ConfigUtils.conf().getString("zipkin.service.name"))
                .currentTraceContext(MDCCurrentTraceContext.create())
                .reporter(reporter()).build();
    }

    @Bean
    HttpTracing httpTracing() {
        return HttpTracing.create(tracing()).toBuilder()
                .serverSampler(new HttpSampler() {
                    @Override
                    public <Req> Boolean trySample(HttpAdapter<Req, ?> httpAdapter, Req req) {
                        if (httpAdapter.path(req).startsWith("/status")) return false;
                        return null;
                    }
                }).build();
    }

    @Bean
    Call.Factory callFactory() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return TracingCallFactory.create(httpTracing(), client);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInterceptor);
    }
}
