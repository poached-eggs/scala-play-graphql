-- !Ups

create table request_metadata(
    id          uuid not null constraint pk_request_metadata primary key,
    datetime    timestamp default null,
    uri         varchar default null,
    body        varchar default null
);

-- !Downs

DROP TABLE request_metadata;