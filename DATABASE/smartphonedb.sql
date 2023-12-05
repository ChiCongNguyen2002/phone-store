-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 05, 2023 at 02:44 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smartphonedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `cartitem`
--

CREATE TABLE `cartitem` (
  `id` int(11) NOT NULL,
  `shoppingCartId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `supplierId`, `status`) VALUES
(30, 'IPHONE', 1, 1),
(31, 'SAMSUNG', 2, 1),
(32, 'VIVO', 5, 1),
(39, 'IPHONE1', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE `coupon` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `count` int(11) NOT NULL,
  `promotion` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coupon`
--

INSERT INTO `coupon` (`id`, `code`, `count`, `promotion`, `description`, `status`) VALUES
(1, 'CM3', 40, '3', 'Khuyến mãi 3%', 1),
(2, 'CM4', 30, '10', 'Khuyến mãi 10%', 1),
(3, 'CM5', 5, '5', 'Khuyến mãi 5%', 1),
(13, 'C', 1, '1', '1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `userId` int(11) NOT NULL,
  `statusCustomer` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `fullname`, `address`, `phone`, `email`, `userId`, `statusCustomer`) VALUES
(1, 'Nguyen Chi Cong', 'Cà Mau', '0948399484', 'congkhpro291002@gmail.com', 7, 1),
(2, 'Nguyen Van Tien Dụng', 'Binh Chanh', '0948399484', 'tiendung2209@gmail.com', 8, 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `salary` double NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `userId`, `fullname`, `address`, `phone`, `email`, `salary`, `status`) VALUES
(2, 11, 'Võ Văn Huấn', 'Vung Tau', '0948399484', 'congkhpro291002@gmail.com', 2500000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `forgot_password_token`
--

CREATE TABLE `forgot_password_token` (
  `id` int(11) NOT NULL,
  `token` varchar(100) NOT NULL,
  `isuUsed` tinyint(1) NOT NULL,
  `expireTime` datetime NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `forgot_password_token`
--

INSERT INTO `forgot_password_token` (`id`, `token`, `isuUsed`, `expireTime`, `user_id`) VALUES
(1, '26477600-a2f8-4cff-a529-9f4790c03d89', 0, '2023-11-22 23:13:10', 0),
(2, '31c83e68-0237-4013-9c47-f7ee72741415', 1, '2023-12-02 02:01:50', 7),
(3, '436af7e9-3b8e-4e38-a187-06690f8b8fe8', 0, '2023-12-03 21:34:36', 7);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `id` int(11) NOT NULL,
  `orderId` int(11) DEFAULT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unitPrice` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`id`, `orderId`, `productId`, `quantity`, `unitPrice`) VALUES
(22, 7, 131, 2, 20000),
(23, 8, 132, 4, 13000000),
(24, 9, 132, 1, 230000),
(25, 9, 142, 2, 20000);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `orderDate` date NOT NULL,
  `Status` int(11) NOT NULL,
  `couponId` int(11) NOT NULL,
  `paymentMethod` varchar(50) NOT NULL,
  `total` double NOT NULL,
  `ShipName` varchar(50) NOT NULL,
  `ShipAddress` varchar(200) NOT NULL,
  `ShipPhoneNumber` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `userId`, `orderDate`, `Status`, `couponId`, `paymentMethod`, `total`, `ShipName`, `ShipAddress`, `ShipPhoneNumber`) VALUES
(7, 11, '2023-10-22', 5, 1, 'COD', 2500000, 'Dinh Thinh', 'Ca Mau', '0941124123'),
(8, 8, '2023-11-24', 2, 2, 'COD', 2000000, 'Tan Duy', 'Cà Mau', '0129293493'),
(9, 7, '2023-10-05', 4, 1, 'COD', 1500000, 'Ngoc So', 'Ca Mau', '0129294393');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double(50,0) NOT NULL,
  `Image` varchar(100) NOT NULL,
  `description` varchar(10000) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `color` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `Image`, `description`, `categoryId`, `quantity`, `color`, `status`) VALUES
(131, 'iPhone 15 Pro Max 256GB', 34920000, 'IPhone_1.jpeg', 'iPhone 15 Pro Max đem lại một diện mạo hoàn toàn mới và sở hữu nhiều tính năng ưu việt cùng công nghệ tiên tiến. Hãy khám phá các đánh giá chi tiết về sản phẩm về khía cạnh thiết kế, màn hình, hiệu năng, thời lượng pin và bộ camera độc đáo qua các thông tin dưới đây!', 30, 100, 'đen', 1),
(132, 'iPhone 15 Pro Max 256GB', 34290000, 'IPhone_2.png', 'Đẹp Xứng Đáng với chất lượng', 30, 100, 'xanh', 1),
(133, 'iPhone 15 Pro Max 256GB', 34290000, 'IPhone_3.jpeg', 'Đẹp Xanh', 30, 100, 'Trắng', 1),
(142, 'iPhone 15 Plus VN/a', 25000000, 'iphone-15-plus-black-thumbtz-650x650.png.png', 'iPhone 15 Plus VN/a', 30, 100, 'đen', 1),
(143, 'iPhone 15 Plus VN/a', 25590000, 'iphone-15-plus yellow.png', 'iPhone 15 Plus VN/a', 30, 100, 'Vàng', 1),
(144, 'iPhone 15 Plus VN/a', 25590000, 'iphone15pink.jpg', 'iPhone 15 Plus VN/a', 30, 100, 'hồng', 1),
(145, 'Samsung Galaxy S23 Ultra 8/256GB', 25900000, 'SamsungGalaxyS23_BLACK.png', 'Samsung Galaxy S23 Ultra 8/256GB', 31, 100, 'Đen', 1),
(146, 'Samsung Galaxy S23 Ultra 8/256GB', 25590000, 'SamsungGalaxyS23_WHITE.png', 'Samsung Galaxy S23 Ultra 8/256GB', 31, 100, 'Trắng', 1),
(147, 'Samsung Galaxy S23 Ultra 8/256GB', 25590000, 'SamsungGalaxyS23_GREEN.png', 'Samsung Galaxy S23 Ultra 8/256GB', 31, 100, 'xanh lá', 1),
(149, 'Samsung Galaxy A54 5G 8/128GB', 8690000, 'SamsungGalaxyA5_BLACK.png', 'Samsung Galaxy A54 5G 8/128GB', 31, 100, 'đen', 1),
(150, 'Samsung Galaxy A54 5G 8/128GB', 8690000, 'SamsungGalaxyA5_TIM.png', 'Samsung Galaxy A54 5G 8/128GB', 31, 100, 'Tím', 1),
(151, 'Vivo V29e 8GB-256GB', 8690000, 'Vivo_V29e.png', 'Vivo V29e 8GB-256GB', 32, 100, 'xanh', 1),
(152, 'Vivo Y17s 4/128GB', 3890000, 'Vivo_Y17s.png', 'Vivo Y17s 4/128GB', 32, 100, 'Tím', 1),
(153, 'Vivo Y36 8-128GB', 5490000, 'Vivo_Y36.png', 'Vivo Y36 8-128GB', 32, 100, 'xanh', 1);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `rating` int(50) NOT NULL,
  `DateReview` datetime NOT NULL,
  `comments` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `userId`, `productId`, `rating`, `DateReview`, `comments`) VALUES
(31, 11, 131, 5, '2023-11-26 12:16:34', 'Tốt'),
(32, 9, 142, 5, '2023-12-05 08:58:12', 'san pham tot'),
(33, 7, 142, 2, '2023-12-05 08:59:13', 'san pham binh thuong');

-- --------------------------------------------------------

--
-- Table structure for table `shoppingcart`
--

CREATE TABLE `shoppingcart` (
  `id` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `totalItems` int(11) NOT NULL,
  `totalPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `PhoneNumber` varchar(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Id`, `Name`, `PhoneNumber`, `Address`, `status`) VALUES
(1, 'Apple', '0816089161', 'Ca Mau', 1),
(2, 'Samsung', '0785431800', 'Ha Noi', 1),
(5, 'Vivo APEX', '0785346410', 'Ha Noi', 1),
(12, 'Apple', '0912111222', '11', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `fullname` varchar(30) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `role` enum('ADMIN','USER','EMPLOYEE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `fullname`, `address`, `phone`, `role`) VALUES
(7, 'congkhpro291002@gmail.com', '$2a$10$4zzxBP0Ot2GonLPW4mNnz.BkeS9tX0ZWM5FL4rPHc607QNVyzUGgm', 'Nguyễn Đình Thịnh', 'Ca Mau', '0948399484', 'USER'),
(8, 'congnguyen@gmail.com', '$2a$10$/RK4hgagmY7w/8CyYhQ.E.qcgFiI1ANppwn3Pq6l0oOFRjwvUOxSm', 'Nguyễn Chí Công', 'Cà Mau', '0948831177', 'USER'),
(9, 'admin@gmail.com', '$2a$10$cFfZBkooDqE7HzlM0b9dm.CGFs7XE3bpUVPB.iSIJyB/OnQKIPAHm', 'Nguyễn Chí Công', 'Bến Tre', '0912111222', 'ADMIN'),
(11, 'chicong123@gmail.com', '$2a$10$fvULYA6JnR2wssmuqEE.rOdXJ0/yK1poTTI2NqfB.qmizyWiWxPNS', 'chi cong', 'Đắk Lắk', '0386431800', 'EMPLOYEE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cartitem`
--
ALTER TABLE `cartitem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `shopping_cart_fk` (`shoppingCartId`),
  ADD KEY `product_fk_1` (`productId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `s` (`supplierId`);

--
-- Indexes for table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_fk_3` (`userId`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_fk_2` (`userId`);

--
-- Indexes for table `forgot_password_token`
--
ALTER TABLE `forgot_password_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_fk` (`orderId`),
  ADD KEY `product_fk` (`productId`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coupon_fk` (`couponId`),
  ADD KEY `user_fk` (`userId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_fk` (`categoryId`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_fk_2` (`productId`);

--
-- Indexes for table `shoppingcart`
--
ALTER TABLE `shoppingcart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cartitem`
--
ALTER TABLE `cartitem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `forgot_password_token`
--
ALTER TABLE `forgot_password_token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `orderdetail`
--
ALTER TABLE `orderdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `shoppingcart`
--
ALTER TABLE `shoppingcart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cartitem`
--
ALTER TABLE `cartitem`
  ADD CONSTRAINT `product_fk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `shopping_cart_fk` FOREIGN KEY (`shoppingCartId`) REFERENCES `shoppingcart` (`id`);

--
-- Constraints for table `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `s` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`Id`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `user_fk_3` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `user_fk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD CONSTRAINT `order_fk` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `product_fk` FOREIGN KEY (`productId`) REFERENCES `product` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `coupon_fk` FOREIGN KEY (`couponId`) REFERENCES `coupon` (`id`),
  ADD CONSTRAINT `user_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `category_fk` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `product_fk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
