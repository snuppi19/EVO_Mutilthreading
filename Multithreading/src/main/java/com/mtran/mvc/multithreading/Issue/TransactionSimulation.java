package com.mtran.mvc.multithreading.Issue;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TransactionSimulation {
    static final int INIT_BALANCE = 50;
    static final int NUM_TRANS = 1000000;
    int balance = INIT_BALANCE;
    int credits = 0;
    int debits = 0;

    public void performTransaction() {
        Random random = new Random();
        for (int i = 0; i < NUM_TRANS; i++) {
            int v = random.nextInt(NUM_TRANS);
            if (random.nextInt(2) == 0) {
                // Credit transaction
                balance += v;
                credits += v;
            } else {
                // Debit transaction
                balance -= v;
                debits += v;
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TransactionSimulation simulation = new TransactionSimulation();
        simulation.performTransaction();
        long endTime = System.currentTimeMillis();
        System.out.println("Final Balance: " + simulation.balance);
        System.out.println("Final Balance2: " + (TransactionSimulation.INIT_BALANCE + simulation.credits - simulation.debits));
        System.out.println("Total Credits: " + simulation.credits);
        System.out.println("Total Debits: " + simulation.debits);
        long elapsedTime = endTime - startTime;
        System.out.println("Execution Time: " + elapsedTime + " milliseconds");
    }
}
