CREATE ROLE kanban WITH LOGIN PASSWORD 'kanban';
ALTER USER kanban CREATEDB;

\c postgres kanban
CREATE DATABASE kanban;
