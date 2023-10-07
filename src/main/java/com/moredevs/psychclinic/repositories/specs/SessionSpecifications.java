package com.moredevs.psychclinic.repositories.specs;

import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Session;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SessionSpecifications {
    public static Specification<Session> withClientName(Integer psychologistId) {
        return (Root<Session> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Join<Session, Client> sessionClientJoin = root.join("client");
            query.multiselect(sessionClientJoin.get("name"), root.get("dateAndTime"), root.get("sessionNotes"), root.get("sessionStatus"));
            return criteriaBuilder.equal(root.get("psychologist").get("id"), psychologistId);
        };
    }
}
