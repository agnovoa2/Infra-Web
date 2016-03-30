select * from user;
select * from subject;
select * from software;
select * from prof_sub;
select * from sub_soft;
select * from printer;
select * from model;
select * from consumable;

delete from sub_soft;
delete from prof_sub;
delete from software;
delete from user;
delete from subject;
delete from printer;
delete from model;
delete from consumable;

insert into user values('profesor',1,'profesor@esei.uvigo.es','profesor','profesor','profesor','PROFESSOR','profesor');

insert into subject values('PROI','O06G150V01104','GRADE','',0);
insert into subject values('SGI','O06M132V01105','MASTER','',0);

insert into software values('Kubuntu 15.10', 'kubuntu.org', 'OPERATIVE_SYSTEM');
insert into software values('CodeBlocks', 'coeblocks.org', 'PROGRAM');

insert into prof_sub values('profesor','PROI');

insert into sub_soft values('Informática: Programación I','Kubuntu 15.10');

insert into model values('OKI model 1', 'OKI');

insert into printer values(1, 'Despacho 301', 'OKI model 1');

insert into prof_prin values('profesor',1);

insert into consumable values('OKI TONER N','Negro','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO N','Negro','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER NF','Negro fotográfico','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO NF','Negro fotográfico','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER C','Cyan','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO C','Cyan','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER CC','Cyan claro','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO CC','Cyan claro','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER M','Magenta','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO M','Magenta','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER MM','Magenta claro','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO MM','Magenta claro','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER A','Amarillo','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO A','Amarillo','CARTRIDGE','Para impresora OKI');
insert into consumable values('OKI TONER T','Tricolor','TONER','Para impresora OKI');
insert into consumable values('OKI CARTUCHO T','Tricolor','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI CINTURON','BELT_UNIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI FUSOR','FUSER','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI BASURA','GARBAGE_UNIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI TRANSFERENCIA','TRANSFER_KIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI TAMBOR','DRUM','Para impresora OKI');

insert into cons_model values('OKI model 1','OKI TONER N');
insert into cons_model values('OKI model 1','OKI TAMBOR');