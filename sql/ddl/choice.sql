CREATE TABLE choice (id BIGINT AUTO_INCREMENT PRIMARY KEY,
question_id BIGINT,
quiz_id BIGINT,
selection_no INT,
content TEXT,
correct_flg BOOLEAN) AUTO_INCREMENT = 20;