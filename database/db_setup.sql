-- A script to make a fresh db
-- Please, be careful, as it drops all the current data

DROP DATABASE IF EXISTS http_server;

CREATE DATABASE http_server CHARACTER SET utf8 COLLATE utf8_general_ci;
USE http_server;

GRANT USAGE ON *.* TO 'http_server'@'localhost';
GRANT ALL ON TABLE http_server.* TO 'http_server'@'localhost';

CREATE TABLE requests(
	id INT NOT NULL AUTO_INCREMENT,
	src_ip VARCHAR(20) NOT NULL,
	uri VARCHAR(50),
	time DATETIME NOT NULL,
	sent_bytes INT NOT NULL DEFAULT 0,
	received_bytes INT NOT NULL DEFAULT 0,
	time_stamp TIMESTAMP NOT NULL,
	url_redirects VARCHAR(50) NOT NULL DEFAULT 'empty',
	CONSTRAINT requests_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

