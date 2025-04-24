package com.javacode.JCTestTask.controller;


import com.javacode.JCTestTask.dto.WalletRequest;
import com.javacode.JCTestTask.model.OperationType;
import com.javacode.JCTestTask.model.Wallet;
import com.javacode.JCTestTask.repository.WalletRepository;
import com.javacode.JCTestTask.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    private UUID existingWalletId;
    private UUID nonExistentWalletId;


    @BeforeEach
    public void setUp() {
        existingWalletId = UUID.randomUUID(); // Замените на реальный UUID существующего кошелька
        nonExistentWalletId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Wallet wallet = new Wallet();
        wallet.setId(existingWalletId);
        wallet.setBalance(BigDecimal.valueOf(100.00));
        walletRepository.save(wallet);
    }

    @Test
    public void testPerformDepositOperation() throws Exception {
        WalletRequest request = new WalletRequest();
        request.setWalletId(existingWalletId);
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + existingWalletId + "\",\"operationType\":\"DEPOSIT\",\"amount\":100.0}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPerformWithdrawalOperation() throws Exception {
        WalletRequest request = new WalletRequest();
        request.setWalletId(existingWalletId);
        request.setOperationType(OperationType.WITHDRAW);
        request.setAmount(BigDecimal.valueOf(50.00));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + existingWalletId + "\",\"operationType\":\"WITHDRAW\",\"amount\":50.0}"))
                .andExpect(status().isOk());
    }


    @Test
    public void testPerformOperationWithNonExistentWallet() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + nonExistentWalletId + "\",\"operationType\":\"DEPOSIT\",\"amount\":100.0}"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetBalance() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/{walletId}", existingWalletId))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetBalanceWithNonExistentWallet() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/{walletId}", nonExistentWalletId))
                .andExpect(status().isNotFound());
    }



    @Test
    public void testPerformOperationWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + existingWalletId + "\",\"operationType\":\"DEPOSIT\",\"amount\":-50.0}"))
                .andExpect(status().isBadRequest());
    }
}