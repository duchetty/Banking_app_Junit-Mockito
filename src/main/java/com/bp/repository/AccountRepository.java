package com.bp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bp.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
