DROP VIEW IF EXISTS vw_dashboard_category_allocation CASCADE;

ALTER TABLE tb_withdraw_category
ALTER COLUMN name TYPE VARCHAR(30);

CREATE VIEW vw_dashboard_category_allocation AS
SELECT
    tw.name as category_name,
    CAST(EXTRACT(MONTH FROM ts.date) AS INTEGER) a_month,
    CAST(EXTRACT(YEAR FROM ts.date) AS INTEGER) a_year,
    SUM(ts.amount) as total
FROM tb_transaction ts
         JOIN tb_withdraw_category tw ON tw.id = ts.withdraw_category_id
WHERE ts.transaction_type = 'WITHDRAW'
GROUP BY tw.name, a_month, a_year;