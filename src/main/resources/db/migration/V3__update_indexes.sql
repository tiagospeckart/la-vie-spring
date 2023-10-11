-- Find the current maximum ID
SELECT MAX(id) FROM client;

-- Add an offset to the maximum ID (e.g., add 2)
SELECT setval('client_id_seq', (SELECT MAX(id) FROM client) + 2, false);

-- Find the current maximum ID
SELECT MAX(id) FROM psychologist;

-- Add an offset to the maximum ID (e.g., add 2)
SELECT setval('psychologist_id_seq', (SELECT MAX(id) FROM psychologist) + 2, false);

-- Find the current maximum ID
SELECT MAX(id) FROM admin;

-- Add an offset to the maximum ID (e.g., add 2)
SELECT setval('admin_id_seq', (SELECT MAX(id) FROM admin) + 2, false);

-- Find the current maximum ID
SELECT MAX(id) FROM session;

-- Add an offset to the maximum ID (e.g., add 2)
SELECT setval('session_id_seq', (SELECT MAX(id) FROM session) + 2, false);
