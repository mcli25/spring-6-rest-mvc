
    create table beer (
        price decimal(10,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        upc varchar(25),
        id varchar(36) not null,
        beer_name varchar(50),
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        active bit,
        version integer not null,
        created_date datetime(6) not null,
        update_date datetime(6) not null,
        id CHAR(36) not null,
        phone_number varchar(20),
        name varchar(50) not null,
        email varchar(100),
        primary key (id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create index idx_customer_name 
       on customer (name);
