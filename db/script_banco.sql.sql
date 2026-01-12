create database sistema_cemiterio;
use sistema_cemiterio;

create table sepultura(
idSepultura int auto_increment primary key,
lote varchar(30) not null,
tipoSepultura varchar (30) not null,
statusSepultura varchar(30) not null,
familiarResponsavel varchar(100) not null,
dataCriacao date not null
);

create table falecido(
idFalecido int auto_increment primary key,
nomeCompleto varchar(100) not null,
dataNascimento date not null,
possuiCertidaoObito varchar(10) not null,
cpf varchar(12) not null,
sepultura int not null,
dataFalecimento date not null,
familiaResponsavel varchar(100),
constraint fk_falecido_sepultura foreign key(sepultura) references sepultura(idSepultura)
);

create table servico(
idServico int auto_increment primary key,
dataServico date not null,
tipoServico varchar(70) not null,
statusServico varchar(70) not null,
sepultura int not null,
notificacaoServico varchar(300) not null,
constraint fk_servico_sepultura foreign key(sepultura) references sepultura(idSepultura)
);

create table usuario(
idUsuario int auto_increment primary key,
nomeUsuario varchar(70) not null,
login varchar(50) not null unique,
senha varchar(100) not null,
perfil varchar(50) not null
);



