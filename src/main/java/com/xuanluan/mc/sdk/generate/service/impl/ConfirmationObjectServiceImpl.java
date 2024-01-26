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
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
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
    public <T> String create(ConfirmationObjectDTO<T> dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        AssertUtils.isTrue(dto.getExpiredNum() > 0, "expiredNum > 0");
        AssertUtils.notBlank(dto.getObjectId(), "object_id");
        AssertUtils.notBlank(dto.getType(), "type");

        String token = GeneratorUtils.generateCodeDigits(dto.getLengthDigit());
        ConfirmationObject confirmationObject = ConfirmationConverter.toConfirmationObject(new ConfirmationObject(), dto);
        confirmationObject.setToken(convertToMd5(token));
        confirmationObject.setCreatedBy(byUser);
        confirmationObjectRepository.save(confirmationObject);
        return token;
    }

    @Override
    public <T> ConfirmationObject getLast(Class<T> object, String objectId, String type) {
        return confirmationObjectRepository.getLast(object.getSimpleName(), objectId, type);
    }

    @Override
    public <T> void validate(Class<T> object, String objectId, String type, String code) {
        Date currentDate = new Date();
        ConfirmationObject confirmationObject = getLast(object, objectId, type);
        AssertUtils.isTrue(confirmationObject != null && confirmationObject.getToken().equals(convertToMd5(code)), "confirmation.error.invalid", "");
        AssertUtils.isTrue(confirmationObject.getExpiredAt().after(currentDate), "confirmation.error.expired", HttpStatus.REQUEST_TIMEOUT);
    }

    private String convertToMd5(String token) {
        return DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
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
