CREATE TABLE tb_user(
    Id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60) NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    role VARCHAR(15) NOT NULL
);