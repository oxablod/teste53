ALTER TABLE teste53.pedidos ADD COLUMN vl_total decimal(6,2) not null;

CREATE TABLE teste53.pessoa
(
    id_pessoa 	int4 auto_increment primary key,
    nome_pessoa	varchar(160)
);
ALTER TABLE teste53.pessoa AUTO_INCREMENT=1;
insert into teste53.pessoa (nome_pessoa) values
                                             ('Pessoa 1'),
                                             ('Pessoa 2'),
                                             ('Pessoa 3'),
                                             ('Pessoa 4'),
                                             ('Pessoa 5'),
                                             ('Pessoa 6'),
                                             ('Pessoa 7'),
                                             ('Pessoa 8'),
                                             ('Pessoa 9'),
                                             ('Pessoa 10');

ALTER TABLE teste53.pedidos
    ADD FOREIGN KEY (codigo_cliente) REFERENCES teste53.pessoa(id_pessoa);