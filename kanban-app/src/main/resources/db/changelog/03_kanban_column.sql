ALTER TABLE task
ADD COLUMN kanban_id INTEGER
FOREIGN KEY (kanban_id) REFERENCES kanban(id);