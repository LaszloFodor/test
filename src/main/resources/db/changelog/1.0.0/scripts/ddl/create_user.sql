create table users
(
    id                             bigint generated by default as identity primary key,
    date_of_birth                  date,
    email                          varchar(255) unique,
    name                           varchar(255),
    name_of_mother                 varchar(255),
    place_of_birth                 varchar(255),
    social_security_identification varchar(9) unique
);