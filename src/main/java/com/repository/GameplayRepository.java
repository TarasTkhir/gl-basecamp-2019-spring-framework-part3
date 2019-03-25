package com.repository;

import com.entity.GameplayEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface GameplayRepository extends CrudRepository<GameplayEntity,Integer> {

}
