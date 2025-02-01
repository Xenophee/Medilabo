CREATE SCHEMA IF NOT EXISTS dev;
SET search_path TO dev;



CREATE TABLE Patients (
      id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
      first_name VARCHAR(70) NOT NULL,
      last_name VARCHAR(70) NOT NULL,
      birthdate DATE NOT NULL,
      gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
      address VARCHAR(150),
      phone VARCHAR(15)
);


INSERT INTO Patients (first_name, last_name, birthdate, gender, address, phone)
VALUES ('TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
       ('TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
       ('TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
       ('TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');