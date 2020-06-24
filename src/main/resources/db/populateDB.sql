DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2020-06-22 08:57:00', 'Breakfast', 400),
  (100000, '2020-06-24 12:31:00', 'Lunch', 400),
  (100000, '2020-06-29 15:41:00', 'Dinner', 500),
  (100001, '2020-06-26 15:41:00', 'Dinner', 700),
  (100001, '2020-06-27 21:41:00', 'Late dinner', 1500);

