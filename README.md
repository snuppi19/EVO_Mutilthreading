# EVO_Mutilthreading
---
## Mô tả 
Đây là project luyện tập bài tập cơ bản về việc tạo tình huống nhiều transaction xảy ra cùng 1 lúc và các cách xử lý
Trong bài này mô phỏng cộng trừ tiền đơn giản 
hint:
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
