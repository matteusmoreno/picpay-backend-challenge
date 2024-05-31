CREATE TABLE common_users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    complete_name VARCHAR(150) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(19,2)
);

CREATE TABLE shopkeepers (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    complete_name VARCHAR(150) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(19,2)
);

CREATE TABLE transfers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    payer_id BIGINT NOT NULL,
    payee_id BIGINT NOT NULL,
    value DECIMAL(19,2) NOT NULL,
    CONSTRAINT fk_payer
        FOREIGN KEY (payer_id) REFERENCES common_users(id),
    CONSTRAINT fk_payee
        FOREIGN KEY (payee_id) REFERENCES shopkeepers(id)
);

