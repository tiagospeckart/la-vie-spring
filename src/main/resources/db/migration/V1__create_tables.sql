-- Define ENUM types
DO $$ BEGIN
    CREATE TYPE status_enum AS ENUM ('ACTIVE', 'INACTIVE');
    CREATE TYPE session_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'CANCELLED');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- Create the Person table
CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(30),
    status status_enum,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255)
);

-- Create the Client table
CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    address VARCHAR(150),
    observations TEXT,
    FOREIGN KEY (id) REFERENCES person(id)
);

-- Create the Psychologist table
CREATE TABLE IF NOT EXISTS psychologist (
    id SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE,
    specialization_area VARCHAR(100),
    biography TEXT,
    FOREIGN KEY (id) REFERENCES person(id)
);

-- Create the Admin table
CREATE TABLE IF NOT EXISTS admin (
    id SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES person(id)
);

-- Create the Session table
CREATE TABLE IF NOT EXISTS session (
    id SERIAL PRIMARY KEY,
    psychologist_id INT,
    client_id INT,
    date_and_time TIMESTAMP NOT NULL,
    session_notes TEXT,
    session_status session_status_enum,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    FOREIGN KEY (psychologist_id) REFERENCES psychologist(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);
