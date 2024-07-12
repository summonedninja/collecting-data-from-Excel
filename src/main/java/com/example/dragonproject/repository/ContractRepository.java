package com.example.dragonproject.repository;

import com.example.dragonproject.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByContractCode(String contractCode);
    Optional<Contract> findByContractCode(String contractCode);

}
