SELECT * FROM user;
select * from cart;
DROP TABLE courses;
ALTER TABLE cart AUTO_INCREMENT = 10000;
SHOW CREATE TABLE cart;

ALTER TABLE cart drop uid;
ALTER TABLE `cart`
    DROP FOREIGN KEY `FKds3koghimkbw4cpwr07oxsv23`;

-- Drop the unique key on `uid`
ALTER TABLE `cart`
    DROP INDEX `UK64lay2b4s78dvnpnb9ecy7jf0`;
