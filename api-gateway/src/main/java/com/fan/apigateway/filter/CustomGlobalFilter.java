package com.fan.apigateway.filter;

import com.fan.apiclientsdk.utils.SignUtils;
import com.fan.apicommon.model.entity.InterfaceInfo;
import com.fan.apicommon.model.entity.User;
import com.fan.apicommon.service.InnerInterfaceInfoService;
import com.fan.apicommon.service.InnerUserInterfaceInfoService;
import com.fan.apicommon.service.InnerUserService;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 全局过滤
 * @author 19677
 */
@Slf4j
@ComponentScan
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static final String INTERFACE_HOST = "http://localhost:8091";

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //1.请求日志
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("" + request.getId());
        log.info("" + path);
        log.info("" + method);
        log.info("" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostName();
        log.info("" + sourceAddress);
        log.info("" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();
        //2. 访问控制 - 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //3. 用户签名认证
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String sign = headers.getFirst("sign");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        //todo 实际情况应该是去数据库中查是否已分配给用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokeUser error",e);
        }
        if ( invokeUser == null){
            return handleNoAuth(response);
        }
        if (Long.parseLong(nonce) > 10000L){
            return handleNoAuth(response);
        }
        //时间和当前时间不能超过五分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES){
            return handleNoAuth(response);
        }
        //实际情况中是从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.sign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)){
            handleNoAuth(response);
        }
        //4. 请求的模拟接口是否存在，以及请求方法是否匹配
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }
        // todo 是否还有调用次数
        // 5. 请求转发，调用模拟接口 + 响应日志
        //        Mono<Void> filter = chain.filter(exchange);
        //        return filter;
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange,GatewayFilterChain chain,Long interfaceInfoId,Long userId){
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            //数据缓存工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK){
                //装饰，增强能力
                ServerHttpResponseDecorator decoratorResponse =
                    new ServerHttpResponseDecorator(originalResponse) {
                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body){
                            log.info("body instanceof Flux: {}",(body instanceof Flux));
                            if (body instanceof Flux){
                                Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                                //返回值写数据
                                //拼接字符串
                                return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //调用成功，接口调用次数
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                        }catch (Exception e){
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//
                                        //构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);
                                        //打印日志
                                        return bufferFactory.wrap(content);
                                    })
                                );
                            }else {
                                //调用失败返回一个错误码
                                log.error("<--- {} 响应code异常", getStatusCode());
                            }
                            return super.writeWith(body);
                        }
                    };
                //设置 response 对象为装饰类
                return chain.filter(exchange.mutate().response(decoratorResponse).build());
            }
            //降级处理返回数据
            return chain.filter(exchange);
        }catch (Exception e){
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
