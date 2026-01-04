CREATE TABLE tb_goal(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
    target_amount DECIMAL(10,2) NOT NULL,
    deadline TIMESTAMP NULL,
    init_date TIMESTAMP NOT NULL,

    user_id BIGINT NOT NULL,

    CONSTRAINT fk_goals_userId FOREIGN KEY (user_id) REFERENCES tb_user(id)
);