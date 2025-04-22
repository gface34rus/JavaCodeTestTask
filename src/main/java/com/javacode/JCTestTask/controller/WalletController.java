package com.javacode.JCTestTask.controller;


import com.javacode.JCTestTask.dto.WalletRequest;
import com.javacode.JCTestTask.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping
    public ResponseEntity<Void> performOperation(@RequestBody WalletRequest request) {
        walletService.performOperation(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}
