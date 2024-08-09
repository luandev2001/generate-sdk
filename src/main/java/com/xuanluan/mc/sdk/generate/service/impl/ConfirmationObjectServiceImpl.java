package com.xuanluan.mc.sdk.generate.service.impl;

import com.xuanluan.mc.sdk.generate.repository.confirm.ConfirmationObjectRepository;
import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import com.xuanluan.mc.sdk.generate.domain.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.service.ConfirmationObjectService;
import com.xuanluan.mc.sdk.generate.service.converter.ConfirmationConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationObjectServiceImpl<T> implements ConfirmationObjectService<T> {
    private final ConfirmationObjectRepository confirmationObjectRepository;
    private final MessageAssert messageAssert;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(ConfirmationObjectDTO<T> dto) {
        messageAssert.notNull(dto, "request");
        messageAssert.isTrue(dto.getExpiredNum() > 0, "expiredNum > 0");
        messageAssert.notBlank(dto.getObjectId(), "object_id");
        messageAssert.notBlank(dto.getType(), "type");

        String token = GeneratorUtils.randomDigits(dto.getLengthDigit());
        ConfirmationObject confirmationObject = ConfirmationConverter.toConfirmationObject(new ConfirmationObject(), dto);
        confirmationObject.setToken(convertToMd5(token));
        confirmationObjectRepository.save(confirmationObject);
        return token;
    }

    @Override
    public ConfirmationObject getLast(Class<T> object, String objectId, String type) {
        Optional<ConfirmationObject> objectOptional = confirmationObjectRepository.findOne((root, query, criteriaBuilder) -> {
            query.orderBy(QueryUtils.toOrders(Sort.by(Sort.Direction.DESC, "createdAt"), root, criteriaBuilder));
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("objectType"), object.getSimpleName()),
                    criteriaBuilder.equal(root.get("objectId"), objectId),
                    criteriaBuilder.equal(root.get("type"), type)
            );
        });
        return objectOptional.orElse(null);
    }

    @Override
    public ConfirmationObject validate(Class<T> object, String objectId, String type, String code) {
        Date currentDate = new Date();
        ConfirmationObject confirmationObject = getLast(object, objectId, type);
        messageAssert.isTrue(confirmationObject != null && confirmationObject.getToken().equals(convertToMd5(code)), "confirmation.invalid", "");
        messageAssert.isTrue(confirmationObject.getExpiredAt() != null && confirmationObject.getExpiredAt().after(currentDate), "confirmation.expired");
        return confirmationObject;
    }

    @Override
    public void deleteAllExpired(Class<T> object, String objectId, String type) {
        List<ConfirmationObject> confirmationObjects = confirmationObjectRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("objectType"), object.getSimpleName()),
                criteriaBuilder.equal(root.get("objectId"), objectId),
                criteriaBuilder.equal(root.get("type"), type),
                criteriaBuilder.lessThan(root.get("createdAt"), new Date())
        ));
        confirmationObjectRepository.deleteAll(confirmationObjects);
    }

    private String convertToMd5(String token) {
        return DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
    }
}
