-- !Ups

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Insert Teams
begin;
insert into team (id, name, country) values (uuid_generate_v4(),'F.C. Barcelona','Spain');
insert into team (id, name, country) values (uuid_generate_v4(),'Real Madrid','Spain');
insert into team (id, name, country) values (uuid_generate_v4(),'Juventus','Italy');
insert into team (id, name, country) values (uuid_generate_v4(),'A.C. Milan','Italy');
insert into team (id, name, country) values (uuid_generate_v4(),'Bayern Munich','Germany');
insert into team (id, name, country) values (uuid_generate_v4(),'Borussia Dortmund','Germany');
insert into team (id, name, country) values (uuid_generate_v4(),'Manchester United','England');
insert into team (id, name, country) values (uuid_generate_v4(),'Liverpool','England');
end;

-- Insert Players
begin;
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Luis','Figo','RW','Portugal');
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Zinedine','Zidane','CAM','France');
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Gabriel','Batistuta','CF','Argentina');
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Paolo','Maldini','CB','Italy');
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Roberto','Carlos','LB','Brazil');
insert into player (id, first_name, last_name, field_position, nationality) values (uuid_generate_v4(),'Oliver','Khan','GK','Germany');
end;

-- !Downs