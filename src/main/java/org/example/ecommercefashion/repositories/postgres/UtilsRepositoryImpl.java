package org.example.ecommercefashion.repositories.postgres;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UtilsRepositoryImpl<T> implements UtilsRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;


}
