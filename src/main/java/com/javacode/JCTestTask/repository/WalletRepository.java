package com.javacode.JCTestTask.repository;

import com.javacode.JCTestTask.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
