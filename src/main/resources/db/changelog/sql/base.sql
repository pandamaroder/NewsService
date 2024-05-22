--changeset olga.kukushkina:2024.05.12:user.sequence
create sequence if not exists demo.user_seq;

--changeset olga.kukushkina:2024.05.12:users.table
create table if not exists demo.users (
    id bigint primary key default nextval('demo.user_seq'),
    username varchar(255) not null unique
);

--changeset olga.kukushkina:2024.05.12:news.sequence
create sequence if not exists demo.categories_seq;

--changeset olga.kukushkina:2024.05.12:news.table
create table if not exists demo.categories (
    id bigint primary key default nextval('demo.categories_seq'),
    name varchar(255) not null
);

--changeset olga.kukushkina:2024.05.12:news.sequence
create sequence if not exists demo.news_seq;

--changeset olga.kukushkina:2024.05.12:news.table
create table if not exists demo.news (
    id bigint primary key default nextval('demo.news_seq'),
    title varchar(255) not null,
    content text,
    user_id bigint,
    category_id bigint,
    foreign key (user_id) references demo.users (id),
    foreign key (category_id) references demo.categories (id)
);

--changeset olga.kukushkina:2024.05.12:comment.sequence
create sequence if not exists demo.comment_seq;

--changeset olga.kukushkina:2024.05.12:comments.table
create table if not exists demo.comments (
    id bigint primary key default nextval('demo.comment_seq'),
    text varchar(255) not null,
    user_id bigint,
    news_id bigint,
    foreign key (user_id) references demo.users (id),
    foreign key (news_id) references demo.news (id)
);

