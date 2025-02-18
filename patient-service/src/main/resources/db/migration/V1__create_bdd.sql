CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE Patients (
      id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
      last_name VARCHAR(70) NOT NULL,
      first_name VARCHAR(70) NOT NULL,
      birthdate DATE NOT NULL,
      gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
      address VARCHAR(150),
      phone VARCHAR(15)
);