package com.fan.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fan.apiclientsdk.utils.SignUtils;
import com.fan.apicommon.model.entity.User;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class ApiClient {

    private String accessKey;

    private String secretKey;

    public ApiClient(String accessKey,String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private static final String API_GATEWAY_HOST = "http://localhost:8090";

    public String getByGet(String param){
        HashMap<String,Object> map = new HashMap<>(16);
        map.put("param",param);
        String result = HttpUtil.get(API_GATEWAY_HOST + "/api/test/get",map);
        return result;
    }

    public String getByPost(String param){
        HashMap<String,Object> map = new HashMap<>(16);
        map.put("param",param);
        String result = HttpUtil.post(API_GATEWAY_HOST + "/api/test/post", map);
        return result;
    }

    public String getUserByPost(User user){
        String jsonStr = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(API_GATEWAY_HOST + "/api/test/user")
            .addHeaders(getHeader(jsonStr))
            .body(jsonStr)
            .execute();
        String result = httpResponse.body();
        return result;
    }

    public Map<String,String> getHeader(String body){
        Map<String,String> map = new HashMap<>(16);
        map.put("body",body);
        map.put("sign", SignUtils.sign(body,secretKey));
        map.put("accessKey",accessKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        return map;
    }

}
