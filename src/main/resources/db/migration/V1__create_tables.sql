-- Create the Client table with columns from Person
CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(30),
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    date_of_birth DATE NOT NULL,
    address VARCHAR(150),
    observations TEXT
);

-- Create the Psychologist table with columns from Person
CREATE TABLE IF NOT EXISTS psychologist (
    id SERIAL PRIMARY KEY,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(30),
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE,
    specialization_area VARCHAR(100),
    biography TEXT
);

-- Create the Admin table with columns from Person
CREATE TABLE IF NOT EXISTS admin (
    id SERIAL PRIMARY KEY,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(30),
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    password VARCHAR(255) NOT NULL
);

-- Create the Session table
CREATE TABLE IF NOT EXISTS session (
    id SERIAL PRIMARY KEY,
    psychologist_id INT,
    client_id INT,
    date_and_time TIMESTAMP NOT NULL,
    session_notes TEXT,
    session_status VARCHAR(15),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    FOREIGN KEY (psychologist_id) REFERENCES psychologist(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);
