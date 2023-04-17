CREATE TABLE staging_question (
question_id BIGINT PRIMARY KEY,
quiz_id BIGINT,
num int,
content text,
comment text,
choiceType text
);