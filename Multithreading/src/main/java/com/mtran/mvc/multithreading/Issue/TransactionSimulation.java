package com.mtran.mvc.multithreading.Issue;

import com.mtran.mvc.multithreading.Account;
import com.mtran.mvc.multithreading.TransactionTask;

import java.util.concurrent.CountDownLatch;

public class TransactionSimulation {
    public static void main(String[] args) {
        try {
            Account account = new Account(500);// tạo account 500 đ
            CountDownLatch latch = new CountDownLatch(1); // tạo biến latch để đánh dấu luồng cần chờ
            Thread[] threads = new Thread[5000];// tạo 5k thread vì 10 là chưa đủ
            for (int i = 0; i < 5000; i++) {
                TransactionTask transactionTask = new TransactionTask(account, 1000, i % 2 == 0);
                threads[i] = new Thread(() -> {
                    try {
                        latch.await();//giữ cho các luồng chờ đến khi gặp cooldown lần đầu tiên
                        transactionTask.performTransaction();// giao nhiệm vụ cho các luồng
                        System.out.println("Luồng " + Thread.currentThread().getName() +
                                ": Số dư = " + account.getAmount());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            for (Thread thread : threads) {
                thread.start(); // đưa các luồng vào " chuẩn bị"
            }
            Thread.sleep(10000);// cho đợi thêm 10s nữa để các luồng tăng khả năng chạy song song
            long startTime = System.nanoTime();
            latch.countDown();// các luồng chạy 1 lúc

            for (Thread thread : threads) {
                thread.join();// chờ các luồng chạy xong thì mới tính kết quả của luồng chính
            }
            long endTime = System.nanoTime();

            long durationInMillis = (endTime - startTime) / 1_000_000;
            System.out.println("Số dư cuối cùng: " + account.getAmount());
            System.out.println("Thời gian thực hiện: " + durationInMillis + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
kết quả bị âm || > 500 lúc đầu nếu tạo thread đủ lớn và ở bên xử lí các deposit cho chậm nhịp 1mls ->2mls
->race condition
   nguyên nhân: hàm viết để các luồng thread có sleep 10s và có biến latch giữ để gần như các luồng chạy cùng 1 lúc như ở ngoài thực tế
   -> do có nhiều giao dịch cùng truy cập vào 1 biến trong cùng 1 thời gian nhất định nên gây ra bất đồng bộ
   ví dụ :
   giao dịch A cộng tiền vào acc và bị chờ 1mls . Trong lúc chờ thì có giao dịch B trừ tiền truy cập vào
   -> số dư acc lúc này không chính xác -> nhiều giao dịch tương tự dẫn đến làm sai lệch số dư.

   synchronized: ~11000ms -> khóa method -> thread chờ nhau-> lâu
   atomic: ~ 600ms ->có atomic operation-> k cho các thread chen vào giữa + nhưng không dùng đc logic phức tạp( get và set khong được đặt cạnh nhau)
   @Transactional(isolation = Isolation.SERIALIZABLE)-> thao tác với database -> dựa vào số lượng bảng và data + độ phức tạpc của hàm
   reentantLock: ~500ms-> lock và unlock ngay tại method -> chống race conditions -> code nhiều phức tạp ở mỗi method khi phải lock và unlock thủ công.
   Queue: dùng cho các tác vụ xử lí nhiều giá trị liên quan đi kèm (rabbitMQ)
 */