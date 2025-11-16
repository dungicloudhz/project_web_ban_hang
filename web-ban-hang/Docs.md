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
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (2, 'Điện thoại', 'Điện thoại', 'dien-thoai');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (3, 'Laptop', 'Laptop', 'laptop');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (4, 'Máy tính bảng', 'Máy tính bảng', 'may-tinh-bang');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (5, 'Phụ kiện', 'Phụ kiện', 'phu-kien');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (6, 'Tivi', 'Tivi', 'tivi');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (7, 'Tủ lạnh', 'Tủ lạnh', 'tu-lanh');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (8, 'Máy giặt', 'Máy giặt', 'may-giat');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (9, 'Điều hòa', 'Điều hòa', 'dieu-hoa');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (10, 'Máy lọc không khí', 'Máy lọc không khí', 'may-loc-khong-khi');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (11, 'Máy hút bụi', 'Máy hút bụi', 'may-hut-bui');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (12, 'Đồng hồ', 'Đồng hồ', 'dong-ho');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (13, 'Máy ảnh', 'Máy ảnh', 'may-anh');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (14, 'Máy quay', 'Máy quay', 'may-quay');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (15, 'Thiết bị âm thanh', 'Thiết bị âm thanh', 'thiet-bi-am-thanh');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (16, 'Máy in', 'Máy in', 'may-in');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (17, 'Máy chiếu', 'Máy chiếu', 'may-chieu');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (18, 'Gaming', 'Gaming', 'gaming');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (19, 'Thời trang nam', 'Thời trang nam', 'thoi-trang-nam');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (20, 'Thời trang nữ', 'Thời trang nữ', 'thoi-trang-nu');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (21, 'Giày dép', 'Giày dép', 'giay-dep');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (22, 'Túi xách', 'Túi xách', 'tui-xach');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (23, 'Đồ gia dụng', 'Đồ gia dụng', 'do-gia-dung');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (24, 'Đồ nội thất', 'Đồ nội thất', 'do-noi-that');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (25, 'Sách', 'Sách', 'sach');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (26, 'Văn phòng phẩm', 'Văn phòng phẩm', 'van-phong-pham');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (27, 'Đồ chơi', 'Đồ chơi', 'do-choi');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (28, 'Thực phẩm', 'Thực phẩm', 'thuc-pham');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (29, 'Mỹ phẩm', 'Mỹ phẩm', 'my-pham');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (30, 'Chăm sóc sức khỏe', 'Chăm sóc sức khỏe', 'cham-soc-suc-khoe');
INSERT INTO mydb.category (category_id, description, category_name, slug) VALUES (31, 'Thể thao', 'Thể thao', 'the-thao');

-- Thêm thể loại
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (2, 'Apple', 'Thương hiệu Apple - iPhone, MacBook, iPad nổi tiếng của Mỹ', 'apple.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (3, 'Samsung', 'Thương hiệu công nghệ hàng đầu Hàn Quốc', 'samsung.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (4, 'Xiaomi', 'Thương hiệu điện thoại và thiết bị thông minh của Trung Quốc', 'xiaomi.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (5, 'Oppo', 'Thương hiệu điện thoại phổ biến tại châu Á', 'oppo.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (6, 'Vivo', 'Thương hiệu smartphone trẻ trung, nhiều tính năng', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (7, 'Realme', 'Thương hiệu smartphone giá rẻ, hiệu năng cao', 'realme.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (8, 'Huawei', 'Tập đoàn công nghệ hàng đầu Trung Quốc', 'huawei.svg');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (9, 'Asus', 'Thương hiệu laptop, điện thoại, linh kiện nổi tiếng', 'asus.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (10, 'Acer', 'Nhà sản xuất laptop và màn hình máy tính Đài Loan', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (11, 'Dell', 'Thương hiệu laptop và máy tính doanh nghiệp của Mỹ', 'dell.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (12, 'HP', 'Hewlett-Packard - thương hiệu máy tính, máy in lâu đời', 'hp.webp');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (13, 'Lenovo', 'Tập đoàn công nghệ đa quốc gia Trung Quốc', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (14, 'MSI', 'Thương hiệu chuyên gaming laptop và phần cứng', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (15, 'Sony', 'Thương hiệu Nhật Bản nổi tiếng với TV, PlayStation', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (16, 'LG', 'Tập đoàn điện tử Hàn Quốc - TV, tủ lạnh, máy giặt', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (17, 'Toshiba', 'Thương hiệu Nhật Bản - điện tử, thiết bị gia dụng', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (18, 'Panasonic', 'Thương hiệu Nhật chuyên đồ điện tử và gia dụng', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (19, 'Canon', 'Thương hiệu máy ảnh, máy in hàng đầu Nhật Bản', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (20, 'Nikon', 'Thương hiệu máy ảnh nổi tiếng thế giới', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (21, 'Casio', 'Thương hiệu đồng hồ, máy tính bỏ túi, nhạc cụ', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (22, 'Rolex', 'Thương hiệu đồng hồ cao cấp Thụy Sĩ', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (23, 'Adidas', 'Thương hiệu thể thao toàn cầu từ Đức', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (24, 'Nike', 'Thương hiệu thể thao hàng đầu thế giới', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (25, 'Puma', 'Thương hiệu thể thao phong cách năng động', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (26, 'Gucci', 'Thương hiệu thời trang cao cấp của Ý', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (27, 'Chanel', 'Thương hiệu mỹ phẩm và thời trang cao cấp Pháp', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (28, 'Dior', 'Thương hiệu xa xỉ của Pháp', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (29, 'Hermès', 'Thương hiệu đồ da và nước hoa cao cấp Pháp', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (30, 'Unilever', 'Tập đoàn hàng tiêu dùng đa quốc gia Anh - Hà Lan', 'lenovo.png');
INSERT INTO mydb.brands (brand_id, brand_name, description, image) VALUES (31, 'P&G', 'Procter & Gamble - tập đoàn hàng tiêu dùng Mỹ', 'lenovo.png');

-- Thêm màu sắc
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (1, 'Đen 1.1', 'Đen 1.1');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (2, 'Đen', 'Đen');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (3, 'Trắng', 'Trắng');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (4, 'Đỏ', 'Đỏ');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (5, 'Xanh dương', 'Xanh dương');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (6, 'Xanh lá', 'Xanh lá');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (7, 'Vàng', 'Vàng');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (8, 'Cam', 'Cam');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (9, 'Tím', 'Tím');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (10, 'Hồng', 'Hồng');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (11, 'Nâu', 'Nâu');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (12, 'Xám', 'Xám');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (13, 'Bạc', 'Bạc');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (14, 'Vàng đồng', 'Vàng đồng');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (15, 'Be', 'Be');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (16, 'Xanh navy', 'Xanh navy');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (17, 'Xanh ngọc', 'Xanh ngọc');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (18, 'Đỏ đô', 'Đỏ đô');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (19, 'Xanh rêu', 'Xanh rêu');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (20, 'Kem', 'Kem');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (21, 'Ghi', 'Ghi');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (22, 'Trắng ngà', 'Trắng ngà');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (23, 'Đen nhám', 'Đen nhám');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (24, 'Xanh paste', 'Xanh pastel');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (25, 'Hồng paste', 'Hồng pastel');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (26, 'Tím pastel', 'Tím pastel');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (27, 'Nâu đất', 'Nâu đất');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (28, 'Xanh lam', 'Xanh lam');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (29, 'Đen bóng', 'Đen bóng');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (30, 'Đỏ tươi', 'Đỏ tươi');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (31, 'Vàng chanh', 'Vàng chanh');
INSERT INTO mydb.colors (color_id, color_code, color_name) VALUES (32, 'Bạc ánh ki', 'Bạc ánh kim');

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

INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (1, 'Điện thoại iPhone 5 - thiết kế sang trọng, hiệu năng ổn định', null, 'iPhone 5', true, 2, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (2, 'Điện thoại iPhone 6 - thiết kế sang trọng, hiệu năng ổn định', null, 'iPhone 6', true, 2, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (3, 'Điện thoại iPhone 14 Pro Max - cao cấp, mạnh mẽ', null, 'iPhone 14 Pro Max', true, 2, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (4, 'Điện thoại Samsung Galaxy S24 Ultra - flagship đỉnh cao', null, 'Samsung Galaxy S24 Ultra', true, 3, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (5, 'Điện thoại Xiaomi Redmi Note 13 Pro', null, 'Xiaomi Redmi Note 13 Pro', true, 4, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (6, 'Điện thoại Oppo Reno 10', null, 'Oppo Reno 10', true, 5, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (7, 'Điện thoại Vivo V30', null, 'Vivo V30', true, 6, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (8, 'Điện thoại Realme 11 Pro', null, 'Realme 11 Pro', true, 7, 2);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (9, 'Laptop MacBook Air M2 - hiệu năng mạnh, pin lâu', null, 'MacBook Air M2', true, 2, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (10, 'Laptop Dell XPS 13 - cao cấp, siêu mỏng nhẹ', null, 'Dell XPS 13', true, 11, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (11, 'Laptop Asus TUF Gaming F15', null, 'Asus TUF Gaming F15', true, 9, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (12, 'Laptop HP Pavilion 15', null, 'HP Pavilion 15', true, 12, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (13, 'Laptop Lenovo ThinkPad X1 Carbon', null, 'Lenovo ThinkPad X1 Carbon', true, 13, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (14, 'Laptop MSI GF63 Thin', null, 'MSI GF63 Thin', true, 14, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (15, 'Laptop Acer Aspire 7', null, 'Acer Aspire 7', true, 10, 3);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (16, 'Máy tính bảng iPad Air 5', null, 'iPad Air 5', true, 2, 4);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (17, 'Máy tính bảng Samsung Galaxy Tab S9', null, 'Samsung Galaxy Tab S9', true, 3, 4);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (18, 'Máy tính bảng Xiaomi Pad 6', null, 'Xiaomi Pad 6', true, 4, 4);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (19, 'Tai nghe Bluetooth AirPods Pro 2', null, 'AirPods Pro 2', true, 2, 5);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (20, 'Tai nghe Samsung Buds FE', null, 'Samsung Buds FE', true, 3, 5);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (21, 'Sạc nhanh 65W Oppo SuperVOOC', null, 'Oppo SuperVOOC 65W', true, 5, 5);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (22, 'Ốp lưng iPhone 14 Pro Max chính hãng', null, 'Ốp lưng iPhone 14 Pro Max', true, 2, 5);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (23, 'Smart TV LG 55 inch 4K UHD', null, 'LG Smart TV 55UQ7550', true, 16, 6);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (24, 'Smart TV Sony Bravia 65 inch 4K', null, 'Sony Bravia X90L 65 inch', true, 15, 6);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (25, 'Smart TV Samsung Crystal UHD 50 inch', null, 'Samsung Crystal UHD 50 inch', true, 3, 6);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (26, 'Máy ảnh Canon EOS R8', null, 'Canon EOS R8', true, 19, 13);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (27, 'Máy ảnh Nikon Z6 II', null, 'Nikon Z6 II', true, 20, 13);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (28, 'Máy ảnh Sony Alpha A7 IV', null, 'Sony Alpha A7 IV', true, 15, 13);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (29, 'Đồng hồ thông minh Apple Watch Series 9', null, 'Apple Watch Series 9', true, 2, 12);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (30, 'Đồng hồ Casio G-Shock GA-2100', null, 'Casio G-Shock GA-2100', true, 21, 12);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (31, 'Đồng hồ Rolex Submariner', null, 'Rolex Submariner', true, 22, 12);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (32, 'Áo thun nam Adidas chính hãng', null, 'Áo thun Adidas Nam', true, 23, 19);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (33, 'Giày Nike Air Force 1', null, 'Nike Air Force 1', true, 24, 21);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (34, 'Giày Puma RS-X', null, 'Puma RS-X', true, 25, 21);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (35, 'Túi xách Gucci Marmont', null, 'Túi Gucci Marmont', true, 26, 22);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (36, 'Nước hoa Dior Sauvage', null, 'Dior Sauvage Eau de Toilette', true, 28, 29);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (37, 'Son môi Chanel Rouge Allure', null, 'Chanel Rouge Allure', true, 27, 29);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (38, 'Kem dưỡng da Unilever Dove', null, 'Dove Body Lotion', true, 30, 30);
INSERT INTO mydb.products (product_id, description, image, product_name, status, brand_id, category_id) VALUES (39, 'Bột giặt Ariel P&G', null, 'Ariel Hương Downy', true, 31, 23);

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
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (1, null, 'iPhone 5 - thiết kế sang trọng, hiệu năng ổn định', 0, null, 10, 10, 'iphone-5s.jpg', true, false, 'iPhone 5', 100, 6000000, 10, 7, 1, 3, 2, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (2, null, 'iPhone 6 - thiết kế sang trọng, hiệu năng ổn định', 0, null, 10, 10, 'iPhone 6.webp', true, false, 'iPhone 6', 100, 6000000, 10, 7, 1, 3, 2, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (3, null, 'iPhone 14 Pro Max - cao cấp, mạnh mẽ', 10, null, 10, 10, 'iPhone 14 Pro Max.jpg', true, true, 'iPhone 14 Pro Max', 200, 29900000, 10, 7, 1, 6, 3, 8);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (4, null, 'Samsung Galaxy S24 Ultra - flagship đỉnh cao', 5, null, 10, 10, 'Samsung Galaxy S24 Ultra.webp', true, true, 'Samsung Galaxy S24 Ultra', 150, 25900000, 10, 7, 1, 6, 4, 8);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (5, null, 'Xiaomi Redmi Note 13 Pro', 0, null, 10, 10, 'Xiaomi Redmi Note 13 Pro.jpg', true, false, 'Xiaomi Redmi Note 13 Pro', 100, 8990000, 10, 7, 3, 5, 5, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (6, null, 'Oppo Reno 10', 0, null, 10, 10, 'Oppo Reno 10.webp', false, false, 'Oppo Reno 10', 120, 10990000, 10, 7, 2, 5, 6, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (7, null, 'Vivo V30', 0, null, 10, 10, 'Vivo V30.webp', false, false, 'Vivo V30', 100, 9990000, 10, 7, 3, 5, 7, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (8, null, 'Realme 11 Pro', 0, null, 10, 10, 'Realme 11 Pro.webp', false, false, 'Realme 11 Pro', 80, 8990000, 10, 7, 2, 4, 8, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (9, null, 'MacBook Air M2 - hiệu năng mạnh, pin lâu', 0, null, 10, 10, 'MacBook Air M2.png', true, true, 'MacBook Air M2', 100, 28900000, 15, 30, 1, 6, 9, 8);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (10, false, 'Dell XPS 13 - cao cấp, siêu mỏng nhẹ', 5, false, 10, 10, 'Dell XPS 13.jpg', false, false, 'Dell XPS 13', 90, 25900000, 15, 30, 2, 1, 10, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (11, false, 'Asus TUF Gaming F15', 0, false, 10, 10, 'Asus TUF Gaming F15.jpg', false, false, 'Asus TUF Gaming F15', 80, 20900000, 20, 32, 3, 1, 11, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (12, null, 'HP Pavilion 15', 0, null, 10, 10, 'HP Pavilion 15.jpg', false, false, 'HP Pavilion 15', 100, 17900000, 15, 32, 2, 5, 12, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (13, null, 'Lenovo ThinkPad X1 Carbon', 10, null, 10, 10, 'Lenovo ThinkPad X1 Carbon.webp', true, true, 'Lenovo ThinkPad X1 Carbon', 50, 33900000, 15, 32, 1, 6, 13, 8);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (14, null, 'MSI GF63 Thin', 0, null, 10, 10, 'MSI GF63 Thin.webp', false, true, 'MSI GF63 Thin', 80, 20900000, 18, 32, 2, 6, 14, 8);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (15, false, 'Acer Aspire 7', 0, false, 10, 10, 'Acer Aspire 7.png', false, false, 'Acer Aspire 7', 120, 16900000, 18, 32, 3, 1, 15, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (16, null, 'iPad Air 5 - mỏng nhẹ, mạnh mẽ', 0, null, 10, 10, 'iPad Air 5.webp', true, false, 'iPad Air 5', 100, 16900000, 10, 20, 1, 5, 16, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (17, null, 'Samsung Galaxy Tab S9', 0, null, 10, 10, 'Galaxy Tab S9.webp', true, true, 'Galaxy Tab S9', 80, 18900000, 10, 20, 1, 5, 17, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (18, null, 'Xiaomi Pad 6', 0, null, 10, 10, 'Xiaomi Pad 6.jpg', false, false, 'Xiaomi Pad 6', 120, 9990000, 10, 20, 3, 4, 18, 6);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (19, false, 'Tai nghe AirPods Pro 2 - chống ồn chủ động', 0, false, 10, 10, 'AirPods Pro 2.jpg', false, false, 'AirPods Pro 2', 200, 5490000, 5, 5, 1, 1, 19, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (20, null, 'Tai nghe Samsung Buds FE', 0, null, 10, 10, 'Samsung Buds FE.webp', false, true, 'Samsung Buds FE', 180, 2990000, 5, 5, 2, 2, 20, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (21, null, 'Sạc nhanh Oppo SuperVOOC 65W', 0, null, 10, 10, 'Oppo SuperVOOC 65W.webp', false, false, 'Oppo SuperVOOC 65W', 150, 990000, 5, 5, 3, 2, 21, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (22, null, 'Ốp lưng iPhone 14 Pro Max chính hãng', 0, null, 10, 10, 'Ốp lưng iPhone 14 Pro Max.jpg', false, false, 'Ốp lưng iPhone 14 Pro Max', 300, 490000, 5, 5, 4, 2, 22, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (23, null, 'Smart TV LG 55 inch 4K UHD', 5, null, 60, 80, 'LG Smart TV 55UQ7550.avif', true, true, 'LG Smart TV 55UQ7550', 60, 10900000, 8000, 120, 2, 2, 23, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (24, null, 'Smart TV Sony Bravia X90L 65 inch', 10, null, 60, 80, 'Sony Bravia X90L.png', true, true, 'Sony Bravia X90L', 40, 18900000, 8500, 130, 1, 2, 24, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (25, null, 'Smart TV Samsung Crystal UHD 50 inch', 0, null, 60, 80, 'Samsung Crystal UHD 50.jpg', false, true, 'Samsung Crystal UHD 50', 70, 9990000, 7000, 115, 3, 2, 25, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (26, false, 'Canon EOS R8', 0, false, 10, 10, 'Canon EOS R8.jpg', false, false, 'Canon EOS R8', 40, 35900000, 20, 10, 1, 1, 26, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (27, null, 'Nikon Z6 II', 0, null, 10, 10, 'Nikon Z6 II.jpg', true, false, 'Nikon Z6 II', 30, 39900000, 20, 10, 2, 2, 27, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (28, null, 'Sony Alpha A7 IV', 0, null, 10, 10, 'Sony Alpha A7 IV.jpg', true, true, 'Sony Alpha A7 IV', 35, 41900000, 20, 10, 1, 2, 28, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (29, false, 'Apple Watch Series 9', 0, false, 5, 5, 'Apple Watch Series 9.jpg', false, false, 'Apple Watch Series 9', 100, 10900000, 1, 5, 1, 1, 29, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (30, false, 'Casio G-Shock GA-2100', 0, false, 5, 5, 'Casio G-Shock GA-2100.jpg', false, false, 'Casio G-Shock GA-2100', 150, 3990000, 1, 5, 2, 1, 30, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (31, null, 'Rolex Submariner', 0, null, 5, 5, 'Rolex Submariner.webp', true, true, 'Rolex Submariner', 10, 250000000, 1, 5, 1, 2, 31, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (32, false, 'Áo thun Adidas Nam', 0, false, 2, 2, 'Áo thun Adidas Nam.webp', false, false, 'Áo thun Adidas Nam', 200, 590000, 1, 2, 3, 1, 32, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (33, null, 'Giày Nike Air Force 1', 0, null, 2, 2, 'Nike Air Force 1.jpg', true, true, 'Nike Air Force 1', 150, 2500000, 1, 2, 1, 2, 33, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (34, null, 'Giày Puma RS-X', 0, null, 2, 2, 'Puma RS-X.avif', false, false, 'Puma RS-X', 100, 2300000, 1, 2, 2, 2, 34, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (35, null, 'Túi xách Gucci Marmont', 0, null, 10, 10, 'Gucci Marmont.webp', true, true, 'Gucci Marmont', 50, 49900000, 2, 10, 1, 2, 35, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (36, null, 'Nước hoa Dior Sauvage Eau de Toilette', 0, null, 10, 10, 'Dior Sauvage.jpg', true, true, 'Dior Sauvage', 80, 2990000, 1, 10, 3, 2, 36, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (37, false, 'Son môi Chanel Rouge Allure', 0, false, 10, 10, 'Chanel Rouge Allure.webp', false, false, 'Chanel Rouge Allure', 150, 1290000, 1, 10, 2, 1, 37, 1);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (38, null, 'Kem dưỡng da Dove', 0, null, 10, 10, 'Dove Body Lotion.webp', false, false, 'Dove Body Lotion', 200, 199000, 1, 10, 3, 2, 38, 3);
INSERT INTO mydb.product_detail (product_detail_id, bestseller, description, discount, discount_current, haute, height, image, most_recent, outstanding, product_detail_name, quantity, unit_price, weight, width, color_id, memory_storage_id, product_id, ram_id) VALUES (39, false, 'Bột giặt Ariel Hương Downy', 0, false, 10, 10, 'Ariel P&G.jpg', false, false, 'Ariel Hương Downy', 300, 149000, 1, 10, 2, 1, 39, 1);

```