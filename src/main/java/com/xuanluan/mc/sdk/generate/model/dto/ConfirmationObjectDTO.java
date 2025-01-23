package com.xuanluan.mc.sdk.generate.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationObjectDTO<T> {
    private Class<T> object;
    private String objectId;
    private String type;
}
