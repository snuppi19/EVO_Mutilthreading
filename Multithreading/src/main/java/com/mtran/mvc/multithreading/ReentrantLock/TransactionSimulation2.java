package com.mtran.mvc.multithreading.ReentrantLock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionSimulation2 {
    static final int INIT_BALANCE = 50;
    static final int NUM_TRANS = 1000000;
    int balance = INIT_BALANCE;
    int credits = 0;
    int debits = 0;

    private final ReentrantLock lock = new ReentrantLock();

    public void performTransaction() {
        Random random = new Random();
        for (int i = 0; i < NUM_TRANS; i++) {
            int v = random.nextInt(NUM_TRANS);
            boolean isCredit = random.nextInt(2) == 0;
            lock.lock();
            try {
                if (isCredit) {
                    // Credit transaction
                    balance += v;
                    credits += v;
                } else {
                    // Debit transaction
                    balance -= v;
                    debits += v;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TransactionSimulation2 simulation = new TransactionSimulation2();
        simulation.performTransaction();
        long endTime = System.currentTimeMillis();
        System.out.println("Final Balance: " + simulation.balance);
        System.out.println("Final Balance2: " + (TransactionSimulation2.INIT_BALANCE + simulation.credits - simulation.debits));
        System.out.println("Total Credits: " + simulation.credits);
        System.out.println("Total Debits: " + simulation.debits);
        long elapsedTime = endTime - startTime;
        System.out.println("Execution Time: " + elapsedTime + " milliseconds");
    }
}
