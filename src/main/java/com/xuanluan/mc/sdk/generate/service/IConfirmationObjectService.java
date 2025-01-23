package com.xuanluan.mc.sdk.generate.service;

import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.CreateConfirmationObject;
import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.ValidateConfirmationObject;

public interface IConfirmationObjectService {
    <T> String create(CreateConfirmationObject<T> dto);

    <T> ConfirmationObject getLast(ConfirmationObjectDTO<T> request) ;

    <T> ConfirmationObject validate(ValidateConfirmationObject<T> request);

    <T> void deleteAllExpired(ConfirmationObjectDTO<T> request);
}
