

insert into user values('profesor',1,'profesor@esei.uvigo.es','profesor','profesor','profesor','PROFESSOR','profesor');
insert into user values('agnovoa2',1,'agnovoa2@esei.uvigo.es','Gutierrez','Alejandro','eseiSephiroth69','INTERN','Novoa');
insert into user values('alumno',1,'alumno@esei.uvigo.es','alumno','alumno','alumno','STUDENT','alumno');

insert into subject(subjectName,code,degree,description,petitionState) values('PROI','O06G150V01104','GRADE','',0);
insert into subject(subjectName,code,degree,description,petitionState) values('SGI','O06M132V01105','MASTER','',0);

insert into software(softwareName,downloadURL,type) values('Kubuntu 15.10', 'kubuntu.org', 'OPERATIVE_SYSTEM');
insert into software(softwareName,downloadURL,type) values('CodeBlocks', 'coeblocks.org', 'PROGRAM');

insert into prof_sub values('profesor',1);

insert into sub_soft values(1,1);

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

insert into configuration values(1,'agnovoa2@esei.uvigo.es','correoSephiroth69','agnovoa2@esei.uvigo.es');

insert into incidencetype values('Monitor');
insert into incidencetype values('Cpu');
insert into incidencetype values('Teclado');
insert into incidencetype values('Raton');
insert into incidencetype values('Red');
insert into incidencetype values('Inicio de Sesion');
insert into incidencetype values('Software');
insert into incidencetype values('Otros');
insert into incidencetype values('Windows');
insert into incidencetype values('Linux');

insert into computer(labelNum,laboratory,num,state) values(12,'libre acceso',0,'OK');
insert into computer(labelNum,laboratory,num,state) values(13,'libre acceso',47,'INCIDENCE');

insert into incidence(date,description,state,computer,user) values('2016-03-29','una insidensia',0,2,'agnovoa2');