create table category (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, unit_of_measure_id bigint, primary key (id)) engine=InnoDB;
create table notes (id bigint not null auto_increment, recipe_notes longtext, recipe_id bigint, primary key (id)) engine=InnoDB;
create table recipe (id bigint not null auto_increment, cook_time integer, description varchar(255), difficulty varchar(255), directions longtext, image longblob, prep_time integer, source varchar(255), url varchar(255), notes_id bigint, primary key (id)) engine=InnoDB;
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB;
create table unit_of_measure (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
alter table ingredient add constraint recipe_into_ingredient foreign key (recipe_id) references recipe (id);
alter table ingredient add constraint uom_into_ingredient foreign key (unit_of_measure_id) references unit_of_measure (id);
alter table recipe add constraint notes_into_recipe foreign key (notes_id) references notes (id);
alter table recipe_category add constraint category_into_recipe_category foreign key (category_id) references category (id);
alter table recipe_category add constraint recipe_into_recipe_category foreign key (recipe_id) references recipe (id);
