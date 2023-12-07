package com.xuanluan.mc.sdk.generate.service.impl;

import com.xuanluan.mc.sdk.generate.repository.confirm.ConfirmationObjectRepository;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import com.xuanluan.mc.sdk.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.service.ConfirmationObjectService;
import com.xuanluan.mc.sdk.generate.service.converter.ConfirmationConverter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ConfirmationObjectServiceImpl implements ConfirmationObjectService {
    private final ConfirmationObjectRepository confirmationObjectRepository;

    public ConfirmationObjectServiceImpl(ConfirmationObjectRepository confirmationObjectRepository) {
        new ConfirmationMessage().init();
        this.confirmationObjectRepository = confirmationObjectRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> ConfirmationObject create(ConfirmationObjectDTO<T> dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        AssertUtils.isTrue(dto.getExpiredNum() > 0, "expiredNum > 0");
        ConfirmationObject confirmationObject = ConfirmationConverter.toConfirmationObject(new ConfirmationObject(), dto);
        confirmationObject.setToken(GeneratorUtils.generateCodeDigits(dto.getLengthDigit()));
        confirmationObject.setCreatedBy(byUser);
        AssertUtils.notBlank(confirmationObject.getObjectId(), "object_id");
        AssertUtils.notBlank(confirmationObject.getObject(), "object");
        AssertUtils.notBlank(confirmationObject.getToken(), "token");
        AssertUtils.notBlank(confirmationObject.getType(), "type");
        AssertUtils.notNull(confirmationObject.getExpiredAt(), "expiredAt");
        return confirmationObjectRepository.save(confirmationObject);
    }

    @Override
    public <T> ConfirmationObject getLast(Class<T> object, String objectId, String type) {
        return confirmationObjectRepository.getLast(object.getSimpleName(), objectId, type);
    }

    @Override
    public <T> void validate(Class<T> object, String objectId, String type, String code) {
        ConfirmationObject confirmationObject = getLast(object, objectId, type);
        AssertUtils.isTrue(confirmationObject != null && confirmationObject.getToken().equals(code), "confirmation.error.invalid", "");
        AssertUtils.isTrue(confirmationObject.getExpiredAt().after(new Date()), "confirmation.error.expired", HttpStatus.REQUEST_TIMEOUT);
    }

    private static class ConfirmationMessage extends MessageUtils {
        private ConfirmationMessage() {

        }

        private void init() {
            put(
                    "confirmation.error.expired",
                    Message.builder().vn("Mã xác thực đã hết hạn").en("Verification code has expired").build()
            );
            put(
                    "confirmation.error.invalid",
                    Message.builder().vn("Mã xác thực không hợp lệ").en("Verification code is not valid").build()
            );
        }
    }
}
