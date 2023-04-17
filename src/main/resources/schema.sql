create table if not exists games (
    app_id bigint not null,
    image_url varchar(255),
    name varchar(255),
    primary key (app_id)
) engine=InnoDB

create table if not exists game_achievements (
    game_achievement_id bigint auto_increment not null,
    description varchar(255),
    display_name varchar(255),
    icon varchar(255),
    icon_gray varchar(255),
    steam_name varchar(255),
    app_id bigint not null,
    primary key (game_achievement_id),
    foreign key (app_id) references games (app_id)
) engine=InnoDB

create table if not exists players (
    steam_id bigint not null,
    avatar_hash varchar(255),
    last_log_off bigint,
    profile_url varchar(255),
    steam_name varchar(255),
    time_created bigint,
    primary key (steam_id)
) engine=InnoDB

create table player_games (
    steam_id bigint not null,
    app_id bigint not null,
    foreign key (steam_id) references players (steam_id),
    foreign key (app_id) references games (app_id)
) engine=InnoDB

create table player_achievements (
    player_achievement_id bigint auto_increment not null,
    achieved bit,
    unlock_time varchar(255),
    game_achievement_id bigint not null,
    steam_id bigint not null,
    primary key (player_achievement_id),
    foreign key (steam_id) references players (steam_id),
    foreign key (game_achievement_id) references game_achievements (game_achievement_id)
) engine=InnoDB

create table players_game_achievements (
    steam_id bigint not null,
    game_achievement_id bigint not null
) engine=InnoDB
