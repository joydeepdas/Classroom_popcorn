create table Movie 
(

MID varchar(10) not null ,
Title varchar(50) not null ,
Release_Year year not null,
Imdb_rating float(1) not null,
Synopsis varchar(500) not null, 
primary  key (MID)
);

create table Movie_Genres
(

MID varchar(10) not null ,
Genres varchar(50) not null,
primary key (MID,Genres),
Foreign key (MID) references Movie(MID)

);

CREATE TABLE Movie_Locations
(

MID varchar(10) not null ,
Locations varchar(50) not null,
primary key (MID,Locations),
Foreign key (MID) references Movie(MID)

);


CREATE TABLE Movie_keywords
(

MID varchar(10) not null ,
Keywords varchar(50) not null,
primary key (MID,Keywords),
Foreign key (MID) references Movie(MID)

);

CREATE TABLE Person
(

PID varchar(10) not null ,
P_Name varchar(50) not null unique,
Gender varchar(10) not null,
Image_url varchar(500),
primary key (PID)

);

CREATE TABLE Credit
(

MID varchar(10) not null ,
PID varchar(10) not null ,
Role varchar(50) not null,
primary key (MID,PID),
Foreign key (MID) references Movie(MID),
Foreign key (PID) references Person(PID)

);


CREATE TABLE Country
(

C_Name varchar(50) not null,
primary key (C_Name)
);

CREATE TABLE Movie_Certificates
(

MID varchar(10) not null ,
C_Name varchar(50) not null,
Certificate varchar(50) not null,
primary key (MID,C_Name),
Foreign key (MID) references Movie(MID),
Foreign key (C_Name) references Country(C_Name)

);

CREATE  TABLE Movie_dates
(

MID varchar(10) not null ,
C_Name varchar(50) not null,
Release_Date date not null,
primary key (MID,C_Name,Release_Date),
Foreign key (MID) references Movie(MID),
Foreign key (C_Name) references Country(C_Name)

);


CREATE  TABLE Movie_Languages
(

MID varchar(10) not null ,
C_Name varchar(50) not null,
Languages varchar(50) not null,
primary key (MID,C_Name,Languages),
Foreign key (MID) references Movie(MID),
Foreign key (C_Name) references Country(C_Name)

);


CREATE  TABLE Stream_Info
(

MID varchar(10) not null ,
Movie_url varchar(500) not null,
Trailer_url varchar(500),
Image_url varchar(500) not null,
Running_time int(10) not null,
Video_quality int(10) not null,
primary key (MID),
FOREIGN KEY (MID) references Movie(MID) 

);


CREATE  TABLE User
(

Email_Id varchar(50) not null ,
Name varchar(50) not null,
Password varchar(50) not null,
Image_url varchar(500),
primary key (Email_Id) 

);

CREATE  TABLE Reviews
(

Email_Id varchar(50) not null ,
MID varchar(10) not null,
Time_stamp TIMESTAMP not null,
Rating float(1) not null,
Comment varchar(500) not null,
primary key (Email_Id,MID) ,
FOREIGN KEY (MID) references Movie(MID) ,
FOREIGN KEY (Email_Id) references User(Email_Id)

);

CREATE  TABLE Bookmarks
(

Email_Id varchar(50) not null ,
MID varchar(10) not null,
time_elapsed int(10) not null,
primary key (Email_Id,MID) ,
FOREIGN KEY (MID) references Movie(MID), 
FOREIGN KEY (Email_Id) references User (Email_Id)
);

CREATE TABLE System
(

MSN varchar(50) not null,
Last_email_Id varchar(50),
primary key (MSN) ,
FOREIGN KEY (Last_email_Id) references User(Email_Id) 

);

CREATE  TABLE User_search
(

Email_Id varchar(50) not null ,
MID varchar(10) not null,
time_stamp timestamp not null,
primary key (Email_Id,MID) ,
FOREIGN KEY (MID) references Movie(MID),
FOREIGN KEY (Email_Id) references User(Email_Id) 

);

CREATE  TABLE System_search
(

MSN varchar(50) not null ,
MID varchar(10) not null,
time_stamp timestamp not null,
primary key (MSN,MID), 
FOREIGN KEY (MSN) references System (MSN),
FOREIGN KEY (MID) references Movie(MID)

);
