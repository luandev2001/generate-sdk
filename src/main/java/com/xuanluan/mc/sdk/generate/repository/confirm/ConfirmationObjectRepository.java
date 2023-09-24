package com.xuanluan.mc.sdk.generate.repository.confirm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

@Repository
public interface ConfirmationObjectRepository extends CrudRepository<ConfirmationObject, String>, ConfirmationObjectRepositoryCustom {
}
