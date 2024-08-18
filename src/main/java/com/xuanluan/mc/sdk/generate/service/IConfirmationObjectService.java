package com.xuanluan.mc.sdk.generate.service;

import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;

public interface IConfirmationObjectService {
    <T> String create(ConfirmationObjectDTO<T> dto);

    <T> ConfirmationObject getLast(Class<T> object, String objectId, String type);

    <T> ConfirmationObject validate(Class<T> object, String objectId, String type, String code);

    <T> void deleteAllExpired(Class<T> object, String objectId, String type);
}
