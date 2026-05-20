CREATE DATABASE IF NOT EXISTS controle_treino;
USE controle_treino;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    peso DECIMAL(5,2),
    altura DECIMAL(4,2),
    meta VARCHAR(50)
);

CREATE TABLE grupo_muscular (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE exercicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    series INT NOT NULL,
    repeticoes INT NOT NULL,
    grupo_muscular_id INT NOT NULL,
    FOREIGN KEY (grupo_muscular_id) REFERENCES grupo_muscular(id)
);

CREATE TABLE treino (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE execucao_treino (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_execucao DATE NOT NULL,
    carga DECIMAL(6,2) NOT NULL,
    observacao TEXT,
    exercicio_id INT NOT NULL,
    treino_id INT NOT NULL,
    FOREIGN KEY (exercicio_id) REFERENCES exercicio(id),
    FOREIGN KEY (treino_id) REFERENCES treino(id)
);