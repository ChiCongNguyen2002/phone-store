-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2023 at 08:52 PM
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
(30, 'IPHONE', 4, 1),
(31, 'SAMSUNG', 2, 1),
(32, 'VIVO', 1, 1);

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
(4, 'CM6', 6, '6', 'Khuyến mãi 6%', 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, '26477600-a2f8-4cff-a529-9f4790c03d89', 0, '2023-11-22 23:13:10', 0);

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
(24, 7, 132, 1, 230000);

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
(7, 11, '2022-10-22', 5, 1, 'COD', 1500000, 'Dinh Thinh', 'Ca Mau', '0941124123'),
(8, 8, '2023-11-24', 4, 2, 'COD', 1500000, 'Tan Duy', 'Cà Mau', '0129293493'),
(9, 7, '2023-10-05', 1, 1, 'COD', 1500000, 'Ngoc So', 'Ca Mau', '0129294393'),
(10, 11, '2023-08-22', 1, 1, 'COD', 1500000, 'Dinh Thinh', 'Ca Mau', '0941124123'),
(11, 11, '2023-06-22', 1, 1, 'COD', 1500000, 'Dinh Thinh', 'Ca Mau', '0941124123');

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
(132, 'iPhone 15 Pro Max 256GB', 34290000, 'IPhone_2.jpeg', 'Đẹp Xứng Đáng với chất lượng', 30, 100, 'xanh', 1),
(133, 'iPhone 15 Pro Max 256GB', 34290000, 'IPhone_3.jpeg', 'Đẹp Xanh', 30, 100, 'Trắng', 1),
(134, 'SAM SUNG GALAXY ULTRA S23', 25400000, 'SamSung_1.jpeg', 'Đen Đẹp', 31, 3, 'Đen', 1),
(135, 'SAM SUNG GALAXY S21', 9490000, 'SamSung_3.jpeg', 'Đen Đẹp Ngầu', 31, 3, 'Đen', 1),
(136, 'IPHONE 13 PROMAX', 35000000, 'IPhone_4.jpeg', 'Đen Đep Ngầu', 30, 100, 'Đen', 1),
(137, 'IPHONE 14', 25000000, 'IPhone_2.jpeg', 'Xanh Đen', 30, 5, 'xanh', 1),
(138, 'SAMSUNG GALAXY', 35000000, 'SamSung_3.jpeg', 'Đen ngầu', 31, 3, 'Đen', 1),
(139, 'IPhone 15 Pro Max 256GB', 2600000, 'IPhone_5.png', 'iPhone 15 Pro Max đem lại một diện mạo hoàn toàn mới và sở hữu nhiều tính năng ưu việt cùng công nghệ tiên tiến. Hãy khám phá các đánh giá chi tiết về sản phẩm về khía cạnh thiết kế, màn hình, hiệu năng, thời lượng pin và bộ camera độc đáo qua các thông tin dưới đây!', 30, 2, 'Trắng TiTan', 1);

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
(2, 'Samsung', '0765433120', 'Ha Noi', 1),
(4, 'IPHONE', '0386431800', 'Ca Mau', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `fullname` varchar(30) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `fullname`, `role`) VALUES
(7, 'congkhpro291002@gmail.com', '$2a$10$AsMAjMnlq.6HRBQb0tXrou3lNKAw6YaismGBXHzBdHW6tMOOIaOwq', 'Nguyễn Đình Thịnh', 'USER'),
(8, 'congnguyen@gmail.com', '$2a$10$/RK4hgagmY7w/8CyYhQ.E.qcgFiI1ANppwn3Pq6l0oOFRjwvUOxSm', 'Nguyễn Chí Công', 'USER'),
(9, 'admin@gmail.com', '$2a$10$cFfZBkooDqE7HzlM0b9dm.CGFs7XE3bpUVPB.iSIJyB/OnQKIPAHm', 'Nguyễn Chí Công', 'ADMIN'),
(11, 'chicong123@gmail.com', '$2a$10$fvULYA6JnR2wssmuqEE.rOdXJ0/yK1poTTI2NqfB.qmizyWiWxPNS', 'chicong', 'USER');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forgot_password_token`
--
ALTER TABLE `forgot_password_token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `orderdetail`
--
ALTER TABLE `orderdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=140;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `shoppingcart`
--
ALTER TABLE `shoppingcart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

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
