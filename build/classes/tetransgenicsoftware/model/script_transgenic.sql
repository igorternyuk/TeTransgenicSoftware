/**
 * Author:  igor
 * Created: May 31, 2017
 */
create database bd_alimentos;
use bd_alimentos;

create table marca(
    id int auto_increment,
    nombre varchar(100),
    primary key(id)
);

insert into marca values(null, 'Arcor'); --1
insert into marca values(null, 'Galaf'); --2
insert into marca values(null, 'Kellogs'); --3
insert into marca values(null, 'Aruba'); --4
insert into marca values(null, 'Albo'); --5
insert into marca values(null, 'Savory'); --6
insert into marca values(null, 'Lider'); --7
insert into marca values(null, 'Soprole'); --8
insert into marca values(null, 'Trendi'); --9
insert into marca values(null, 'alhue'); --10
insert into marca values(null, 'Garber'); --11
insert into marca values(null, 'Nestle'); --12
insert into marca values(null, 'Whatts'); --13
insert into marca values(null, 'Aurora'); --14
insert into marca values(null, 'Mega'); --15
insert into marca values(null, 'Mickilsen'); --16

create table alimento(
     id int auto_increment,
     nombre varchar(100),
     marca int,/*FK*/
     trans bit,
     primary key(id),
     foreign key(marca) references marca(id)
);

 insert into alimento values(null,'Cereal Mix',1,true);
 insert into alimento values(null,'Granola',2,true);
 insert into alimento values(null,'Choco Krispi',3,false);
 insert into alimento values(null,'Lomito Atún en aceite',4,true);
 insert into alimento values(null,'Atún y Sardina',5,false);
 insert into alimento values(null,'Lolipop',6,false);
 insert into alimento values(null,'Ketchup',7,true);
 insert into alimento values(null,'Petit Fort',8,false);
 insert into alimento values(null,'Cassata',9,true);
 insert into alimento values(null,'Pororo',10,false);
 insert into alimento values(null,'Jugos',11,false);
 insert into alimento values(null,'Colados',12,true);
 insert into alimento values(null,'Salsa de tomates',13,false);
 insert into alimento values(null,'budin',14,true);
 insert into alimento values(null,'Chis pop',15,true);
 insert into alimento values(null,'Margarina',12,true);
 insert into alimento values(null,'Marmeladas',16,true);

select * from marca;
select * from alimento;
select * from marca where id=4;
drop database bd_alimentos;