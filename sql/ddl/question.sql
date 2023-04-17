CREATE TABLE question (id BIGINT AUTO_INCREMENT PRIMARY KEY,
quiz_id BIGINT,
num int,
content text,
comment text,
choiceType text,
create_userid BIGINT
) AUTO_INCREMENT = 20;