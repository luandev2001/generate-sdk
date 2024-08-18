package com.xuanluan.mc.sdk.generate.repository.confirm;

import com.xuanluan.mc.sdk.repository.JpaMultipleRepository;
import org.springframework.stereotype.Repository;
import com.xuanluan.mc.sdk.generate.model.entity.ConfirmationObject;

@Repository
public interface ConfirmationObjectRepository extends JpaMultipleRepository<ConfirmationObject, String> {
}
