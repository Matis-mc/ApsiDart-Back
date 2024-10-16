    create table Player (
        id varchar(255) not null AUTO_INCREMENT,
        firstName varchar(255),
        lastName varchar(255),
        pseudo varchar(255),
        primary key (id)
    );

    insert into Player (id, firstName, lastName, pseudo) values("1", "matis", "charrier", "mati");