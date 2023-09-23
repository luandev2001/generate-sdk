package com.xuanluan.mc.sdk.generate.domain.dto;

import com.xuanluan.mc.sdk.generate.domain.enums.PeriodTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfirmationObjectDTO<T> {
    private Class<T> object;
    private String objectId;
    private long expiredNum;
    private PeriodTime period;
    private String type;
    private int lengthDigit;

    public int getLengthDigit() {
        return lengthDigit > 0 ? lengthDigit : 5;
    }
}
