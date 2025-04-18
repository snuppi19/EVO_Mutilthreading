# EVO_Mutilthreading
---
## Mô tả 
Đây là project luyện tập bài tập cơ bản về việc tạo tình huống nhiều transaction xảy ra cùng 1 lúc và các cách xử lý
Trong bài này mô phỏng cộng trừ tiền đơn giản 
* kết quả :
 kết quả bị âm || > 500 lúc đầu nếu tạo thread đủ lớn và ở bên xử lí các deposit cho chậm nhịp 1mls ->2mls
->race condition
   nguyên nhân: hàm viết để các luồng thread có sleep 10s và có biến latch giữ để gần như các luồng chạy cùng 1 lúc như ở ngoài thực tế
   -> do có nhiều giao dịch cùng truy cập vào 1 biến trong cùng 1 thời gian nhất định nên gây ra bất đồng bộ
   ví dụ :
   giao dịch A cộng tiền vào acc và bị chờ 1mls . Trong lúc chờ thì có giao dịch B trừ tiền truy cập vào
   -> số dư acc lúc này không chính xác -> nhiều giao dịch tương tự dẫn đến làm sai lệch số dư.

   * synchronized: ~11000ms -> khóa method -> thread chờ nhau-> lâu
   * reentantLock: ~500ms-> lock và unlock ngay tại method -> chống race conditions -> code nhiều phức tạp ở mỗi method khi phải lock và unlock thủ công.
   * semaphore : 41ms -> config được số lượng truy cập 
  
## Yêu cầu 
* Java [Phiên bản 17]
* Sử dụng Maven
* IDEA: Intellij
## Tải & Cài đặt 
1. Clone repository
```
https://github.com/snuppi19/EVO_Mutilthreading.git
```
3. Tải dependencies
 * backend
```
mvn install
```
4. Chạy backend
```
mvn spring-boot:run
```
