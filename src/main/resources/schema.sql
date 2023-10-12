create table news (
    id long primary key auto_increment,
    name text not null,
    annotation text not null,
    content text not null,
    image blob not null,
    placed_at timestamp not null
);

create table category (
    id long primary key auto_increment,
    name text not null,
    description text not null,
    image text not null
);

create table product (
    id long primary key auto_increment,
    category_id long,
    name text not null,
    description text not null,
    coast bigint not null,
    image text not null,
    foreign key (category_id) references category(id)
);