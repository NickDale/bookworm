package com.library.model.repositories;

import com.library.model.repositories.predicate.CriteriaEnum;
import com.library.model.repositories.predicate.SearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

@SuppressWarnings("unchecked")
public class AbstractJpaRepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements AbstractJpaRepository<T> {

    private static final String REGEX = "(\\w+?)(:|<|>|!|~)(\\w+?),";
    private final EntityManager em;

    public AbstractJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public <S> List<S> findAll(Class<S> returnType) {
        return this.getReadQuery(null, returnType, Sort.unsorted()).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public <S> List<S> findAll(Class<S> returnType, Sort sort) {
        return this.getReadQuery(null, returnType, sort).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public <S> List<S> findAll(Class<S> returnType, String search, int page, int size, String[] sorts) {
        var cb = em.getCriteriaBuilder();
        var predicates = new ArrayList<Predicate>();
        var cq = cb.createQuery(returnType);
        var root = cq.from(returnType);

        if (isNotBlank(search)) {
            var matcher = Pattern.compile(REGEX).matcher(search + ",");
            while (matcher.find()) {
                var searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                var pred = createPredicate(root, searchCriteria, cb);
                if (nonNull(pred)) {
                    predicates.add(pred);
                }
            }
        }
        Sort sort = Sort.by(getOrders(sorts));
        if (sort.isSorted()) {
            cq.orderBy(toOrders(sort, root, cb));
        }
        cq.where(predicates.toArray(Predicate[]::new));
        var pageRequest = PageRequest.of(page, size, sort);
        var query = em.createQuery(cq);
        if (pageRequest.isPaged()) {
            query.setFirstResult((int) pageRequest.getOffset());
            query.setMaxResults(pageRequest.getPageSize());
        }
        return query.getResultList();
    }

    private <S> Predicate createPredicate(final Root<S> root, final SearchCriteria criteria, final CriteriaBuilder builder) {
        switch (CriteriaEnum.findByValue(criteria.getOperation())) {
            case EQUAL:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATE:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

    private List<Sort.Order> getOrders(String[] sorts) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sorts[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sorts) {
                orders.add(createSortOrder(sortOrder.split(",")));
            }
        } else {
            // sort=[field, direction]
            orders.add(createSortOrder(sorts));
        }
        return orders;
    }

    private Sort.Order createSortOrder(@NonNull String[] sorts) {
        return new Sort.Order(Sort.Direction.fromString(sorts[1]), sorts[0]);
    }

    @Override
    public EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    @Transactional
    public <Entity> Entity saveEntity(Entity entity) {
        if (isNull(entity)) {
            throw new RuntimeException("Entity cannot be null!");
        }
        final JpaEntityInformation<Entity, ?> information =
                (JpaEntityInformation<Entity, ?>) JpaEntityInformationSupport.getEntityInformation(entity.getClass(), em);
        if (information.isNew(entity)) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public <S, R extends Collection<S>> List<S> saveAllEntity(R entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(saveEntity(entity));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public <S> S findById(Class<S> returnType, @NonNull Object id) {
        Map<String, Object> hints = new HashMap<>();
        this.getQueryHints().withFetchGraphs(em).forEach(hints::put);
        S s = em.find(returnType, id, hints);
        if (isNull(s)) {
            throw new EntityNotFoundException(String.format("We don't find %s class with %s id", returnType.getSimpleName(), id));
        }
        return s;
    }

    @Override
    @Transactional
    public <S> void deleteEntity(S entity) {
        final JpaEntityInformation<S, ?> entityInformation =
                (JpaEntityInformation<S, ?>) JpaEntityInformationSupport.getEntityInformation(entity.getClass(), em);

        if (entityInformation.isNew(entity)) {
            return;
        }
        S existing = (S) em.find(ProxyUtils.getUserClass(entity), entityInformation.getId(entity));
        if (existing == null) {
            return;
        }
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected <S> TypedQuery<S> getReadQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);
        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return em.createQuery(query);
    }

    private <S, U> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                        CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");
        Root<U> root = query.from(domainClass);
        if (isNull(spec)) {
            return root;
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        return root;
    }

}
