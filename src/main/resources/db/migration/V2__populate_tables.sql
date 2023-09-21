-- Populate the 'client' table with 2 records
INSERT INTO client (id, email, phone, status, created_at, created_by, updated_at, date_of_birth, address, observations) VALUES
(1, 'client1@email.com', '9999-7891', 'ACTIVE', NOW(), 'Flyway', NOW(), '1980-05-25', '100 Elm St.', 'Client 1 details'),
(2, 'client2@email.com', '9999-7892', 'ACTIVE', NOW(), 'Flyway', NOW(), '1985-11-20', '101 Oak St.', 'Client 2 details');

-- Populate the 'psychologist' table with 2 records
INSERT INTO psychologist (id, email, phone, status, created_at, created_by, updated_at, password, license_number, specialization_area, biography) VALUES
(1, 'psychologist1@email.com', '9999-7893', 'INACTIVE', NOW(), 'Flyway', NOW(), 'psypass1', 'license001', 'Clinical', 'Bio for Psychologist 1'),
(2, 'psychologist2@email.com', '9999-7894', 'ACTIVE', NOW(), 'Flyway', NOW(), 'psypass2', 'license002', 'Child Psychology', 'Bio for Psychologist 2');

-- Populate the 'admin' table with 2 records
INSERT INTO admin (id, email, phone, status, created_at, created_by, updated_at, password) VALUES
(1, 'admin1@email.com', '9999-7895', 'ACTIVE', NOW(), 'Flyway', NOW(), 'adminpass1'),
(2, 'admin2@email.com', '9999-7896', 'INACTIVE', NOW(),'Flyway', NOW(), 'adminpass2');

-- Populate the 'session' table with 4 records
-- Two sessions for each client, each with a different psychologist
INSERT INTO session (psychologist_id, client_id, date_and_time, session_notes, session_status, created_at, created_by, updated_at) VALUES
(1, 1, '2023-10-10 10:00:00', 'Initial session with Client 1 and Psychologist 1', 'ACTIVE', NOW(), 'Flyway', NOW()),
(2, 1, '2023-10-17 10:00:00', 'Second session with Client 1 and Psychologist 2', 'ACTIVE', NOW(), 'Flyway', NOW()),
(1, 2, '2023-10-11 11:00:00', 'Initial session with Client 2 and Psychologist 1', 'ACTIVE', NOW(), 'Flyway', NOW()),
(2, 2, '2023-10-18 11:00:00', 'Second session with Client 2 and Psychologist 2', 'ACTIVE', NOW(), 'Flyway', NOW());
