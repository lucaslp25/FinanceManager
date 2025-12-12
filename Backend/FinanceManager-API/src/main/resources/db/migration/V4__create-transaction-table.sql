CREATE TABLE tb_transaction(
   id VARCHAR(36) NOT NULL,
   amount DECIMAL(10,2) NOT NULL,
   description VARCHAR(150) NULL,
   transaction_type VARCHAR(15) NOT NULL,
   withdraw_category_id VARCHAR(50) NULL,

   CONSTRAINT fk_withdraw_category FOREIGN KEY (withdraw_category_id) REFERENCES tb_withdraw_category(id)
);
