package org.seckill.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ay_user_kill_product")
public class AyUserKillProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //定义自增
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer state;
    private Date createTime;

    @Override
    public String toString() {
        return "AyUserKillProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", userId=" + userId +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
