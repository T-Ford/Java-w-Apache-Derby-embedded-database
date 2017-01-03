-- music.sql
-- Written by Tyler Ford for CS 455 @ RIC
-- This file contains SQL information for creating and populating 
-- a database of musicians and their band

CONNECT 'jdbc:derby:music;create=true';

CREATE TABLE Musician (
	Id int,
	Fname varchar(20),
	Lname varchar(20),
	CurrentBand varchar (40),
	PRIMARY KEY(Id)
);

INSERT INTO Musician VALUES
	(1000, 'Joe', 'Bonamassa', 'Joe Bonamassa'),
	(1001, 'Dave', 'Grohl', 'Foo Fighters'),
	(1002, 'Michael', 'Balzary', 'Red Hot Chili Peppers'),
	(1003, 'Mark', 'Hoppus', 'Blink 182'),
	(1004, 'Chris', 'Shiflett', 'Foo Fighters');

CREATE TABLE Guitarplayer (
	Id int REFERENCES Musician(Id),
	FavoriteGuitar varchar(20),
	Amplifier varchar(30),
	PRIMARY KEY(Id)
);

INSERT INTO Guitarplayer VALUES
	(1000, 'Gibson Les Paul', 'Marshall JCM 2000'),
	(1001, 'Gibson DG-335', 'Mesa Boogie Dual Rectifier'),
	(1004, 'Fender Telecaster', 'Vox AC30');
	
CREATE TABLE Bassplayer (
	Id int REFERENCES Musician (Id),
	FavoriteBass varchar(25),
	Amplifier varchar(30),
	PRIMARY KEY(Id)
);

INSERT INTO Bassplayer VALUES
	(1002, 'Fender Jazz Bass', 'Gallien-Krueger 2001'),
	(1003, 'Fender Precision Bass', 'Ampeg SVT-CL');

CREATE TABLE Bandlist (
	Id int,
	Name varchar(25),
	PRIMARY KEY(Id)
);

INSERT INTO Bandlist VALUES
	(2000, 'Joe Bonamassa'),
	(2001, 'Foo Fighters'),
	(2002, 'Red Hot Chili Peppers'),
	(2003, 'Blink 182');

CREATE TABLE Band (
	PlayerId int REFERENCES Musician(Id),
	BandId int REFERENCES Bandlist(Id)
);
	
INSERT INTO Band VALUES
	(1000, 2000),
	(1001, 2001),
	(1002, 2002),
	(1003, 2003),
	(1004, 2001);