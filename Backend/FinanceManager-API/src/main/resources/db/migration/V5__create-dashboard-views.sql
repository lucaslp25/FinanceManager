CREATE VIEW vw_dashboard_category_allocation AS
    -- Query for take the total value per category
SELECT
    SUM(ts.amount) Total,
    tw.name
FROM tb_transaction ts
    JOIN tb_withdraw_category tw ON tw.id = ts.withdraw_category_id
WHERE ts.transaction_type = 'WITHDRAW'
GROUP BY tw.name
ORDER BY Total DESC;

        -- \\ --

CREATE VIEW vw_dashboard_summary AS
    -- Query for KPI cards
SELECT
    EXTRACT (MONTH FROM ts.date) as mes,
    EXTRACT (YEAR FROM ts.date) as ano,
    SUM(CASE WHEN ts.transaction_type = 'DEPOSIT' THEN ts.amount ELSE 0 END) total_deposit,
    SUM(CASE WHEN ts.transaction_type = 'WITHDRAW' THEN ts.amount ELSE 0 END) total_withdraw
FROM tb_transaction ts
GROUP BY mes, ano;