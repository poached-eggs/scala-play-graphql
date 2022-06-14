-- !Ups

create table team(
    id          uuid not null constraint pk_team primary key,
    name        varchar default null,
    country     varchar default null
);

create table player(
    id              uuid not null constraint pk_image primary key,
    first_name      varchar default null,
    last_name       varchar default null,
    field_position  varchar default null,
    nationality     varchar default null
);

-- !Downs

DROP TABLE team;
DROP TABLE player;