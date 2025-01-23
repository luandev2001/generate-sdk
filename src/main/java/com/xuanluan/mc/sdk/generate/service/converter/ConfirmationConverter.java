package com.xuanluan.mc.sdk.generate.service.converter;

import com.xuanluan.mc.sdk.generate.model.enums.PeriodTime;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.CreateConfirmationObject;

import java.util.Date;

public class ConfirmationConverter {
    public static <T> ConfirmationObject toConfirmationObject(ConfirmationObject object, CreateConfirmationObject<T> dto) {
        object.setObjectId(dto.getObjectId());
        object.setObjectType(dto.getObject().getSimpleName());
        object.setExpiredAt(new Date(new Date().getTime() + PeriodTime.convert(dto.getExpiredNum(), dto.getPeriod()) * 1000));
        object.setType(dto.getType());
        return object;
    }
}
