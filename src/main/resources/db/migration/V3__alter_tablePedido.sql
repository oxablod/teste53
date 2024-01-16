ALTER TABLE teste53.pedidos ADD PRIMARY KEY (id);
ALTER TABLE teste53.pedidos MODIFY id INT AUTO_INCREMENT FIRST;
ALTER TABLE teste53.pedidos ADD COLUMN nr_controle int not null;