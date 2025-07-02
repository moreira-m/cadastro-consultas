-- Tabela de login
DROP TABLE IF EXISTS login;
CREATE TABLE login (
    user_id INTEGER PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela de clientes
DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
    client_id INTEGER PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(255)
);

-- Tabela de consulta
DROP TABLE IF EXISTS consulta;
CREATE TABLE consulta (
    consulta_id INTEGER PRIMARY KEY,
    client_id INT NOT NULL,
    data DATE NOT NULL,
    horario TIME NOT NULL,
    medico VARCHAR(255),
    observacoes TEXT,
    FOREIGN KEY (client_id) REFERENCES clientes(client_id)
);


