DROP TABLE IF EXISTS users_folders;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS folders;

-- Table: users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128),
    last_name VARCHAR(128),
    email VARCHAR(128),
    password VARCHAR(128),
    role VARCHAR(128)
);
-- Table: folders
CREATE TABLE folders (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128)
);
-- Table: users_folders
CREATE TABLE users_folders (
    user_id int,
    folder_id int,
    PRIMARY KEY(user_id, folder_id),
    CONSTRAINT fk_folders_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_folder_id FOREIGN KEY(folder_id) REFERENCES folders(id)
);
-- Table: notes
CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    user_id int,
    folder_id int,
    content TEXT,
    audio_data BYTEA,
    audio_length REAL,
    saved BOOLEAN,
    CONSTRAINT fk_notes_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_notes_folder_id FOREIGN KEY(folder_id) REFERENCES folders(id)
);