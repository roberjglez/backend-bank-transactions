package com.rjglez.backend.bank.transactions.infrastructure.adapter.repository;

import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

}
