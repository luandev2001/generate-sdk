package com.xuanluan.mc.sdk.generate.service.converter;

import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.CreateConfirmationObject;

import java.time.Instant;

public class ConfirmationConverter {
    public static <T> ConfirmationObject toConfirmationObject(ConfirmationObject object, CreateConfirmationObject<T> dto) {
        object.setObjectId(dto.getObjectId());
        object.setObjectType(dto.getObject().getSimpleName());
        object.setExpiredAt(Instant.from(Instant.now().plus(dto.getExpiredNum(), dto.getPeriod())));
        object.setType(dto.getType());
        return object;
    }
}
