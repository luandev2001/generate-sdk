package com.xuanluan.mc.sdk.generate.service;

import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

public interface ConfirmationObjectService {
    <T> ConfirmationObject create(ConfirmationObjectDTO<T> dto, String byUser);

    <T> ConfirmationObject getLast(Class<T> object, String objectId);

    <T> boolean isExpired(Class<T> object, String objectId);

    <T> ConfirmationObject resetWhenExpired(ConfirmationObjectDTO<T> dto, String byUser);
}
