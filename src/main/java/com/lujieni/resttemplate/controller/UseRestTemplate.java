package com.lujieni.resttemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.lujieni.resttemplate.common.RequestDTO;
import com.lujieni.resttemplate.domain.po.PersonPO;
import com.lujieni.resttemplate.interceptor.SignatureRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @Package: com.lujieni.resttemplate.controller
 * @ClassName: UseRestTemplate
 * @Author: lujieni
 * @Description: 使用restTemplate
 * @Date: 2020-12-25 16:39
 * @Version: 1.0
 */
@RestController
public class UseRestTemplate {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Description:
     * @param
     * @return:
     * @Author: lujieni
     * @Date: 2021/3/4
     */
    @GetMapping("/query-person-by-name")
    public List<PersonPO> queryPersonByName(){
        RequestDTO<String> requestDTO = new RequestDTO<>();
        requestDTO.setData("王明");
        List<PersonPO> result = postForObject("http://localhost:8080/query-person-by-name", requestDTO, new TypeReference<List<PersonPO>>(){});
        logger.info(result.toString());
        return result;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        headers.set("Accept", "application/json");
        return headers;
    }


    private <T> T postForObject(String url, Object request, TypeReference<T> typeReference, Object... urlVariables) {
        String requestJson = JSON.toJSONString(request);
        HttpEntity<String> entity = new HttpEntity(requestJson,getHttpHeaders());//没有getHttpHeaders()不可以

        this.restTemplate.setInterceptors(Arrays.asList(new SignatureRequestInterceptor()));

        String json = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class, urlVariables).getBody();
        return JSON.parseObject(json, typeReference);
    }


}