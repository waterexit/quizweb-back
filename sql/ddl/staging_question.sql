CREATE TABLE staging_question (
question_id BIGINT PRIMARY KEY,
quiz_id BIGINT,
num int,
name varchar(1000),
content text,
comment text,
choiceType text
);