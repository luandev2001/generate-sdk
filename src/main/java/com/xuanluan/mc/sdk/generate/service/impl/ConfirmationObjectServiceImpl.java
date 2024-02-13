package com.xuanluan.mc.sdk.generate.service.impl;

import com.xuanluan.mc.sdk.generate.repository.confirm.ConfirmationObjectRepository;
import com.xuanluan.mc.sdk.service.locale.MessageAssert;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.service.ConfirmationObjectService;
import com.xuanluan.mc.sdk.generate.service.converter.ConfirmationConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class ConfirmationObjectServiceImpl implements ConfirmationObjectService {
    private final ConfirmationObjectRepository confirmationObjectRepository;
    private final MessageAssert messageAssert;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> String create(ConfirmationObjectDTO<T> dto, String byUser) {
        messageAssert.notNull(dto, "request");
        messageAssert.isTrue(dto.getExpiredNum() > 0, "expiredNum > 0");
        messageAssert.notBlank(dto.getObjectId(), "object_id");
        messageAssert.notBlank(dto.getType(), "type");

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
        messageAssert.isTrue(confirmationObject != null && confirmationObject.getToken().equals(convertToMd5(code)), "confirmation.invalid", "");
        messageAssert.isTrue(confirmationObject.getExpiredAt() != null && confirmationObject.getExpiredAt().after(currentDate), "confirmation.expired");
    }

    private String convertToMd5(String token) {
        return DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
    }
}
