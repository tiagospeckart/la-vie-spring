-- Populate the 'person' table with 6 records
INSERT INTO person (email, phone, status, created_at, updated_at) VALUES
('person1@email.com', '9999-7891', 'ACTIVE', NOW(), NOW()),
('person2@email.com', '9999-7892', 'ACTIVE', NOW(), NOW()),
('person3@email.com', '9999-7893', 'INACTIVE', NOW(), NOW()),
('person4@email.com', '9999-7894', 'ACTIVE', NOW(), NOW()),
('person5@email.com', '9999-7895', 'ACTIVE', NOW(), NOW()),
('person6@email.com', '9999-7896', 'INACTIVE', NOW(), NOW());

-- Populate the 'client' table with 2 records (using persons 1 and 2)
INSERT INTO client (id, date_of_birth, address, observations) VALUES
(1, '1980-05-25', '100 Elm St.', 'Client 1 details'),
(2, '1985-11-20', '101 Oak St.', 'Client 2 details');

-- Populate the 'psychologist' table with 2 records (using persons 3 and 4)
INSERT INTO psychologist (id, password, license_number, specialization_area, biography) VALUES
(3, 'psypass1', 'license001', 'Clinical', 'Bio for Psychologist 1'),
(4, 'psypass2', 'license002', 'Child Psychology', 'Bio for Psychologist 2');

-- Populate the 'admin' table with 2 records (using persons 5 and 6)
INSERT INTO admin (id, password) VALUES
(5, 'adminpass1'),
(6, 'adminpass2');

-- Populate the 'session' table with 4 records
-- Two sessions for each client, each with a different psychologist
INSERT INTO session (psychologist_id, client_id, date_and_time, session_notes, session_status, created_at, updated_at) VALUES
(3, 1, '2023-10-10 10:00:00', 'Initial session with Client 1 and Psychologist 1', 'ACTIVE', NOW(), NOW()),
(4, 1, '2023-10-17 10:00:00', 'Second session with Client 1 and Psychologist 2', 'ACTIVE', NOW(), NOW()),
(3, 2, '2023-10-11 11:00:00', 'Initial session with Client 2 and Psychologist 1', 'ACTIVE', NOW(), NOW()),
(4, 2, '2023-10-18 11:00:00', 'Second session with Client 2 and Psychologist 2', 'ACTIVE', NOW(), NOW());