-- ***************************************************************************
-- HaiNa Confidential
-- 
-- OCO Source Materials
-- 
-- HaiNa Products: Spindle Monitoring Platform 
--
-- (C) Copyright HaiNa. 2015
-- 
-- The source code for this program is not published or otherwise divested of
-- its trade secrets, irrespective of what has been deposited with the China
-- Copyright Office.
-- ***************************************************************************

DROP TABLE  IF EXISTS  version;
DROP TABLE  IF EXISTS  USERS;
DROP TABLE  IF EXISTS  ROLES;
DROP TABLE  IF EXISTS  CODEMAPPINGS;
DROP TABLE  IF EXISTS  CRAFTPARAMS;
DROP TABLE  IF EXISTS  SPINDLEDATA;
DROP TABLE  IF EXISTS  STATIONDATA;
DROP TABLE  IF EXISTS  STATISDATA;
DROP TABLE  IF EXISTS  SHIFTS;
DROP TABLE  IF EXISTS  SPINDLES;
DROP TABLE  IF EXISTS  MONITORS;
DROP TABLE  IF EXISTS  STATIONS;
DROP TABLE  IF EXISTS  NAMEVALUES;

-- tables

CREATE TABLE STATIONS
    (id INT not null AUTO_INCREMENT, name VARCHAR(50) , description VARCHAR(50), status VARCHAR(20) NOT NULL DEFAULT 'Active', NO INT ,
    PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
    
CREATE TABLE MONITORS
    (id  INT not null AUTO_INCREMENT , monitorId INT, name VARCHAR(50) , description VARCHAR(50)  DEFAULT 'WireLess', stationId INT, model VARCHAR(30), status VARCHAR(20) NOT NULL DEFAULT 'Active', protocol VARCHAR(50) , ip VARCHAR(50), port INT, 
    FOREIGN KEY(stationId) REFERENCES STATIONS(id) ON DELETE CASCADE,
    UNIQUE (id, stationId),
    PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
    
CREATE TABLE SPINDLES
   (id INT not null AUTO_INCREMENT, name VARCHAR(50) , description VARCHAR(50), stationId INT,
   FOREIGN KEY(stationId) REFERENCES STATIONS(id) ON DELETE CASCADE,
   PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
   
CREATE TABLE SHIFTS
   (id INT not null AUTO_INCREMENT, name VARCHAR(50) NOT NULL,  description VARCHAR(50),
   UNIQUE (name),
   PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
   
CREATE TABLE STATIONDATA
   (id BIGINT not null AUTO_INCREMENT, batchNO BIGINT default 1, stationId INT, shiftId INT, brokenSpindles INT, instantBrokenHeads INT, emptySpindles INT default 0,creepSpindles INT default 0,twist FLOAT, draftTimes FLOAT, powerKW FLOAT, eneryKWH FLOAT, productionEfficiency FLOAT, frontRollerSpeed INT, brokenEndsPer1000Spindles INT,  avgSpindleSpeed INT, 
   realTimeProduction FLOAT, grossProductionByShift FLOAT, createdOn TIMESTAMP DEFAULT current_timestamp ,startTime DATETIME, endTime DATETIME, doffTime DATETIME , warning VARCHAR(128), valid BOOLEAN DEFAULT true, doffBorkenEnds INT, accumulatedBrokenEnds INT, stationStatus VARCHAR(20), isBeforeDoff BOOLEAN , attr1 VARCHAR(128), attr2 DATETIME, attr3 DATETIME, attr4 INT, attr5 BOOLEAN,
    FOREIGN KEY(stationId) REFERENCES STATIONS(id) ,
    FOREIGN KEY(shiftId) REFERENCES SHIFTS(id),
    PRIMARY KEY (id) )  DEFAULT CHARSET=utf8;

CREATE TABLE STATISDATA
   (id BIGINT not null AUTO_INCREMENT, stationId INT, shiftId INT, doffNO BIGINT, createdOn DATETIME,
    PRIMARY KEY (id) ,
    FOREIGN KEY(stationId) REFERENCES STATIONS(id) ,
    FOREIGN KEY(shiftId) REFERENCES SHIFTS(id)
   )  DEFAULT CHARSET=utf8;

CREATE TABLE SPINDLEDATA
   (id BIGINT not null AUTO_INCREMENT, spindleId INT, stationdataId BIGINT , spindleSpeed INT, status VARCHAR(20),
    FOREIGN KEY(spindleId)     REFERENCES SPINDLES(id) ,
    FOREIGN KEY(stationdataId) REFERENCES STATIONDATA(id) ,
    PRIMARY KEY (id) )  DEFAULT CHARSET=utf8;
    
CREATE TABLE  CRAFTPARAMS
   (id INT not null AUTO_INCREMENT, stationId INT, materialParam INT, categoryParam INT, spindleAmount INT, frontRollerDiameter FLOAT,midRollerDiameter FLOAT,backRollerDiameter FLOAT,interSensorRange FLOAT, standardSpeed INT, standardTwist FLOAT,
    FOREIGN KEY(stationId) REFERENCES STATIONS(id) ,
    PRIMARY KEY (id) 
    )   DEFAULT CHARSET=utf8;
    
CREATE TABLE CODEMAPPINGS
   (id INT not null AUTO_INCREMENT, codeNO INT, name VARCHAR(20), description VARCHAR(20),
     PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
     
CREATE TABLE NAMEVALUES
    (id INT not null AUTO_INCREMENT, name VARCHAR(120) NOT NULL , code INT,value FLOAT, name_type CHAR(1),
    UNIQUE (name),
    PRIMARY KEY (id) )   DEFAULT CHARSET=utf8;
     
CREATE TABLE ROLES
   (id INT not null AUTO_INCREMENT, name VARCHAR(30), name_CN VARCHAR(30), name_EN VARCHAR(30), description_CN VARCHAR(30),description_EN VARCHAR(30),
    UNIQUE (name_EN), 
    UNIQUE (name),
    PRIMARY KEY (id) )  DEFAULT CHARSET=utf8;
     
     
CREATE TABLE USERS
   (id INT not null AUTO_INCREMENT, name VARCHAR(30), passwd VARCHAR(128), roleId INT, roleName VARCHAR(30),
     UNIQUE (name),
     FOREIGN KEY(roleId) REFERENCES ROLES(id),
     FOREIGN KEY(roleName) REFERENCES ROLES(name),
     PRIMARY KEY (id) )  DEFAULT CHARSET=utf8;   
     
-- CREATE TABLE USERROLES
--   ( id INT GENERATED BY DEFAULT AS IDENTITY, userName VARCHAR(30), roleName VARCHAR(30), roleName_CN VARCHAR(30), roleName_EN VARCHAR(30) 
--     PRIMARY KEY (id) );
     
CREATE TABLE VERSION
    (vernumber VARCHAR(64),  createdOn TIMESTAMP DEFAULT current_timestamp);
    
INSERT into VERSION values('1.0.0',null);
insert into roles ( name,name_CN, name_EN, description_CN, description_EN ) values  ('admin-users' ,'管理员' ,'Admin', '管理员有所有权限','have all privileges.');
insert into roles ( name,name_CN, name_EN, description_CN, description_EN ) values  ('valid-users','操作员' ,'Operators', '操作员有查看数据权限','have view privileges.');
insert into roles (name,description_CN, description_EN) values('manager-gui','Tomcat role for managing apps','tomcat管理员');

insert into users (name,passwd,roleName) values('tomcat',   '33e1b232a4e6fa0028a6670753749a17',   'manager-gui');
insert into users (name, passwd, roleId,roleName)  values ('admin001','4eef1e1ea34879a2ae60c60815927ed9',1,'admin-users');

insert into shifts(name,description) values('甲班','甲班');
insert into shifts(name,description) values('乙班','乙班');
insert into shifts(name,description) values('丙班','丙班');
insert into shifts(name,description) values('丁班','丁班');

-- index
CREATE INDEX shift_name   on SHIFTS   (name);
CREATE INDEX codemap_name on CODEMAPPINGS (name);
CREATE INDEX monitor_status on MONITORS (status);
CREATE INDEX station_status on STATIONS (status);
CREATE INDEX station_NO     on STATIONS (NO);

CREATE INDEX monitor_station on MONITORS (stationId);
CREATE INDEX spindle_station on SPINDLES (stationId);
CREATE INDEX stdata_station    on STATIONDATA (stationId);
CREATE INDEX stdata_shift    on STATIONDATA (shiftId);
CREATE INDEX spdata_spindle  on SPINDLEDATA (spindleId);
CREATE INDEX spdata_stationdata  on SPINDLEDATA (stationDataId);
CREATE INDEX craftparam_station on CRAFTPARAMS (stationId);

CREATE INDEX statisdata_station    on STATISDATA (stationId);
CREATE INDEX statisdata_shift    on STATISDATA (shiftId);
CREATE INDEX statisdata_doffNO   on STATISDATA (doffNO);


