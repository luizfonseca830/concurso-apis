package com.br.concurso.api.repository.launch;

import com.br.concurso.api.model.Launch;
import com.br.concurso.api.resource.filter.LaunchFilter;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LaunchRepositoryImpl implements LaunchRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Launch> filter(LaunchFilter launchFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Launch> criteriaQuery = builder.createQuery(Launch.class);
        Root<Launch> root = criteriaQuery.from(Launch.class);


        Predicate[] predicate = createRestrict(launchFilter, builder, root);
        criteriaQuery.where(predicate);


        TypedQuery<Launch> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    //Cria as restrições
    private Predicate[] createRestrict(LaunchFilter launchFilter, CriteriaBuilder builder, Root<Launch> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(launchFilter.getDescription())) {
            predicates.add(builder.like(
                    builder.lower(root.get("description")), "%" + launchFilter.getDescription().toLowerCase() + "%"));
        }
        if (launchFilter.getDueDateOf() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dueDateOf"), launchFilter.getDueDateOf()));

        }
        if (launchFilter.getDueDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dueDateTo"), launchFilter.getDueDateTo()));
        }
        if (launchFilter.getPaymentDate() != null) {
            predicates.add(builder.equal(root.get("paymentDate"), launchFilter.getPaymentDate()));
        }
        if (launchFilter.getAmount() != null) {
            predicates.add(builder.equal(root.get("amount"), launchFilter.getAmount()));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
