package com.xuanluan.mc.sdk.generate.repository.confirm;

import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

public interface ConfirmationObjectRepositoryCustom {
    ConfirmationObject getLast(String object, String objectId, String type);

}
