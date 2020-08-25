drop schema if exists bot cascade;
create schema bot;

drop user if exists player;
create user player password 'player' valid until 'infinity';

grant all on schema bot to player;
grant all privileges on all sequences in schema bot to player;
grant all privileges on all functions in schema bot to player;

create table bot.users (
	id varchar(64) primary key,
	tag varchar(100),
	experience varchar(18)
);
