package com.mtran.mvc.multithreading.Synchronized;


import com.mtran.mvc.multithreading.Issue.TransactionSimulation;

public class MultithreadedSimulation1 {
    public static void main(String[] args) throws InterruptedException {
        int numThread= 10;
        Thread[] threads = new Thread[numThread];

        TransactionSimulation1 simulation = new TransactionSimulation1();
        //make thread prepare to start
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numThread; i++) {
            threads[i]=new Thread(simulation::performTransaction);
            threads[i].start();
        }
        //wait all thread finished to execute
        for(Thread t:threads){
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Final Balance: " + simulation.balance);
        System.out.println("Final Balance2: " + (TransactionSimulation1.INIT_BALANCE + simulation.credits - simulation.debits));
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

      Fix : SYNCHRONIZED
      synchron là việc sử dụng "khóa " tại cái hàm xử lý, tức là khi mà thread này truy cập thì thread khác phải đợi
      -> thời gian lâu
      ** không thể hủy lock
      test : ~1100ms
     */

}
