package com.xuanluan.mc.sdk.generate.service;

import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

public interface ConfirmationObjectService<T> {
    String create(ConfirmationObjectDTO<T> dto);

    ConfirmationObject getLast(Class<T> object, String objectId, String type);

    ConfirmationObject validate(Class<T> object, String objectId, String type, String code);

    void deleteAllExpired(Class<T> object, String objectId, String type);
}
