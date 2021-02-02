CREATE DATABASE db_spaces;

CREATE USER 'db_spaces_user'@'%' identified by 'pass';

GRANT ALL ON db_spaces.* TO 'db_spaces_user'@'%';