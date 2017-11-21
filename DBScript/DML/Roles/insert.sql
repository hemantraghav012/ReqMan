INSERT INTO reqman.roles(id, name, status, datecreated, createdby) VALUES (1, 'Application Admin', true, current_timestamp, 'SYSTEM');
INSERT INTO reqman.roles(id, name, status, datecreated, createdby) VALUES (2, 'Account Admin', true, current_timestamp, 'SYSTEM');
INSERT INTO reqman.roles(id, name, status, datecreated, createdby) VALUES (3, 'Requestor', true, current_timestamp, 'SYSTEM');
INSERT INTO reqman.roles(id, name, status, datecreated, createdby) VALUES (4, 'Team Member', true, current_timestamp, 'SYSTEM');
select * from reqman.roles;

select * from reqman.users;

select * from reqman.userroles;
-- 2,3,4,5,6,7
insert into reqman.userroles(userid,roleid) values (2,3);
insert into reqman.userroles(userid,roleid) values (3,3);
insert into reqman.userroles(userid,roleid) values (4,3);
insert into reqman.userroles(userid,roleid) values (5,3);
insert into reqman.userroles(userid,roleid) values (6,3);
insert into reqman.userroles(userid,roleid) values (7,3);

-- by default requestor role, post trail period then role will become to team member