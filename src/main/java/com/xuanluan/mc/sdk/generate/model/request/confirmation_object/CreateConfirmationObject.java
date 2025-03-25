package com.xuanluan.mc.sdk.generate.model.request.confirmation_object;

import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class CreateConfirmationObject<T> extends ConfirmationObjectDTO<T> {
    private long expiredNum;
    private ChronoUnit period = ChronoUnit.MINUTES;
    private int lengthDigit;

    public int getLengthDigit() {
        return lengthDigit > 0 ? lengthDigit : 5;
    }
}
