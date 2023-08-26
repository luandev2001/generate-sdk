package org.xuanluan.mc.generate.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.xuanluan.mc.generate.domain.enums.PeriodTime;

@Getter
@Setter
@Builder
public class ConfirmationObjectDTO<T> {
    private Class<T> object;
    private String objectId;
    private long expiredNum;
    private PeriodTime period;
}
