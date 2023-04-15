CREATE TABLE tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag varchar(15),
    num BIGINT DEFAULT 0
);
INSERT INTO tag (id,tag,num) VALUES (1,"進撃の巨人",2);