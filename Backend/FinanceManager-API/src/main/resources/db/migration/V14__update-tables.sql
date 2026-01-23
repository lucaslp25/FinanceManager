ALTER TABLE tb_withdraw_category
    ADD COLUMN user_id BIGINT;

ALTER TABLE tb_withdraw_category
    ADD CONSTRAINT fk_withdraw_category_user
        FOREIGN KEY (user_id) REFERENCES tb_user(id);

ALTER TABLE tb_deposit_category
    ADD COLUMN user_id BIGINT;

ALTER TABLE tb_deposit_category
    ADD CONSTRAINT fk_deposit_category_user
        FOREIGN KEY (user_id) REFERENCES tb_user(id);

DELETE FROM tb_transaction WHERE withdraw_category_id IN (SELECT id FROM tb_withdraw_category WHERE user_id IS NULL);
DELETE FROM tb_withdraw_category WHERE user_id IS NULL;

DELETE FROM tb_transaction WHERE deposit_category_id IN (SELECT id FROM tb_deposit_category WHERE user_id IS NULL);
DELETE FROM tb_deposit_category WHERE user_id IS NULL;

DROP VIEW IF EXISTS vw_dashboard_category_allocation;
DROP VIEW IF EXISTS vw_dashboard_summary;

CREATE VIEW vw_dashboard_category_allocation AS
SELECT
    c.name AS category_name,
    EXTRACT(MONTH FROM t.date)::INTEGER AS a_month,
    EXTRACT(YEAR FROM t.date)::INTEGER AS a_year,
    SUM(t.amount) AS total,
    t.user_id AS user_id
FROM tb_transaction t
         JOIN tb_withdraw_category c ON t.withdraw_category_id = c.id
WHERE t.transaction_type = 'WITHDRAW'
GROUP BY c.name, EXTRACT(MONTH FROM t.date), EXTRACT(YEAR FROM t.date), t.user_id;

CREATE VIEW vw_dashboard_summary AS
SELECT
    EXTRACT(MONTH FROM t.date)::INTEGER AS mes,
    EXTRACT(YEAR FROM t.date)::INTEGER AS ano,
    SUM(CASE WHEN t.transaction_type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS total_deposit,
    SUM(CASE WHEN t.transaction_type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS total_withdraw,
    t.user_id AS user_id
FROM tb_transaction t
GROUP BY EXTRACT(MONTH FROM t.date), EXTRACT(YEAR FROM t.date), t.user_id;