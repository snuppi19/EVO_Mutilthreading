package com.mtran.mvc.multithreading;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

@Component
@NoArgsConstructor
public class Account {
    private double amount;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void deposit(double value) {
        lock.lock();
        try {
            amount += value;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double value) {
        lock.lock();
        try {
            amount -= value;
        } finally {
            lock.unlock();
        }
    }
}
