DROP TABLE IF EXISTS user_task CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_rol VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    phone_number INT NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE tasks (
    task_id SERIAL PRIMARY KEY,
    task_name VARCHAR(100) NOT NULL,
    task_description VARCHAR(200) NOT NULL
);

CREATE TABLE user_task (
    user_task_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    task_id INT NOT NULL,
    creation_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    colaborators VARCHAR(100) NOT NULL,

    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_task_id FOREIGN KEY (task_id) REFERENCES tasks(task_id)
);