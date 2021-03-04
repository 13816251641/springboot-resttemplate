package com.lujieni.resttemplate.common;


import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
public class RequestDTO<T> implements Serializable {

    private static final long serialVersionUID = -8255475298369413350L;

    /**
     * 时间戳
     */
    private long timestamp;


    /**
     * 每一个请求过来都有一个保人的 messageId ，每次返回时也需要返回
     */
    private String messageId;


    /**
     * 初建业务数据对象
     */
    @Valid
    private T data;

}
