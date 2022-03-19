SET @myVar = (SELECT b.id
FROM qr.part_box p, box_move b
where p.out_doc='114e98bd-f957-4277-a2d6-94699dcacf4e' 
and b.id=p.box_move
and b.sent_to_master_date is not null);

Update qr.box_move
set sent_to_master_date=null
where id in (select * from (SELECT b.id
FROM qr.part_box p, box_move b
where p.out_doc='114e98bd-f957-4277-a2d6-94699dcacf4e' 
and b.id=p.box_move
and b.sent_to_master_date is not null) as t)