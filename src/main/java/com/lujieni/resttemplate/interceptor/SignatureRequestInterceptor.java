package com.lujieni.resttemplate.interceptor;

import com.lujieni.resttemplate.util.MD5Utils;
import com.lujieni.resttemplate.util.MapUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.lujieni.resttemplate.interceptor
 * @ClassName: SignatureRequestInterceptor
 * @Author: lujieni
 * @Description: 调用签名接口的拦截器
 * @Date: 2021-03-04 14:36
 * @Version: 1.0
 */
@Data
public class SignatureRequestInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * apiKey不是公钥
     */
    private String apiKey = "123";

    /**
     * 签名私钥,供应商和我们都存有!!!
     */
    private String securityKey = "456";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //调用inner的地址都需要进行签名
        String requestUrl = request.getURI().toString();
        HttpMethod method = request.getMethod();
        //获取请求参数
        String paramString = "";
        if (HttpMethod.GET.equals(method)) {
            String queryString = request.getURI().getQuery();
            logger.info("queryString:" + queryString);
            //多查询参数进行排序
            Map<String, Object> paramMap = new HashMap<>();
            if (!StringUtils.isEmpty(queryString)) {
                String[] params = queryString.split("&");
                for (String param : params) {
                    if (!StringUtils.isEmpty(param)) {
                        String[] paramPair = param.split("=");
                        if (paramPair != null && paramPair.length == 2) {
                            paramMap.put(paramPair[0], paramPair[1]);
                        }
                    }
                }
            }
            paramString = MapUtils.createLinkString(paramMap);
        } else {
            paramString = new String(body, Charset.forName("UTF-8"));
        }
        /*
            解密的时候ApiKey先要对,其次SecurityKey也要对
         */
        String signStr = request.getURI().getPath() + "|"
                + paramString + "|"
                + getApiKey() + "|"
                + getSecurityKey();
        String signResult = MD5Utils.sign(signStr, "UTF-8");
        HttpHeaders headers = request.getHeaders();
        headers.add("ApiKey", getApiKey());
        headers.add("Authorization", signResult);
        headers.add("TimeStamp", String.valueOf(System.currentTimeMillis()));
        logger.info("request the itf:" + requestUrl);
        logger.info("signStr:" + signStr);
        logger.info("signResult:" + signResult);
        ClientHttpResponse response = execution.execute(request, body);
        logger.info("request the itf success");
        return response;

    }
}