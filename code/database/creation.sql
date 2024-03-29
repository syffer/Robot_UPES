/*
*
*
*
*/


-- delete old table 
drop table AnnotatedObjects;
drop sequence AnnotatedObjects_SequenceId;

create table AnnotatedObjects (
	id int primary key,		-- auto increment by trigger AnnotatedObjects_TriggerId 
	tag varchar(15) not null, 
 	
	area float not null,
	perimeter float not null,

	compactness float not null,
	circularity float not null,
	curvature float not null,
	bendingEnergy float not null,

	width number(6) not null,
	height number(6) not null,
	ratioWidthHeight float not null
);


-- used to auto increment id 
create sequence AnnotatedObjects_SequenceId
start with 1
increment by 1
cache 10;

-- auto increment id 
create or replace trigger AnnotatedObjects_TriggerId 
before insert 
on AnnotatedObjects 
for each row 
begin
	if(inserting and :new.id is null) then 
		select AnnotatedObjects_SequenceId.nextval 
		into :new.id 
		from sys.dual;
	end if;
end;
/

commit; 

-- tests
/* 
insert into AnnotatedObjects(tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight)
values('ROCK', 1, 1, 1, 1, 1, 1, 1, 1, 1);
*/


select * 
from AnnotatedObjects 
order by compactness, bendingEnergy, curvature, circularity, perimeter, area, ratioWidthHeight, width, height;


