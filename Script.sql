drop table users;

create table users (
	id SERIAL primary key,
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255),
	password_hash varchar(60),
	CONSTRAINT constraint_name UNIQUE (email)
);

select id, email, password_hash from users;

delete  from users;

drop table doctors;
create table doctors (
	user_id INTEGER not null,
	ml_number varchar(255),
	specialization varchar(255)
);

drop table healthdata;
create table healthdata (
	id SERIAL primary key,
	checkup_date timestamp,
	user_id INTEGER not null,
	weight float,
	"height" float,
	steps int,
	heartRate int,
	bp_high int,
	bp_low int,
	caloric_intake int,
	constraint unique_healthdata unique (checkup_date, user_id)
);

select * from healthdata;
select distinct(user_id), * from healthdata ORDER BY checkup_date desc;

drop table recommendations;
create table recommendations (
	diagnosis varchar(255),
	resolution varchar(255), 
	checkup_id int,
	constraint unique_recommendation unique (checkup_id, diagnosis)
);

select * from recommendations;

drop table medicine_schedule;
create table medicine_schedule (
	cron varchar(64),
	start_date date,
	end_date date,
	dosage varchar(64),
	medicine varchar(255),
	user_id int not null,
	constraint unique_schedule unique (medicine, user_id)
);

select * from medicine_schedule;

select * from users;

select * from doctors;
