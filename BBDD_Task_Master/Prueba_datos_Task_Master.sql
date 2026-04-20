INSERT INTO users( user_id, user_nick_name, user_rol, name, surname, email, phone_number, password)VALUES
	(1, 'alejoputoamo', 'admin', 'alejandro', 'millan', 'alejo@gmail.com', '701431234', 'qwer1234AS@#$');

INSERT INTO tasks(task_id, task_name, task_description)VALUES
	(1, 'hacer la compra ', 'hahahahahah');

INSERT INTO user_task( user_task_id, user_id,  task_id, creation_date, expiration_date, colaborators)VALUES
	(1, 1, 1, '2026-05-10', '2026-05-11', 'adamPDA');