update part_box set department = 3 where department in (1, 2, 4);
update part_box set department = 8 where department in (6, 7, 9);
delete from department where (id > 0) and (id not in (select distinct department from part_box));
delete from employee where (id > 0) and (id not in (select distinct employee_id from part_box));