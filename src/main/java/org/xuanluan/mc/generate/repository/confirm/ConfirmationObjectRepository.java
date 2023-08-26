package org.xuanluan.mc.generate.repository.confirm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.xuanluan.mc.generate.domain.entity.ConfirmationObject;

@Repository
public interface ConfirmationObjectRepository extends CrudRepository<ConfirmationObject, String> {
    @Query("select co from ConfirmationObject co where co.object= :object and :objectId " +
            "order by co.createdAt desc " +
            "limit 1")
    ConfirmationObject getLast(String object, String objectId);
}
