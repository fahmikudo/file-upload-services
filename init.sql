create database if not exists db_file_upload;

use db_file_upload;

create table if not exists file_upload_task_log_activities (
    id bigint not null auto_increment,
    activity_name varchar(255),
    created_by varchar(255),
    creation_time bigint,
    last_updated_by varchar(255),
    last_updated_time bigint,
    reference_id varchar(255),
    user_id bigint,
    primary key (id)
) engine=InnoDB;

create table if not exists file_upload_tasks (
    id bigint not null auto_increment,
    created_by varchar(255),
    creation_time bigint,
    file_name varchar(255),
    file_path varchar(255),
    file_type varchar(255),
    last_access_time bigint,
    last_updated_by varchar(255),
    last_updated_time bigint,
    status varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists roles (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists users (
    id bigint not null auto_increment,
    enabled bit,
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists user_roles (
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id)
) engine=InnoDB;

alter table file_upload_task_log_activities add constraint fk_user_id foreign key (user_id) references users (id);

alter table user_roles add constraint fk_role_id foreign key (role_id) references roles (id);

alter table user_roles add constraint fk_user_role_id foreign key (user_id) references users (id);

insert into roles (name) values ('ADMIN'), ('USER');

insert into users (enabled, password, username) values (true, '$2a$10$a3SBMP9LTz.MeOUXgc37DOwtVW/e8LmfjbFmSLa8Kl72zAIsfT0Im', 'testadmin'),
                                                       (true, '$2a$10$a3SBMP9LTz.MeOUXgc37DOwtVW/e8LmfjbFmSLa8Kl72zAIsfT0Im', 'testuser');

insert into user_roles (user_id, role_id) values (1, 1), (1, 2), (2, 2);