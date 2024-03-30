-- Insert random data into users table
INSERT INTO users (first_name, last_name, email, password)
SELECT
    CONCAT('First', id),
    CONCAT('Last', id),
    CONCAT('email', id, '@example.com'),
    CONCAT('password', id)
FROM generate_series(1, 10) as id;

-- Insert random data into folders table
INSERT INTO folders (name)
SELECT
    CONCAT('Folder', id)
FROM generate_series(1, 5) as id;

-- Insert random data into users_folders table
-- Insert random data into users_folders table
INSERT INTO users_folders (user_id, folder_id)
SELECT user_id, folder_id
FROM (
    SELECT u.id AS user_id, f.id AS folder_id
    FROM users u
    CROSS JOIN folders f
    ORDER BY random()
) AS sub
LIMIT 15;

-- Insert random data into notes table
INSERT INTO notes (user_id, folder_id, content, audio_data, audio_length, saved)
SELECT
    (SELECT id FROM users ORDER BY random() LIMIT 1),
    (SELECT id FROM folders ORDER BY random() LIMIT 1),
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    null, -- Generate random binary data if needed
    random() * 100, -- Random audio length
    CASE WHEN random() < 0.5 THEN TRUE ELSE FALSE END -- Random boolean for saved column
FROM generate_series(1, 20);