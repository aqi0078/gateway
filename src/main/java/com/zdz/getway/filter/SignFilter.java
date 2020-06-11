package com.zdz.getway.filter;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author zhangdezhi
 * @date 2020-06-11
 */
@Component
@Slf4j
@Order(-100)
@RefreshScope
public class SignFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString());
        String sign = exchange.getRequest().getQueryParams().getFirst("sign");
        log.info(sign);
// 没有验签的参数
        if (StringUtils.isBlank(sign)) {
            String ste="sss";
            return exchange.getResponse().writeWith(
                    Mono.just(Objects.requireNonNull(this.getBodyBuffer(exchange.getResponse(), ste).get())));
        }
        return chain.filter(exchange);
    }

    /**
     * 封装返回值
     *
     * @param response
     * @param resultVO
     * @return
     */
    private WeakReference<DataBuffer> getBodyBuffer(ServerHttpResponse response, String resultVO) {
        return new WeakReference<>(response.bufferFactory().wrap(resultVO.getBytes()));
    }
}
