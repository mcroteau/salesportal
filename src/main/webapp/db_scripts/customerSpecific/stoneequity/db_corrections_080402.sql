-- change note to 2000----

alter table note add column note_temp varchar(2000);
update note set note_temp = note;
alter table note drop column note;
alter table note add column note varchar(2000);
update note set note = note_temp;
alter table note drop column note_temp;

