ALTER TABLE tb_goal
    ADD COLUMN current_amount DECIMAL(10, 2) NULL;

ALTER TABLE tb_goal
    ADD COLUMN goal_status VARCHAR(30) NULL;

ALTER TABLE tb_goal
    ADD COLUMN goal_priority VARCHAR(30) NULL;

