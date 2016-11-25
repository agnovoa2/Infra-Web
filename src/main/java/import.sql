use infra-web;

insert into User values('agnovoa2',0,'agnovoa2@esei.uvigo.es','Gutierrez','Alejandro','0cb5e3c9e4fb7f4cc6342b18aa5adc8c','INTERN','Novoa');

insert into IncidenceType values('Monitor');
insert into IncidenceType values('Cpu');
insert into IncidenceType values('Teclado');
insert into IncidenceType values('Raton');
insert into IncidenceType values('Red');
insert into IncidenceType values('Inicio de Sesion');
insert into IncidenceType values('Software');
insert into IncidenceType values('Otros');
insert into IncidenceType values('Windows');
insert into IncidenceType values('Linux');

insert into Configuration values(1,'ou=esei,o=ou.uvigo.es','172.19.30.3','codeledeldap','agnovoa2@esei.uvigo.es','correoSephiroth69',636,'simple','ssl','agnovoa2@esei.uvigo.es','uid=readldap,ou=administradores,ou=esei,o=ou.uvigo.es');