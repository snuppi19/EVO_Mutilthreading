package com.mtran.mvc.multithreading.Semaphore;


import com.mtran.mvc.multithreading.ReentrantLock.TransactionSimulation2;

public class MultithreadedSimulation3 {
    public static void main(String[] args) throws InterruptedException {
        int numThread = 10;
        Thread[] threads = new Thread[numThread];

        TransactionSimulation3 simulation = new TransactionSimulation3();
        //make thread prepare to start
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numThread; i++) {
            threads[i] = new Thread(simulation::performTransaction);
            threads[i].start();
        }
        //wait all thread finished to execute
        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Final Balance: " + simulation.balance);
        System.out.println("Final Balance2: " + (TransactionSimulation3.INIT_BALANCE + simulation.credits - simulation.debits));
        System.out.println("Total Credits: " + simulation.credits);
        System.out.println("Total Debits: " + simulation.debits);
        long elapsedTime = endTime - startTime;
        System.out.println("Execution Time: " + elapsedTime + " milliseconds");
    }
    /*
       Số dư balance và balance 2 luôn luôn không bằng nhau do bị race condition ( hiện tượng tranh chấp tài nguyên khi mà
      nhiều nguồn cùng truy cập vào 1 tài nguyên cùng 1 lúc)
      NGUYÊN NHÂN :
      nguyên nhân gây ra condition là do mỗi 1 luồng 1 triệu giao dịch cộng trừ, khi có 10 luồng chạy
      cùng 1 lúc là có 10 triệu giao dịch chạy cùng 1 lúc , dẫn tới việc xảy ra khả năng 2 hoặc nhiều giao dịch sẽ cùng truy cập
      vào creadits hoặc debits dẫn tới miss giao dịch

      Fix : Semaphore
      Hạn chế số lượng truy cập song song vào tài nguyên (count-based access)
      1 thread acquire() nhiều lần sẽ cần release() tương ứng
      ** cấu hình được số lượng truy cập song song cụ thể
      test : ~31ms
     */

}
