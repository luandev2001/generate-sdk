package com.xuanluan.mc.sdk.generate.service.imp;

import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.CreateConfirmationObject;
import com.xuanluan.mc.sdk.generate.model.request.confirmation_object.ValidateConfirmationObject;
import com.xuanluan.mc.sdk.generate.repository.confirm.ConfirmationObjectRepository;
import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import com.xuanluan.mc.sdk.generate.model.dto.ConfirmationObjectDTO;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.generate.service.IConfirmationObjectService;
import com.xuanluan.mc.sdk.generate.service.converter.ConfirmationConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(name = "sdk.confirmation_object.enabled", havingValue = "true")
public class ConfirmationObjectServiceImp implements IConfirmationObjectService {
    private final ConfirmationObjectRepository confirmationObjectRepository;
    private final MessageAssert messageAssert;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> String create(CreateConfirmationObject<T> dto) {
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
    public <T> ConfirmationObject getLast(ConfirmationObjectDTO<T> request) {
        Optional<ConfirmationObject> objectOptional = confirmationObjectRepository.findOne((root, query, criteriaBuilder) -> {
            query.orderBy(QueryUtils.toOrders(Sort.by(Sort.Direction.DESC, "createdAt"), root, criteriaBuilder));
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("objectType"), request.getObject().getSimpleName()),
                    criteriaBuilder.equal(root.get("objectId"), request.getObjectId()),
                    criteriaBuilder.equal(root.get("type"), request.getType())
            );
        });
        return objectOptional.orElse(null);
    }

    @Override
    public <T> ConfirmationObject validate(ValidateConfirmationObject<T> request) {
        Date currentDate = new Date();
        ConfirmationObject confirmationObject = getLast(request);
        messageAssert.isTrue(confirmationObject != null && confirmationObject.getToken().equals(convertToMd5(request.getCode())), "confirmation.invalid", "");
        messageAssert.isTrue(confirmationObject.getExpiredAt() != null && confirmationObject.getExpiredAt().after(currentDate), "confirmation.expired");
        return confirmationObject;
    }

    @Override
    public <T> void deleteAllExpired(ConfirmationObjectDTO<T> request) {
        List<ConfirmationObject> confirmationObjects = confirmationObjectRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("objectType"), request.getObject().getSimpleName()),
                criteriaBuilder.equal(root.get("objectId"), request.getObjectId()),
                criteriaBuilder.equal(root.get("type"), request.getType()),
                criteriaBuilder.lessThan(root.get("expiredAt"), new Date())
        ));
        confirmationObjectRepository.deleteAll(confirmationObjects);
    }

    private String convertToMd5(String token) {
        return DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
    }
}
