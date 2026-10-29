
INSERT INTO setores (nome, sigla) VALUES
                                      ('Tecnologia da Informação', 'TI'),
                                      ('Secretaria Acadêmica', 'SA');

INSERT INTO categorias (nome, descricao, setor_id) VALUES
                                                       ('Computadores', 'Problemas em computadores e periféricos', 1),
                                                       ('Rede e Internet', 'Conectividade e acesso à rede', 1),
                                                       ('Sistemas Acadêmicos', 'Problemas em sistemas acadêmicos', 2),
                                                       ('Documentação', 'Solicitações de documentos acadêmicos', 2);

INSERT INTO usuarios (nome, email, matricula, tipo, setor_id) VALUES
                                                                  ('Ana Solicitante', 'ana@ifrn.edu.br', '2026001', 'SOLICITANTE', NULL),
                                                                  ('Bruno Solicitante', 'bruno@ifrn.edu.br', '2026002', 'SOLICITANTE', NULL),
                                                                  ('Carlos Técnico TI', 'carlos@ifrn.edu.br', '2026003', 'TECNICO', 1),
                                                                  ('Daniela Técnica SA', 'daniela@ifrn.edu.br', '2026004', 'TECNICO', 2);
