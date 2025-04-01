package com.xuanluan.mc.sdk.generate.model.entity;

import com.xuanluan.mc.sdk.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ConfirmationObject extends BaseEntity<UUID> {
    @Column(nullable = false, updatable = false)
    private String objectType;
    @Column(nullable = false, updatable = false)
    private String objectId;
    @Column(nullable = false, updatable = false)
    private String token;
    @Column(nullable = false, updatable = false)
    private Instant expiredAt;
    @Column(nullable = false, updatable = false)
    private String type;
}
