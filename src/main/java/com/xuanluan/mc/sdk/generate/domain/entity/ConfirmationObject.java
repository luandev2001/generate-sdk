package com.xuanluan.mc.sdk.generate.domain.entity;

import com.xuanluan.mc.sdk.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Entity
public class ConfirmationObject extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String object;
    @Column(nullable = false, updatable = false)
    private String objectId;
    @Column(nullable = false, updatable = false, length = 50)
    private String token;
    @Column(nullable = false, updatable = false)
    private Date expiredAt;
    @Column(nullable = false, updatable = false, length = 20)
    private String type;
}
