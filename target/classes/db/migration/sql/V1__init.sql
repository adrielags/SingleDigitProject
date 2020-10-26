CREATE TABLE usuario (
   id INT AUTO_INCREMENT  PRIMARY KEY,
   nome VARCHAR(500) NOT NULL,
   email VARCHAR(500) NOT NULL
);

CREATE TABLE digitounico (
	  id INT AUTO_INCREMENT  PRIMARY KEY,
	  parametron VARCHAR(250) NOT NULL,
	  parametrok INT NOT NULL,
	  valorcalculado VARCHAR(250) NOT NULL,
	  usuario_id INT,
	  foreign key (usuario_id) references usuario(id)
);

CREATE TABLE chaves (
	id INT AUTO_INCREMENT  PRIMARY KEY,
    chave VARCHAR(500) NOT NULL,
    usuario_id INT NOT NULL,
    foreign key (usuario_id) references usuario(id)
)



