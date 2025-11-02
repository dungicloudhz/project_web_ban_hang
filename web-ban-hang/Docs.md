# 1. Cài đặt MySql bằng Docker
B1: Download docker desktop theo link sau: https://www.docker.com/get-started/
B2: Run docker image:
```bash
docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=mydb -e MYSQL_USER=myuser -e MYSQL_PASSWORD=mypassword -p 3306:3306 mysql:8.0
```
Lưu ý: sau khi run docker thực hiện update file properties biến môi trường như sau:
Bật command Prompt: chạy lệnh `ipconfig` với window -> lấy giá trị: `IPv4 Address. . . . . . . . . . . : 192.168.0.103`
update file properties trước khi run project
```properties
spring.datasource.url=jdbc:mysql://192.168.0.103:3306/mydb
```
Sử dụng Extentions Database có sẵn của Intellij để connect database và thực hiện chạy các lệnh sql sau (đối với project chạy lần đầu):
Đăng nhập với tài khoản root:
```yaml
spring.datasource.url=jdbc:mysql://192.168.0.103:3306/mydb
spring.datasource.username=root
spring.datasource.password=123456
```
Run project rồi chạy lệnh sau SQL:
```sql
insert into roles(role_name)
values ("USER");
insert into roles(role_name)
values ("ADMIN");

INSERT INTO mydb.users ( account_non_locked, city, commune, district, email, enabled, failed_attempt, first_name, image, last_name, lock_time, password, phone, reset_password_token, username) VALUES ( true, null, null, null, 'admin@gmail.com', true, 0, null, null, null, null, '$2a$10$H/BCCcoHKI2h3oZGt97vKu6cM0aXUpIdRIyDp5ddKmhC5v1LFeRgO', null, null, 'admin');

INSERT INTO mydb.users_roles (user_id, role_id) VALUES (1, 2);
```
Tài khản mật khẩu để đăng nhập admin:
```yaml
username: admin
password: 123456
```

# 2. Sử dụng trang admin để thêm sản phẩm, quản lý user
url: http://localhost:8080/admin/products/

Thực hiện thêm tông tin sản phẩm từng bước 1 (để 1 sản phẩm chi tiết có đủ thông tin):
- B1: Thêm loại sản phẩm (http://localhost:8080/admin/categories/)
- B2: Thêm thương hiệu (http://localhost:8080/admin/brands/)
- B3: Thêm màu sắc (http://localhost:8080/admin/colors/)
- B4: Thêm Ram
- B5: Thêm Bộ nhớ trong
- B6: Thêm sản phẩm
- B7: Thêm thông tin chi tiết sản phẩm

Quản lý người dùng: http://localhost:8080/admin/user
- Thực hiện phân quyền
- Thêm thông tin user ở màn hình register (http://localhost:8080/users/register)

```sql
-- Thêm loại sản phẩm
INSERT INTO mydb.category (description, category_name, slug)
VALUES
    ('Điện thoại', 'Điện thoại', 'dien-thoai'),
    ('Laptop', 'Laptop', 'laptop'),
    ('Máy tính bảng', 'Máy tính bảng', 'may-tinh-bang'),
    ('Phụ kiện', 'Phụ kiện', 'phu-kien'),
    ('Tivi', 'Tivi', 'tivi'),
    ('Tủ lạnh', 'Tủ lạnh', 'tu-lanh'),
    ('Máy giặt', 'Máy giặt', 'may-giat'),
    ('Điều hòa', 'Điều hòa', 'dieu-hoa'),
    ('Máy lọc không khí', 'Máy lọc không khí', 'may-loc-khong-khi'),
    ('Máy hút bụi', 'Máy hút bụi', 'may-hut-bui'),
    ('Đồng hồ', 'Đồng hồ', 'dong-ho'),
    ('Máy ảnh', 'Máy ảnh', 'may-anh'),
    ('Máy quay', 'Máy quay', 'may-quay'),
    ('Thiết bị âm thanh', 'Thiết bị âm thanh', 'thiet-bi-am-thanh'),
    ('Máy in', 'Máy in', 'may-in'),
    ('Máy chiếu', 'Máy chiếu', 'may-chieu'),
    ('Gaming', 'Gaming', 'gaming'),
    ('Thời trang nam', 'Thời trang nam', 'thoi-trang-nam'),
    ('Thời trang nữ', 'Thời trang nữ', 'thoi-trang-nu'),
    ('Giày dép', 'Giày dép', 'giay-dep'),
    ('Túi xách', 'Túi xách', 'tui-xach'),
    ('Đồ gia dụng', 'Đồ gia dụng', 'do-gia-dung'),
    ('Đồ nội thất', 'Đồ nội thất', 'do-noi-that'),
    ('Sách', 'Sách', 'sach'),
    ('Văn phòng phẩm', 'Văn phòng phẩm', 'van-phong-pham'),
    ('Đồ chơi', 'Đồ chơi', 'do-choi'),
    ('Thực phẩm', 'Thực phẩm', 'thuc-pham'),
    ('Mỹ phẩm', 'Mỹ phẩm', 'my-pham'),
    ('Chăm sóc sức khỏe', 'Chăm sóc sức khỏe', 'cham-soc-suc-khoe'),
    ('Thể thao', 'Thể thao', 'the-thao');

-- Thêm thể loại
INSERT INTO mydb.brands (brand_name, description, image)
VALUES
    ('Apple', 'Thương hiệu Apple - iPhone, MacBook, iPad nổi tiếng của Mỹ', null),
    ('Samsung', 'Thương hiệu công nghệ hàng đầu Hàn Quốc', null),
    ('Xiaomi', 'Thương hiệu điện thoại và thiết bị thông minh của Trung Quốc', null),
    ('Oppo', 'Thương hiệu điện thoại phổ biến tại châu Á', null),
    ('Vivo', 'Thương hiệu smartphone trẻ trung, nhiều tính năng', null),
    ('Realme', 'Thương hiệu smartphone giá rẻ, hiệu năng cao', null),
    ('Huawei', 'Tập đoàn công nghệ hàng đầu Trung Quốc', null),
    ('Asus', 'Thương hiệu laptop, điện thoại, linh kiện nổi tiếng', null),
    ('Acer', 'Nhà sản xuất laptop và màn hình máy tính Đài Loan', null),
    ('Dell', 'Thương hiệu laptop và máy tính doanh nghiệp của Mỹ', null),
    ('HP', 'Hewlett-Packard - thương hiệu máy tính, máy in lâu đời', null),
    ('Lenovo', 'Tập đoàn công nghệ đa quốc gia Trung Quốc', null),
    ('MSI', 'Thương hiệu chuyên gaming laptop và phần cứng', null),
    ('Sony', 'Thương hiệu Nhật Bản nổi tiếng với TV, PlayStation', null),
    ('LG', 'Tập đoàn điện tử Hàn Quốc - TV, tủ lạnh, máy giặt', null),
    ('Toshiba', 'Thương hiệu Nhật Bản - điện tử, thiết bị gia dụng', null),
    ('Panasonic', 'Thương hiệu Nhật chuyên đồ điện tử và gia dụng', null),
    ('Canon', 'Thương hiệu máy ảnh, máy in hàng đầu Nhật Bản', null),
    ('Nikon', 'Thương hiệu máy ảnh nổi tiếng thế giới', null),
    ('Casio', 'Thương hiệu đồng hồ, máy tính bỏ túi, nhạc cụ', null),
    ('Rolex', 'Thương hiệu đồng hồ cao cấp Thụy Sĩ', null),
    ('Adidas', 'Thương hiệu thể thao toàn cầu từ Đức', null),
    ('Nike', 'Thương hiệu thể thao hàng đầu thế giới', null),
    ('Puma', 'Thương hiệu thể thao phong cách năng động', null),
    ('Gucci', 'Thương hiệu thời trang cao cấp của Ý', null),
    ('Chanel', 'Thương hiệu mỹ phẩm và thời trang cao cấp Pháp', null),
    ('Dior', 'Thương hiệu xa xỉ của Pháp', null),
    ('Hermès', 'Thương hiệu đồ da và nước hoa cao cấp Pháp', null),
    ('Unilever', 'Tập đoàn hàng tiêu dùng đa quốc gia Anh - Hà Lan', null),
    ('P&G', 'Procter & Gamble - tập đoàn hàng tiêu dùng Mỹ', null);

-- Thêm màu sắc
INSERT INTO mydb.colors (color_code, color_name)
VALUES
    ('Đen', 'Đen'),
    ('Trắng', 'Trắng'),
    ('Đỏ', 'Đỏ'),
    ('Xanh dương', 'Xanh dương'),
    ('Xanh lá', 'Xanh lá'),
    ('Vàng', 'Vàng'),
    ('Cam', 'Cam'),
    ('Tím', 'Tím'),
    ('Hồng', 'Hồng'),
    ('Nâu', 'Nâu'),
    ('Xám', 'Xám'),
    ('Bạc', 'Bạc'),
    ('Vàng đồng', 'Vàng đồng'),
    ('Be', 'Be'),
    ('Xanh navy', 'Xanh navy'),
    ('Xanh ngọc', 'Xanh ngọc'),
    ('Đỏ đô', 'Đỏ đô'),
    ('Xanh rêu', 'Xanh rêu'),
    ('Kem', 'Kem'),
    ('Ghi', 'Ghi'),
    ('Trắng ngà', 'Trắng ngà'),
    ('Đen nhám', 'Đen nhám'),
    ('Xanh pastel', 'Xanh pastel'),
    ('Hồng pastel', 'Hồng pastel'),
    ('Tím pastel', 'Tím pastel'),
    ('Nâu đất', 'Nâu đất'),
    ('Xanh lam', 'Xanh lam'),
    ('Đen bóng', 'Đen bóng'),
    ('Đỏ tươi', 'Đỏ tươi'),
    ('Vàng chanh', 'Vàng chanh'),
    ('Bạc ánh kim', 'Bạc ánh kim');

INSERT INTO mydb.ram (size_ram)
VALUES
    (2),
    (3),
    (4),
    (6),
    (8),
    (12),
    (16),
    (24),
    (32),
    (48),
    (64),
    (128);

INSERT INTO mydb.memory_storages (memory_storage_name)
VALUES
    ('16GB'),
    ('32GB'),
    ('64GB'),
    ('128GB'),
    ('256GB'),
    ('512GB'),
    ('1TB'),
    ('2TB');

INSERT INTO mydb.products (description, image, product_name, status, brand_id, category_id)
VALUES
-- Điện thoại
('Điện thoại iPhone 6 - thiết kế sang trọng, hiệu năng ổn định', null, 'iPhone 6', true, 2, 2),
('Điện thoại iPhone 14 Pro Max - cao cấp, mạnh mẽ', null, 'iPhone 14 Pro Max', true, 2, 2),
('Điện thoại Samsung Galaxy S24 Ultra - flagship đỉnh cao', null, 'Samsung Galaxy S24 Ultra', true, 3, 2),
('Điện thoại Xiaomi Redmi Note 13 Pro', null, 'Xiaomi Redmi Note 13 Pro', true, 4, 2),
('Điện thoại Oppo Reno 10', null, 'Oppo Reno 10', true, 5, 2),
('Điện thoại Vivo V30', null, 'Vivo V30', true, 6, 2),
('Điện thoại Realme 11 Pro', null, 'Realme 11 Pro', true, 7, 2),

-- Laptop
('Laptop MacBook Air M2 - hiệu năng mạnh, pin lâu', null, 'MacBook Air M2', true, 2, 3),
('Laptop Dell XPS 13 - cao cấp, siêu mỏng nhẹ', null, 'Dell XPS 13', true, 11, 3),
('Laptop Asus TUF Gaming F15', null, 'Asus TUF Gaming F15', true, 9, 3),
('Laptop HP Pavilion 15', null, 'HP Pavilion 15', true, 12, 3),
('Laptop Lenovo ThinkPad X1 Carbon', null, 'Lenovo ThinkPad X1 Carbon', true, 13, 3),
('Laptop MSI GF63 Thin', null, 'MSI GF63 Thin', true, 14, 3),
('Laptop Acer Aspire 7', null, 'Acer Aspire 7', true, 10, 3),

-- Máy tính bảng
('Máy tính bảng iPad Air 5', null, 'iPad Air 5', true, 2, 4),
('Máy tính bảng Samsung Galaxy Tab S9', null, 'Samsung Galaxy Tab S9', true, 3, 4),
('Máy tính bảng Xiaomi Pad 6', null, 'Xiaomi Pad 6', true, 4, 4),

-- Phụ kiện
('Tai nghe Bluetooth AirPods Pro 2', null, 'AirPods Pro 2', true, 2, 5),
('Tai nghe Samsung Buds FE', null, 'Samsung Buds FE', true, 3, 5),
('Sạc nhanh 65W Oppo SuperVOOC', null, 'Oppo SuperVOOC 65W', true, 5, 5),
('Ốp lưng iPhone 14 Pro Max chính hãng', null, 'Ốp lưng iPhone 14 Pro Max', true, 2, 5),

-- Tivi
('Smart TV LG 55 inch 4K UHD', null, 'LG Smart TV 55UQ7550', true, 16, 6),
('Smart TV Sony Bravia 65 inch 4K', null, 'Sony Bravia X90L 65 inch', true, 15, 6),
('Smart TV Samsung Crystal UHD 50 inch', null, 'Samsung Crystal UHD 50 inch', true, 3, 6),

-- Máy ảnh
('Máy ảnh Canon EOS R8', null, 'Canon EOS R8', true, 19, 13),
('Máy ảnh Nikon Z6 II', null, 'Nikon Z6 II', true, 20, 13),
('Máy ảnh Sony Alpha A7 IV', null, 'Sony Alpha A7 IV', true, 15, 13),

-- Đồng hồ
('Đồng hồ thông minh Apple Watch Series 9', null, 'Apple Watch Series 9', true, 2, 12),
('Đồng hồ Casio G-Shock GA-2100', null, 'Casio G-Shock GA-2100', true, 21, 12),
('Đồng hồ Rolex Submariner', null, 'Rolex Submariner', true, 22, 12),

-- Thời trang
('Áo thun nam Adidas chính hãng', null, 'Áo thun Adidas Nam', true, 23, 19),
('Giày Nike Air Force 1', null, 'Nike Air Force 1', true, 24, 21),
('Giày Puma RS-X', null, 'Puma RS-X', true, 25, 21),
('Túi xách Gucci Marmont', null, 'Túi Gucci Marmont', true, 26, 22),
('Nước hoa Dior Sauvage', null, 'Dior Sauvage Eau de Toilette', true, 28, 29),
('Son môi Chanel Rouge Allure', null, 'Chanel Rouge Allure', true, 27, 29),
('Kem dưỡng da Unilever Dove', null, 'Dove Body Lotion', true, 30, 30),
('Bột giặt Ariel P&G', null, 'Ariel Hương Downy', true, 31, 23);




INSERT INTO mydb.product_detail
(bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding,
 product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id)
VALUES
-- Điện thoại
(null, 'iPhone 6 - thiết kế sang trọng, hiệu năng ổn định', 0, null, 10, 10, null, true, false, 'iPhone 6', 100, 6000000, 10, 7, 1, 3, 2, 3),
(null, 'iPhone 14 Pro Max - cao cấp, mạnh mẽ', 10, null, 10, 10, null, true, true, 'iPhone 14 Pro Max', 200, 29900000, 10, 7, 1, 6, 3, 8),
(null, 'Samsung Galaxy S24 Ultra - flagship đỉnh cao', 5, null, 10, 10, null, true, true, 'Samsung Galaxy S24 Ultra', 150, 25900000, 10, 7, 1, 6, 4, 8),
(null, 'Xiaomi Redmi Note 13 Pro', 0, null, 10, 10, null, true, false, 'Xiaomi Redmi Note 13 Pro', 100, 8990000, 10, 7, 3, 5, 5, 6),
(null, 'Oppo Reno 10', 0, null, 10, 10, null, false, false, 'Oppo Reno 10', 120, 10990000, 10, 7, 2, 5, 6, 6),
(null, 'Vivo V30', 0, null, 10, 10, null, false, false, 'Vivo V30', 100, 9990000, 10, 7, 3, 5, 7, 6),
(null, 'Realme 11 Pro', 0, null, 10, 10, null, false, false, 'Realme 11 Pro', 80, 8990000, 10, 7, 2, 4, 8, 6),

-- Laptop
(null, 'MacBook Air M2 - hiệu năng mạnh, pin lâu', 0, null, 10, 10, null, true, true, 'MacBook Air M2', 100, 28900000, 15, 30, 1, 6, 9, 8),
(null, 'Dell XPS 13 - cao cấp, siêu mỏng nhẹ', 5, null, 10, 10, null, true, true, 'Dell XPS 13', 90, 25900000, 15, 30, 2, 6, 10, 8),
(null, 'Asus TUF Gaming F15', 0, null, 10, 10, null, false, true, 'Asus TUF Gaming F15', 80, 20900000, 20, 32, 3, 6, 11, 8),
(null, 'HP Pavilion 15', 0, null, 10, 10, null, false, false, 'HP Pavilion 15', 100, 17900000, 15, 32, 2, 5, 12, 6),
(null, 'Lenovo ThinkPad X1 Carbon', 10, null, 10, 10, null, true, true, 'Lenovo ThinkPad X1 Carbon', 50, 33900000, 15, 32, 1, 6, 13, 8),
(null, 'MSI GF63 Thin', 0, null, 10, 10, null, false, true, 'MSI GF63 Thin', 80, 20900000, 18, 32, 2, 6, 14, 8),
(null, 'Acer Aspire 7', 0, null, 10, 10, null, false, false, 'Acer Aspire 7', 120, 16900000, 18, 32, 3, 5, 15, 6),

-- Máy tính bảng
(null, 'iPad Air 5 - mỏng nhẹ, mạnh mẽ', 0, null, 10, 10, null, true, false, 'iPad Air 5', 100, 16900000, 10, 20, 1, 5, 16, 6),
(null, 'Samsung Galaxy Tab S9', 0, null, 10, 10, null, true, true, 'Galaxy Tab S9', 80, 18900000, 10, 20, 1, 5, 17, 6),
(null, 'Xiaomi Pad 6', 0, null, 10, 10, null, false, false, 'Xiaomi Pad 6', 120, 9990000, 10, 20, 3, 4, 18, 6),

-- Phụ kiện
(null, 'Tai nghe AirPods Pro 2 - chống ồn chủ động', 0, null, 10, 10, null, true, true, 'AirPods Pro 2', 200, 5490000, 5, 5, 1, null, 19, null),
(null, 'Tai nghe Samsung Buds FE', 0, null, 10, 10, null, false, true, 'Samsung Buds FE', 180, 2990000, 5, 5, 2, null, 20, null),
(null, 'Sạc nhanh Oppo SuperVOOC 65W', 0, null, 10, 10, null, false, false, 'Oppo SuperVOOC 65W', 150, 990000, 5, 5, 3, null, 21, null),
(null, 'Ốp lưng iPhone 14 Pro Max chính hãng', 0, null, 10, 10, null, false, false, 'Ốp lưng iPhone 14 Pro Max', 300, 490000, 5, 5, 4, null, 22, null),

-- TV
(null, 'Smart TV LG 55 inch 4K UHD', 5, null, 60, 80, null, true, true, 'LG Smart TV 55UQ7550', 60, 10900000, 8000, 120, 2, null, 23, null),
(null, 'Smart TV Sony Bravia X90L 65 inch', 10, null, 60, 80, null, true, true, 'Sony Bravia X90L', 40, 18900000, 8500, 130, 1, null, 24, null),
(null, 'Smart TV Samsung Crystal UHD 50 inch', 0, null, 60, 80, null, false, true, 'Samsung Crystal UHD 50', 70, 9990000, 7000, 115, 3, null, 25, null),

-- Máy ảnh
(null, 'Canon EOS R8', 0, null, 10, 10, null, true, false, 'Canon EOS R8', 40, 35900000, 20, 10, 1, null, 26, null),
(null, 'Nikon Z6 II', 0, null, 10, 10, null, true, false, 'Nikon Z6 II', 30, 39900000, 20, 10, 2, null, 27, null),
(null, 'Sony Alpha A7 IV', 0, null, 10, 10, null, true, true, 'Sony Alpha A7 IV', 35, 41900000, 20, 10, 1, null, 28, null),

-- Đồng hồ
(null, 'Apple Watch Series 9', 0, null, 5, 5, null, true, true, 'Apple Watch Series 9', 100, 10900000, 1, 5, 1, null, 29, null),
(null, 'Casio G-Shock GA-2100', 0, null, 5, 5, null, false, true, 'Casio G-Shock GA-2100', 150, 3990000, 1, 5, 2, null, 30, null),
(null, 'Rolex Submariner', 0, null, 5, 5, null, true, true, 'Rolex Submariner', 10, 250000000, 1, 5, 1, null, 31, null),

-- Thời trang & Mỹ phẩm
(null, 'Áo thun Adidas Nam', 0, null, 2, 2, null, false, false, 'Áo thun Adidas Nam', 200, 590000, 1, 2, 3, null, 32, null),
(null, 'Giày Nike Air Force 1', 0, null, 2, 2, null, true, true, 'Nike Air Force 1', 150, 2500000, 1, 2, 1, null, 33, null),
(null, 'Giày Puma RS-X', 0, null, 2, 2, null, false, false, 'Puma RS-X', 100, 2300000, 1, 2, 2, null, 34, null),
(null, 'Túi xách Gucci Marmont', 0, null, 10, 10, null, true, true, 'Gucci Marmont', 50, 49900000, 2, 10, 1, null, 35, null),
(null, 'Nước hoa Dior Sauvage Eau de Toilette', 0, null, 10, 10, null, true, true, 'Dior Sauvage', 80, 2990000, 1, 10, 3, null, 36, null),
(null, 'Son môi Chanel Rouge Allure', 0, null, 10, 10, null, false, false, 'Chanel Rouge Allure', 150, 1290000, 1, 10, 2, null, 37, null),
(null, 'Kem dưỡng da Dove', 0, null, 10, 10, null, false, false, 'Dove Body Lotion', 200, 199000, 1, 10, 3, null, 38, null),
(null, 'Bột giặt Ariel Hương Downy', 0, null, 10, 10, null, false, false, 'Ariel P&G', 300, 149000, 1, 10, 2, null, 39, null);

```