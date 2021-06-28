drop table REFERENCIA_ANTERIOR;
drop table OPOSICION;
drop table REL_DEP_EPI;
drop table DEPARTAMENTO;
drop table EPIGRAFE;
create table DEPARTAMENTO(
ETQ VARCHAR(1000) not null primary key,
NOMBRE VARCHAR(1000) not null
);
create table EPIGRAFE(
NOMBRE VARCHAR(1000) not null primary key
);
create table REL_DEP_EPI(
NOMBREEP VARCHAR(1000) not null,
ETQDEP VARCHAR(1000) not null,
PRIMARY KEY(NOMBREEP,ETQDEP),
FOREIGN KEY(NOMBREEP) REFERENCES EPIGRAFE(NOMBRE),
FOREIGN KEY(ETQDEP) REFERENCES DEPARTAMENTO(ETQ)
);
create table OPOSICION(
ID VARCHAR(1000) not null primary key,
FECHA DATE not null,
ESTADO VARCHAR(100) not null,
CONTROL VARCHAR(100) not null,
NOMBREEP VARCHAR(1000) not null,
ETQDEP VARCHAR(1000) not null,
URLPDF VARCHAR(200) not null,
URLXML VARCHAR(200) not null,
TITULO VARCHAR(2000) not null,
FOREIGN KEY(NOMBREEP,ETQDEP) REFERENCES REL_DEP_EPI(NOMBREEP,ETQDEP)
);
create table REFERENCIA_ANTERIOR(
ID_REF_ANTERIOR VARCHAR(1000) not null,
ID_REF_POSTERIOR VARCHAR(1000) not null,
palabra VARCHAR(1000),
texto VARCHAR(1000),
PRIMARY KEY(ID_REF_ANTERIOR,ID_REF_POSTERIOR),
FOREIGN KEY(ID_REF_ANTERIOR) REFERENCES OPOSICION(ID),
FOREIGN KEY(ID_REF_POSTERIOR) REFERENCES OPOSICION(ID)
);
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep1','pruebaNombreDep1');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep2','pruebaNombreDep2');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep3','pruebaNombreDep3');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep4','pruebaNombreDep4');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep5','pruebaNombreDep5');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDep6','pruebaNombreDep6');
insert into DEPARTAMENTO(ETQ,NOMBRE)
values ('pruebaETQDepVacio','pruebaNombreDepVacio');
insert into EPIGRAFE
values ('pruebaNombreEp1');
insert into EPIGRAFE
values ('pruebaNombreEp2');
insert into EPIGRAFE
values ('pruebaNombreEp3');
insert into EPIGRAFE
values ('pruebaNombreEp4');
insert into EPIGRAFE
values ('pruebaNombreEp5');
insert into EPIGRAFE
values ('pruebaNombreEp6');
insert into EPIGRAFE
values ('pruebaNombreEpVacio');

insert into REL_DEP_EPI
values ('pruebaNombreEp1','pruebaETQDep1' );



insert into OPOSICION
values('pruebaId1','2000-01-01','pruebaEstado1','pruebaControl1','pruebaNombreEp1','pruebaETQDep1','pruebaUrlPdf1','pruebaUrlXml1','pruebaTitulo1');
insert into OPOSICION
values('pruebaId2','2000-01-02','pruebaEstado2','pruebaControl2','pruebaNombreEp1','pruebaETQDep1','pruebaUrlPdf2','pruebaUrlXml2','pruebaTitulo2');
insert into OPOSICION
values('pruebaId3','2001-01-02','pruebaEstado3','pruebaControl3','pruebaNombreEp1','pruebaETQDep1','pruebaUrlPdf3','pruebaUrlXml3','pruebaTitulo3');

insert into REFERENCIA_ANTERIOR
values ('pruebaId1','pruebaId2','pruebaPalabra12', 'pruebaTexto12');
insert into REFERENCIA_ANTERIOR
values ('pruebaId1','pruebaId3','pruebaPalabra13', 'pruebaTexto13');
insert into REFERENCIA_ANTERIOR
values ('pruebaId2','pruebaId3','pruebaPalabra23', 'pruebaTexto23');