DROP TABLE IF EXISTS users_folders;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS folders;

-- Table: users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(128) NOT NULL DEFAULT 'USER'
);
-- Table: folders
CREATE TABLE folders (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
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
    user_id int NOT NULL,
    folder_id int,
    title VARCHAR(128),
    content TEXT NOT NULL,
    audio_path TEXT NOT NULL,
    audio_length REAL NOT NULL,
    saved BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_notes_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_notes_folder_id FOREIGN KEY(folder_id) REFERENCES folders(id)
);