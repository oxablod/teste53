CREATE TABLE teste53.pedidos (
                    id                integer not null,
                    data_cadastro     datetime null ,
                    nome_produto      varchar(250) not null,
                    vl_produto        decimal(6,2) not null,
                    quantidade        int null,
                    codigo_cliente    int not null
);
SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;