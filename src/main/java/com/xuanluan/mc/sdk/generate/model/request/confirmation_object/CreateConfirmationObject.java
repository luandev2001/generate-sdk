package com.xuanluan.mc.sdk.generate.model.request.confirmation_object;

import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.model.enums.PeriodTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConfirmationObject<T> extends ConfirmationObjectDTO<T> {
    private long expiredNum;
    private PeriodTime period;
    private int lengthDigit;

    public int getLengthDigit() {
        return lengthDigit > 0 ? lengthDigit : 5;
    }
}
