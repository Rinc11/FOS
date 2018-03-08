--#1:
CREATE TABLE "Trip"
(
  "VehicleID"  INTEGER NOT NULL,
  "TripID"     INTEGER NOT NULL,
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

