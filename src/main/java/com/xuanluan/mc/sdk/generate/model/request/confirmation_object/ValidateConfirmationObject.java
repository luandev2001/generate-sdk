package com.xuanluan.mc.sdk.generate.model.request.confirmation_object;

import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateConfirmationObject<T> extends ConfirmationObjectDTO<T> {
    private String code;
}
