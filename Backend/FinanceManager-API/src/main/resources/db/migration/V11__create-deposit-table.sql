CREATE TABLE tb_deposit_category(
     id BIGSERIAL PRIMARY KEY NOT NULL,
     name VARCHAR(50) NOT NULL
);

ALTER TABLE tb_transaction
    ADD COLUMN deposit_category_id BIGINT NULL;

ALTER TABLE tb_transaction
    ADD CONSTRAINT fk_deposit_category
    FOREIGN KEY (deposit_category_id)
    REFERENCES tb_deposit_category(id);