

insert into user values('profesor',0,'profesor@esei.uvigo.es','profesor','profesor','793741d54b00253006453742ad4ed534','PROFESSOR','profesor');
insert into user values('agnovoa2',0,'agnovoa2@esei.uvigo.es','Gutierrez','Alejandro','0cb5e3c9e4fb7f4cc6342b18aa5adc8c','INTERN','Novoa');
insert into user values('alumno',0,'alumno@esei.uvigo.es','alumno','alumno','c6865cf98b133f1f3de596a4a2894630','STUDENT','alumno');

insert into subject(subjectName,code,degree,description,petitionState) values('PROI','O06G150V01104','GRADE','',1);
insert into subject(subjectName,code,degree,description,petitionState) values('SGI','O06M132V01105','MASTER','',0);

insert into software(softwareName,downloadURL,type) values('Kubuntu 15.10', 'kubuntu.org', 'OPERATIVE_SYSTEM');
insert into software(softwareName,downloadURL,type) values('CodeBlocks', 'coeblocks.org', 'PROGRAM');

insert into prof_sub values('profesor',1);

insert into sub_soft values(1,1);

insert into model(modelName,tradeMark) values('OKI model 1', 'OKI');

insert into printer(inventoryNumber, ubication, modelName, unused) values(1, 'Despacho 301', 1, 0);

insert into prof_prin values('profesor',1);

insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER N','Negro','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO N','Negro','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER NF','Negro fotográfico','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO NF','Negro fotográfico','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER C','Cyan','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO C','Cyan','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER CC','Cyan claro','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO CC','Cyan claro','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER M','Magenta','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO M','Magenta','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER MM','Magenta claro','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO MM','Magenta claro','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER A','Amarillo','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO A','Amarillo','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI TONER T','Tricolor','TONER','Para impresora OKI');
insert into consumable(consumableName, colour, consumableType, description) values('OKI CARTUCHO T','Tricolor','CARTRIDGE','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI CINTURON','BELT_UNIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI FUSOR','FUSER','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI BASURA','GARBAGE_UNIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI TRANSFERENCIA','TRANSFER_KIT','Para impresora OKI');
insert into consumable(consumableName, consumableType, description) values('OKI TAMBOR','DRUM','Para impresora OKI');

insert into cons_model values(1,1);
insert into cons_model values(1,21);

insert into configuration values(1,'ou=esei,o=ou.uvigo.es','172.19.30.3','codeledeldap','agnovoa2@esei.uvigo.es','correoSephiroth69',636,'simple','ssl','agnovoa2@esei.uvigo.es','uid=readldap,ou=administradores,ou=esei,o=ou.uvigo.es');

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