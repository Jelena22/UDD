INSERT INTO roles (name) VALUES ('ROLE_ADMIN'); --1
INSERT INTO roles (name) VALUES ('ROLE_REGULAR_USER'); --2

--sifra Mica123
INSERT INTO USERS (email, password, name, surname, role_id, salt) VALUES
    ('milicamilic@gmail.com', '$2a$12$ZnU7Mu29APtTX9fRWYxLcO96SirQPZS3jKYA.Dx3Dn8y3BY89p8EG', 'Milica', 'Milic', 1, '04T4B6QJnfRh+IxEZWaBew==');
