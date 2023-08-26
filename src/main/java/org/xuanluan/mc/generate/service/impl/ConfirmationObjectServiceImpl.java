package org.xuanluan.mc.generate.service.impl;

import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xuanluan.mc.generate.domain.dto.ConfirmationObjectDTO;
import org.xuanluan.mc.generate.domain.entity.ConfirmationObject;
import org.xuanluan.mc.generate.repository.confirm.ConfirmationObjectRepository;
import org.xuanluan.mc.generate.service.ConfirmationObjectService;
import org.xuanluan.mc.generate.service.converter.ConfirmationConverter;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ConfirmationObjectServiceImpl implements ConfirmationObjectService {
    private final ConfirmationObjectRepository confirmationObjectRepository;

    @Override
    public <T> ConfirmationObject create(ConfirmationObjectDTO<T> dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        ConfirmationObject confirmationObject = ConfirmationConverter.toConfirmationObject(new ConfirmationObject(), dto);
        confirmationObject.setToken(GeneratorUtils.getRandomCode4Digits());
        AssertUtils.notBlank(confirmationObject.getObjectId(), "objectId");
        AssertUtils.notBlank(confirmationObject.getObject(), "object");
        AssertUtils.notBlank(confirmationObject.getToken(), "object");
        AssertUtils.notNull(confirmationObject.getExpiredAt(), "expiredAt");
        return confirmationObjectRepository.save(confirmationObject);
    }

    @Override
    public <T> ConfirmationObject getLast(Class<T> object, String objectId) {
        return confirmationObjectRepository.getLast(object.getSimpleName(), objectId);
    }

    @Override
    public <T> boolean isExpired(Class<T> object, String objectId) {
        return checkConfirmationObject(object, objectId) != null;
    }

    @Override
    public <T> ConfirmationObject resetWhenExpired(ConfirmationObjectDTO<T> dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        ConfirmationObject result = checkConfirmationObject(dto.getObject(), dto.getObjectId());
        return result != null ? result : create(dto, byUser);
    }

    private <T> ConfirmationObject checkConfirmationObject(Class<T> object, String objectId) {
        ConfirmationObject confirmationObject = getLast(object, objectId);
        boolean flag = confirmationObject == null || confirmationObject.getExpiredAt().before(new Date());
        return flag ? null : confirmationObject;
    }
}
