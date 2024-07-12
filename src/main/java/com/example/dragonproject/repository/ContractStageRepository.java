package com.example.dragonproject.repository;

import com.example.dragonproject.model.ContractStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractStageRepository extends JpaRepository<ContractStage,Long> {
}
