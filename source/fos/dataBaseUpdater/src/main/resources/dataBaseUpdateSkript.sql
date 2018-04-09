--#1:
CREATE TYPE ENUM_TRIP_TYPE AS ENUM ('privat', 'geschäftlich');
CREATE TYPE ENUM_VEHICLES_FUELTYPE AS ENUM ('Benzin', 'Diesel', 'Strom', 'Erdgas');
CREATE TYPE ENUM_PERSON_USERTYPE AS ENUM ('Admin', 'Mitarbeiter');

CREATE TABLE "Trip"
(
  "TripID"     SERIAL,
  "VehicleID"  SERIAL NOT NULL,
  "StartTime"  TIMESTAMP NOT NULL,
  "EndTime"    TIMESTAMP,
  "PlaceStart" VARCHAR NOT NULL,
  "PlaceEnd"   VARCHAR,
  "Start_km"   INTEGER NOT NULL,
  "End_km"     INTEGER,
  "Type"       ENUM_TRIP_TYPE NOT NULL,
  "Username"   VARCHAR NOT NULL,
  CONSTRAINT "PK_TRIP"
  PRIMARY KEY ("VehicleID", "TripID", "Username")
);

CREATE TABLE "Vehicles"
(
  "VehicleID"    SERIAL
    CONSTRAINT "PK_VEHICLES"
    PRIMARY KEY,
  "Serialnumber" VARCHAR NOT NULL,
  "Brand"        VARCHAR DEFAULT '',
  "Type"         VARCHAR DEFAULT '',
  "BuildYear"    INTEGER NOT NULL,
  "FuelType"     ENUM_VEHICLES_FUELTYPE NOT NULL,
  "Active_YN"    BOOLEAN DEFAULT TRUE
);

ALTER TABLE "Trip"
  ADD CONSTRAINT "FK_TRIP_VEHICLES"
FOREIGN KEY ("VehicleID") REFERENCES "Vehicles";


CREATE TABLE "Person"
(
  "Username"     VARCHAR NOT NULL
    CONSTRAINT "PK_PERSON"
    PRIMARY KEY,
  "Firstname"    VARCHAR NOT NULL,
  "Lastname"     VARCHAR NOT NULL,
  "AHV"          VARCHAR DEFAULT '',
  "Street"       VARCHAR DEFAULT '',
  "Place"        VARCHAR DEFAULT '',
  "Email"        VARCHAR DEFAULT '',
  "Password"     VARCHAR NOT NULL,
  "PasswordHint" VARCHAR DEFAULT '',
  "Locked_YN"    BOOLEAN DEFAULT FALSE,
  "LoginTry"     INTEGER DEFAULT 0,
  "Usertype"     ENUM_PERSON_USERTYPE DEFAULT 'Mitarbeiter',
  "Deleted_YN"   BOOLEAN DEFAULT FALSE
);

ALTER TABLE "Trip"
  ADD CONSTRAINT "FK_TRIP_PERSON"
FOREIGN KEY ("Username") REFERENCES "Person";

--#2:
-- INSERT PERSON
INSERT INTO "Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('suttema2', 'Marco', 'Sutter', '756.1234.5678.90', 'Feldstrasse 1', 'Amriswil', 'suttema2@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'Admin', false);
INSERT INTO "Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('wipffab', 'Fabian', 'Wipf', '756.1234.5678.90', 'Feldstrasse 1', 'Frauenfeld', 'wipffab@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'Admin', false);
INSERT INTO "Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('ruegjon', 'Jonas', 'Rüegge', '756.1234.5678.90', 'Feldstrasse 1', 'Romanshorn', 'ruegjon@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'Admin', false);
INSERT INTO "Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('mayerret', 'Reto', 'Mayer', '756.1234.5678.90', 'Feldstrasse 1', 'Berg', 'mayeret@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'Admin', false);

--INSERT VEHICLES

INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-312-32, 'Ford', 'Focus', 2010, 'Benzin');
INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-12-43, 'Fiat', 'Panda', 2011, 'Benzin');
INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-234-21, 'VW', 'Golf', 2014, 'Benzin');
INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-123-23, 'BMW', 'X3', 2006, 'Diesel');
INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-123-65, 'Ford', 'Fiesta', 2004, 'Erdgas');
INSERT INTO "Vehicles" ("Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1234-123-54, 'OPEL', 'Astra', 2014, 'Benzin');

-- INSERT TRIP

INSERT INTO "Trip" ("VehicleID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (1, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'privat', 'suttema2');
INSERT INTO "Trip" ("VehicleID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (2, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'geschäftlich', 'wipffab');
INSERT INTO "Trip" ("VehicleID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (3, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'privat', 'mayerret');

--#3:test
-- Benutzer für Tests
INSERT INTO "Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('testUser', 'Hans', 'Test', '756.1234.5678.90', 'Teststrasse 1', 'Testdorf', 'test.user@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'Mitarbeiter', false);
