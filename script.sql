CREATE TABLE books (
  isbn int(10) NOT NULL,
  title varchar(30) DEFAULT NULL,
  author varchar(50) DEFAULT NULL,
  pub varchar(10) DEFAULT NULL,
  cat varchar(5) DEFAULT NULL,
  price int(5) DEFAULT NULL
);

--
-- Dumping data for table books
--

INSERT INTO books (isbn, title, author, pub, cat, price) VALUES
(1, 'Java Programming', 'Manish Hurkat', 'NIIT', 'Java', 1000),
(2, 'Introduction .Net Programming', 'Akhelesh Agarwal', 'NIIT', '.NET', 500),
(3, 'Introduction to Oracle', 'Sumen Dey', 'NIIT', 'Oracl', 600);

CREATE TABLE orderitem (
  ordid int(5) NOT NULL,
  isbn int(10) NOT NULL,
  price int(5) DEFAULT NULL,
  qty int(3) DEFAULT NULL
);



CREATE TABLE orders (
  ordid int(5) NOT NULL,
  userid int(5) DEFAULT NULL,
  orddate timestamp NULL DEFAULT NULL,
  totamt int(6) DEFAULT NULL,
  status char(1) DEFAULT NULL,
  isbn int(11) NOT NULL
);



INSERT INTO orders (ordid, userid, orddate, totamt, status, isbn) VALUES
(1, 2, '2018-11-20 18:30:00', 500, 'A', 0),
(2, 2, NULL, 500, 'a', 0),
(3, 2, '2018-11-20 18:30:00', 600, 'a', 0),
(4, 2, '2018-11-20 18:30:00', 500, 'a', 0),
(5, 2, '2018-11-20 18:30:00', 500, 'a', 0),
(6, 2, '2018-11-20 18:30:00', 500, 'a', 0),
(7, 2, '2018-11-20 18:30:00', 500, 'a', 3),
(8, 2, '2018-11-20 18:30:00', 500, 'a', 3),
(9, 2, '2018-11-21 18:30:00', 400, 'a', 2),
(10, 2, '2018-11-21 18:30:00', 400, 'a', 2),
(11, 2, '2018-11-21 18:30:00', 450, 'a', 2),
(12, 2, '2018-11-21 20:07:04', 12, 'a', 2),
(13, 2, '2018-11-21 20:20:58', 450, 'a', 2),
(14, 2, '2018-11-21 20:22:08', 450, 'a', 2),
(15, 2, '2018-11-21 20:23:41', 450, 'a', 2),
(16, 2, '2018-11-21 20:29:23', 675, 'a', 2),
(17, 2, '2018-11-21 20:40:27', 450, 'a', 2);



CREATE TABLE users (
  userid int(5) NOT NULL,
  uname varchar(20) NOT NULL,
  pwd varchar(10) DEFAULT NULL,
  email varchar(30) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  phone varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO users (userid, uname, pwd, email, address, phone) VALUES
(2, 'Rk', '12345', 'aaaaaa@gmail.com', 'dfdf', '9999999999'),
(4, 'pk', '23', 'aaaadfsf@gmail.com', 'dfdf', '9999999999');

ALTER TABLE books
  ADD PRIMARY KEY (isbn);


ALTER TABLE orderitem
  ADD PRIMARY KEY (ordid,isbn);


ALTER TABLE orders
  ADD PRIMARY KEY (ordid);


ALTER TABLE users
  ADD PRIMARY KEY (userid),
  ADD UNIQUE KEY uname (uname),
  ADD UNIQUE KEY email (email);


ALTER TABLE orders
  MODIFY ordid int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;


