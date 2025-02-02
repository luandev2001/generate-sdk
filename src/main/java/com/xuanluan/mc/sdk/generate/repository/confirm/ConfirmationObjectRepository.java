package com.xuanluan.mc.sdk.generate.repository.confirm;

import com.xuanluan.mc.sdk.repository.JpaMultipleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;

@Repository
@ConditionalOnProperty(name = "sdk.confirmation_object.enabled", havingValue = "true")
public interface ConfirmationObjectRepository extends JpaMultipleRepository<ConfirmationObject, String> {
}
