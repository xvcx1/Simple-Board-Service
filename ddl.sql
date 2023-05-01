drop database if exists b2bProject;
create database b2bProject;

use b2bProject;

drop table if exists board;
create table board(
                      board_no BIGINT AUTO_INCREMENT NOT NULL,
                      title varchar(100),
                      content varchar(500),
                      user_id varchar(50),
                      views int,
                      written_date varchar(50),
                      primary key(board_no)
);

CREATE PROCEDURE loopInsert()
BEGIN
  DECLARE i INT DEFAULT 1;
  WHILE (i <= 3000) DO
      INSERT INTO board VALUES (i, CONCAT('test제목 ', i), CONCAT('test내용 ', i), 'a123', 0, '2022-12-10 23:59:59');
      SET i = i+1;
END WHILE;
END;

CALL loopInsert();


drop table if exists comment;
create table comment(
                        comment_no BIGINT AUTO_INCREMENT NOT NULL,
                        board_no BIGINT,
                        content varchar(300),
                        user_id varchar(50),
                        written_date varchar(50),
                        primary key(comment_no)
);

drop table if exists user;
create table user(
                     id varchar(50) NOT NULL,
                     password varchar(300),
                     name varchar(20),
                     birth date,
                     gender varchar(10),
                     address varchar(100),
                     phone_number varchar(50),
                     primary key(id)
);

insert into user values ('qwer1234', '$2a$10$8jbDPO.EXIfuvdTOJ./J4umHUofL1J.kQiAHQ04GMib1mybLKEQKa', 'root', '2022-12-22', '남', '김포시', '010-1234-1234');