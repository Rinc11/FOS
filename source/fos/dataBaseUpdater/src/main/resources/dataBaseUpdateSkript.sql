--#1:
CREATE TABLE "Trip"
(
  "TripID"     INTEGER NOT NULL,
  "VehicleID"  INTEGER NOT NULL,
  "StartTime"  TIMESTAMP,
  "EndTime"    TIMESTAMP,
  "PlaceStart" VARCHAR,
  "PlaceEnd"   VARCHAR,
  "Start_km"   INTEGER,
  "End_km"     INTEGER,
  "Type"       VARCHAR,
  "Username"   VARCHAR NOT NULL,
  CONSTRAINT "PK_TRIP"
  PRIMARY KEY ("VehicleID", "TripID", "Username")
);

CREATE TABLE "Vehicles"
(
  "VehicleID"    INTEGER NOT NULL
    CONSTRAINT "PK_VEHICLES"
    PRIMARY KEY,
  "Serialnumber" INTEGER,
  "Brand"        VARCHAR,
  "Type"         VARCHAR,
  "BuildYear"    INTEGER,
  "FuelType"     VARCHAR
);

ALTER TABLE "Trip"
  ADD CONSTRAINT "FK_TRIP_VEHICLES"
FOREIGN KEY ("VehicleID") REFERENCES "Vehicles";

CREATE TABLE "Refueling"
(
  "RefuelID"  INTEGER NOT NULL,
  "VehicleID" INTEGER NOT NULL
    CONSTRAINT "FK_Refueling_VEHICLEID"
    REFERENCES "Vehicles",
  "Date"      DATE,
  "FuelType"  VARCHAR,
  "Price"     DOUBLE PRECISION,
  "Amount"    DOUBLE PRECISION,
  CONSTRAINT "PK_Refueling"
  PRIMARY KEY ("RefuelID", "VehicleID")
);

CREATE TABLE "Person"
(
  "Username"     VARCHAR NOT NULL
    CONSTRAINT "PK_PERSON"
    PRIMARY KEY,
  "Firstname"    VARCHAR,
  "Lastname"     VARCHAR,
  "AHV"          VARCHAR,
  "Street"       VARCHAR,
  "Place"        VARCHAR,
  "Email"        VARCHAR,
  "Password"     VARCHAR,
  "PasswordHint" VARCHAR,
  "Locked_YN"    BOOLEAN,
  "LoginTry"     INTEGER,
  "Usertype"     VARCHAR,
  "Deleted_YN"   BOOLEAN
);

ALTER TABLE "Trip"
  ADD CONSTRAINT "FK_TRIP_PERSON"
FOREIGN KEY ("Username") REFERENCES "Person";

CREATE TABLE refuels
(
  "VehicleID" INTEGER,
  "Username"  VARCHAR
    CONSTRAINT "FK_REFUELS_PERSON_USERNAME"
    REFERENCES "Person",
  "RefuelID"  INTEGER,
  CONSTRAINT "UC_REFUELS"
  UNIQUE ("VehicleID", "Username", "RefuelID"),
  CONSTRAINT "FK_REFUELS_VEHICLEID_REFUELID"
  FOREIGN KEY ("VehicleID", "RefuelID") REFERENCES "Refueling" ("VehicleID", "RefuelID")
);
-- INSERT PERSON
INSERT INTO fos."Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('suttema2', 'Marco', 'Sutter', '756.1234.5678.90', 'Feldstrasse 1', 'Amriswil', 'suttema2@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'admin', false);
INSERT INTO fos."Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('wipffab', 'Fabian', 'Wipf', '756.1234.5678.90', 'Feldstrasse 1', 'Frauenfeld', 'wipffab@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'admin', false);
INSERT INTO fos."Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('ruegjon', 'Jonas', 'Rüegge', '756.1234.5678.90', 'Feldstrasse 1', 'Romanshorn', 'ruegjon@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'admin', false);
INSERT INTO fos."Person" ("Username", "Firstname", "Lastname", "AHV", "Street", "Place", "Email", "Password", "PasswordHint", "Locked_YN", "LoginTry", "Usertype", "Deleted_YN") VALUES ('mayeret', 'Reto', 'Mayer', '756.1234.5678.90', 'Feldstrasse 1', 'Berg', 'mayeret@students.zhaw.ch', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '1234', false, 0, 'admin', false);

--INSERT VEHICLES

INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1000, 1234-312-32, 'Ford', 'Focus', 2010, 'Benzin');
INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1001, 1234-12-43, 'Fiat', 'Panda', 2011, 'Benzin');
INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1002, 1234-234-21, 'VW', 'Golf', 2014, 'Benzin');
INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1003, 1234-123-23, 'BMW', 'X3', 2006, 'Diesel');
INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1004, 1234-123-65, 'Ford', 'Fiesta', 2004, 'Benzin');
INSERT INTO fos."Vehicles" ("VehicleID", "Serialnumber", "Brand", "Type", "BuildYear", "FuelType") VALUES (1005, 1234-123-54, 'OPEL', 'Astra', 2014, 'Benzin');

-- INSERT TRIP

INSERT INTO fos."Trip" ("VehicleID", "TripID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (1000, 10000, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'privat', 'suttema2');
INSERT INTO fos."Trip" ("VehicleID", "TripID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (1001, 10001, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'geschäftlich', 'wipffab');
INSERT INTO fos."Trip" ("VehicleID", "TripID", "StartTime", "EndTime", "PlaceStart", "PlaceEnd", "Start_km", "End_km", "Type", "Username") VALUES (1004, 10002, '2018-03-08 13:04:03.614000', '2018-03-09 13:04:10.340000', 'Frauenfeld', 'Winterthur', 100, 130, 'privat', 'mayeret');

--INSERT REFUELING

INSERT INTO fos."Refueling" ("RefuelID", "VehicleID", "Date", "FuelType", "Price", "Amount") VALUES (1000, 1000, '2018-03-08', 'Benzin', 90.55, 40);
INSERT INTO fos."Refueling" ("RefuelID", "VehicleID", "Date", "FuelType", "Price", "Amount") VALUES (1001, 1002, '2018-01-02', 'Diesel', 70.55, 34);
INSERT INTO fos."Refueling" ("RefuelID", "VehicleID", "Date", "FuelType", "Price", "Amount") VALUES (1002, 1001, '2018-02-03', 'Diesel', 40.55, 22);
INSERT INTO fos."Refueling" ("RefuelID", "VehicleID", "Date", "FuelType", "Price", "Amount") VALUES (1003, 1003, '2018-03-04', 'Benzin', 50.55, 32);

-- INSERT REFUELS

INSERT INTO fos.refuels ("VehicleID", "Username", "RefuelID") VALUES (1000, 'suttema2', 1000);
INSERT INTO fos.refuels ("VehicleID", "Username", "RefuelID") VALUES (1001, 'suttema2', 1002);
INSERT INTO fos.refuels ("VehicleID", "Username", "RefuelID") VALUES (1002, 'mayeret', 1001);
INSERT INTO fos.refuels ("VehicleID", "Username", "RefuelID") VALUES (1003, 'ruegjon', 1003);
