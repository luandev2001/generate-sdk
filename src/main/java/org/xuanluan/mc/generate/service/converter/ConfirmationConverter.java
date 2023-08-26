package org.xuanluan.mc.generate.service.converter;

import org.xuanluan.mc.generate.domain.dto.ConfirmationObjectDTO;
import org.xuanluan.mc.generate.domain.entity.ConfirmationObject;
import org.xuanluan.mc.generate.domain.enums.PeriodTime;

import java.util.Date;

public class ConfirmationConverter {
    public static <T> ConfirmationObject toConfirmationObject(ConfirmationObject object, ConfirmationObjectDTO<T> dto) {
        object.setObjectId(dto.getObjectId());
        object.setObject(dto.getObject().getSimpleName());
        object.setExpiredAt(new Date(object.getExpiredAt().getTime() + PeriodTime.convert(dto.getExpiredNum(), dto.getPeriod())));
        return object;
    }
}
