DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, dateTime, calories,  user_id) VALUES
  ('Завтрак','2011-05-16 15:36:38', 234,100000),
  ('Обед','2012-05-16 15:36:38', 34545,100000),
  ('Ужин','2013-05-16 15:36:38', 345,100000),
  ('Завтрак','2014-05-16 15:36:38', 567,100000),
  ('Обед','2015-05-16 15:36:38', 6786,100000),
  ('Ужин','2016-05-16 15:36:38', 3464,100001);
