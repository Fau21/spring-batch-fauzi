drop table if exists eksperimen_batch;
create table if not exists eksperimen_batch(
	 id varchar(50) unique not null,
	 name varchar(50) not null,
	 price int,
     description varchar(50),
     PRIMARY KEY (id)
);