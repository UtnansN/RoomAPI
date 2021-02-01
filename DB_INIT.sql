CREATE DATABASE db_rooms;

CREATE USER 'db_rooms_user'@'%' identified by 'pass';

GRANT ALL ON db_rooms.* TO 'db_rooms_user'@'%';