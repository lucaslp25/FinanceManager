CREATE TABLE tb_wallet(
    id BIGSERIAL PRIMARY KEY,
    balance DECIMAL(10,2) NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
