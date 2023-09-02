package com.xuanluan.mc.sdk.generate.repository.confirm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;

@Repository
public interface ConfirmationObjectRepository extends CrudRepository<ConfirmationObject, String> {
    @Query("select co from ConfirmationObject co " +
            "where co.object=:object and co.objectId=:objectId and co.type=:type " +
            "order by co.createdAt desc " +
            "limit 1")
    ConfirmationObject getLast(String object, String objectId, String type);
}
