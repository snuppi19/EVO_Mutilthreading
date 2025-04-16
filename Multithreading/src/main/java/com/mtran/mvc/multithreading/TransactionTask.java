package com.mtran.mvc.multithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TransactionTask {
    private Account account;
    private double amountDeposit;
    private boolean isDeposit;

    public TransactionTask(Account account, double amountDeposit, boolean isDeposit) {
        this.account = account;
        this.amountDeposit = amountDeposit;
        this.isDeposit = isDeposit;
    }

    public void performTransaction() throws InterruptedException {

            if (isDeposit) {
                Thread.sleep(1);//wait 1mls
                account.deposit(amountDeposit);
            } else {
                Thread.sleep(1);//wait 1mls
                account.withdraw(amountDeposit);
            }


    }
}
