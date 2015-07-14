--Schema for a mysql database to work with the spring mvc-security learning web app

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  ROLE VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (ROLE,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO users(username,password,enabled)
VALUES ('hedges_user','$2a$10$OaTvoZzxlL3ZfdnQZDd2kOt6642cwhW5qlC6xKunzAeOEgFPUX9eO', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('hedges_admin','$2a$10$OaTvoZzxlL3ZfdnQZDd2kOt6642cwhW5qlC6xKunzAeOEgFPUX9eO', TRUE);
 
INSERT INTO user_roles (username, ROLE)
VALUES ('hedges_user', 'ROLE_user');
INSERT INTO user_roles (username, ROLE)
VALUES ('hedges_admin', 'ROLE_user');
INSERT INTO user_roles (username, ROLE)
VALUES ('hedges_admin', 'ROLE_admin');
