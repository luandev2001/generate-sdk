package com.xuanluan.mc.sdk.generate.service.converter;

import com.xuanluan.mc.sdk.generate.domain.enums.PeriodTime;
import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

import java.util.Date;

public class ConfirmationConverter {
    public static <T> ConfirmationObject toConfirmationObject(ConfirmationObject object, ConfirmationObjectDTO<T> dto) {
        object.setObjectId(dto.getObjectId());
        object.setObject(dto.getObject().getSimpleName());
        object.setExpiredAt(new Date(object.getCreatedAt().getTime() + PeriodTime.convert(dto.getExpiredNum(), dto.getPeriod())));
        object.setType(dto.getType() != null ? dto.getType() : object.getObject());
        return object;
    }
}