CREATE TABLE common_users(

    id BINARY(16) NOT NULL PRIMARY KEY,
    complete_name VARCHAR(150) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL

);