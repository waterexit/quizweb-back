CREATE TABLE staging_choice (
choice_id BIGINT PRIMARY KEY,
quiz_id BIGINT,
question_id BIGINT,
selection_no INT,
content TEXT,
correct_flg BOOLEAN
) 