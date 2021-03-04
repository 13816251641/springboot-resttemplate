package com.lujieni.resttemplate.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * @Package: com.lujieni.tkmapper.domain.po
 * @ClassName: PersonPO
 * @Author: lujieni
 * @Description: t_person实体类
 * @Date: 2020-12-17 17:27
 * @Version: 1.0
 */
public class PersonPO extends BasicEntity {

    private String name;

    private Integer myAge;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMyAge() {
        return myAge;
    }

    public void setMyAge(Integer myAge) {
        this.myAge = myAge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}