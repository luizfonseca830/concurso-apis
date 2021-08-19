CREATE TABLE schooling (
                               code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO schooling (name) values ('Nível Fundamental');
INSERT INTO schooling (name) values ('Nível Médio');
INSERT INTO schooling (name) values ('Nível Superior');
INSERT INTO schooling (name) values ('Nível Fundamental Incompleto');
INSERT INTO schooling (name) values ('Nível Médio Incompleto');
INSERT INTO schooling (name) values ('Nível Superior Incompleto');

