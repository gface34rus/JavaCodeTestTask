package com.javacode.JCTestTask.service;

import com.javacode.JCTestTask.dto.WalletRequest;
import com.javacode.JCTestTask.model.OperationType;
import com.javacode.JCTestTask.model.Wallet;
import com.javacode.JCTestTask.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Transactional
    public void performOperation(WalletRequest request) {
        synchronized (this) {
            UUID walletId = request.getWalletId();
            OperationType operationType = request.getOperationType();
            BigDecimal amount = request.getAmount();

            // Проверка на отрицательную сумму
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            Wallet wallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            if (operationType == OperationType.DEPOSIT) {
                wallet.setBalance(wallet.getBalance().add(amount));
            } else if (operationType == OperationType.WITHDRAW) {
                if (wallet.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("Not enough balance");
                }
                wallet.setBalance(wallet.getBalance().subtract(amount));
            }
            walletRepository.save(wallet);
        }
    }

    public BigDecimal getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }
}
