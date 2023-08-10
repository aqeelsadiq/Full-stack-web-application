-- Database
CREATE DATABASE IF NOT EXISTS FullStackWeb;

USE FullStackWeb;

-- Tables 

CREATE TABLE Household (
    email varchar(250) NOT NULL,
    household_type varchar(11) NOT NULL,
    heating int,
    cooling int,
    square_footage int NOT NULL,
    postal_code varchar(5) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE PostalCode (
    postal_code varchar(5) NOT NULL,
    city varchar(30) NOT NULL,
    state varchar(30) NOT NULL,
    latitude DECIMAL(15,10) NOT NULL,
    longitude DECIMAL(15,10) NOT NULL,
    PRIMARY KEY (postal_code)
);

CREATE TABLE PublicUtilities (
    email varchar(250) NOT NULL,
    type varchar(8) NOT NULL,
    PRIMARY KEY (email,type)
);

CREATE TABLE PowerGenerator (
    email varchar(250) NOT NULL,
    power_generator_num int NOT NULL,
    monthly_kwh int NOT NULL,
    storage_kwh int,
    generation_type varchar(14) NOT NULL,
    PRIMARY KEY (email,power_generator_num)
);

CREATE TABLE Appliance (
    email VARCHAR(250) NOT NULL,
    appliance_num int NOT NULL,
    btu int NOT NULL,
    model_name VARCHAR(50),
    manufacturer_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE WaterHeater (
    email varchar(250) NOT NULL,
    appliance_num int NOT NULL,
    energy_source varchar(11) NOT NULL,
    capacity DECIMAL(10,1) NOT NULL,
    current_temperature_setting int,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE AirHandler (
    email varchar(250) NOT NULL,
    appliance_num int NOT NULL,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE AirConditioner (
    email varchar(250) NOT NULL,
    appliance_num int NOT NULL,
    EER DECIMAL(3,1) NOT NULL,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE Heater (
    email varchar(250) NOT NULL,
    appliance_num int NOT NULL,
    energy_source varchar(8) NOT NULL,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE HeatPump (
    email varchar(250) NOT NULL,
    appliance_num int NOT NULL,
    SEER DECIMAL(3,1) NOT NULL, 
    HSPF DECIMAL(3,1) NOT NULL,
    PRIMARY KEY (email,appliance_num)
);

CREATE TABLE Manufacturer (
    manufacturer_name varchar(100) NOT NULL,
    PRIMARY KEY (manufacturer_name)
);


-- Constraints   Foreign Keys: FK_ChildTable_childColumn_ParentTable_parentColumn

ALTER TABLE Household
  ADD CONSTRAINT fk_Household_postalcode_PostalCode_postalcode FOREIGN KEY (postal_code) REFERENCES PostalCode (postal_code) ON UPDATE CASCADE;

ALTER TABLE PublicUtilities
  ADD CONSTRAINT fk_PublicUtilities_email_Household_email FOREIGN KEY (email) REFERENCES Household (email) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE PowerGenerator
  ADD CONSTRAINT fk_PowerGenerator_email_Household_email FOREIGN KEY (email) REFERENCES Household (email) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Appliance
  ADD CONSTRAINT fk_Appliance_email_Household_email FOREIGN KEY (email) REFERENCES Household (email) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_Appliance_manufacturername_Manufacturer_manufacturername FOREIGN KEY (manufacturer_name) REFERENCES Manufacturer (manufacturer_name) ON UPDATE CASCADE;

ALTER TABLE WaterHeater
  ADD CONSTRAINT fk_WaterHeater_email_appliancenum_Appliance_email_appliancenum FOREIGN KEY (email,appliance_num) REFERENCES Appliance (email,appliance_num) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE AirHandler
  ADD CONSTRAINT fk_AirHandler_email_appliancenum_Appliance_email_appliancenum FOREIGN KEY (email,appliance_num) REFERENCES Appliance (email,appliance_num) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE AirConditioner
  ADD CONSTRAINT fk_AC_email_appliancenum_AirHandler_email_appliancenum FOREIGN KEY (email,appliance_num) REFERENCES AirHandler (email,appliance_num) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Heater
  ADD CONSTRAINT fk_Heater_email_appliancenum_AirHandler_email_appliancenum FOREIGN KEY (email,appliance_num) REFERENCES AirHandler (email,appliance_num) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE HeatPump
  ADD CONSTRAINT fk_HeatPump_email_appliancenum_AirHandler_email_appliancenum FOREIGN KEY (email,appliance_num) REFERENCES AirHandler (email,appliance_num) ON DELETE CASCADE ON UPDATE CASCADE;