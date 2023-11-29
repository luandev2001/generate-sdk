package com.xuanluan.mc.sdk.generate.repository.confirm;

import com.xuanluan.mc.sdk.generate.domain.entity.ConfirmationObject;
import com.xuanluan.mc.sdk.repository.BaseRepository;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

public class ConfirmationObjectRepositoryCustomImpl extends BaseRepository<ConfirmationObject> implements ConfirmationObjectRepositoryCustom {
    protected ConfirmationObjectRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, ConfirmationObject.class);
    }

    @Override
    public ConfirmationObject getLast(String object, String objectId, String type) {
        refresh();
        List<Predicate> predicates = appendFilter("object", object, new LinkedList<>());
        appendFilter("objectId", objectId, predicates);
        appendFilter("type", type, predicates);
        return getSingleResult(predicates, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
