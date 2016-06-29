/*==============================================================*/
/* DBMS name:      MySQL 4.0                                    */
/* Created on:     5/17/2016 10:04:12 PM                        */
/*==============================================================*/


drop table if exists CENOVNIK;

drop table if exists FAKTURA_OTPREMNICA;

drop table if exists GRUPA_PROIZVODA;

drop table if exists JEDINICE_MERE;

drop table if exists KATALOG_ROBE_I_USLUGA;

drop table if exists NARUDZBA;

drop table if exists PORESKA_STOPA;

drop table if exists POREZ;

drop table if exists POSLOVNA_GODINA;

drop table if exists POSLOVNI_PARTNER;

drop table if exists PREDUZECE;

drop table if exists SIFRA_DELATNOSTI;

drop table if exists STAVKE_CENOVNIKA;

drop table if exists STAVKE_FAKTURE_OTPREMNICE;

drop table if exists STAVKE_NARUDZBE;

/*==============================================================*/
/* Table: CENOVNIK                                              */
/*==============================================================*/
create table CENOVNIK
(
   ID_CENOVNIKA                   bigint                         not null AUTO_INCREMENT,
   ID_PREDUZECA                   bigint                         not null,
   NAZIV_CENOVNIKA                varchar(100),
   DATUM_PRIMENE                  date                           not null,
   primary key (ID_CENOVNIKA)
);

/*==============================================================*/
/* Table: FAKTURA_OTPREMNICA                                    */
/*==============================================================*/
create table FAKTURA_OTPREMNICA
(
   ID_FAKTURE_OTPREMNICE          bigint                         not null AUTO_INCREMENT,
   ID_POSLOVNE_GODINE             bigint                         not null,
   ID_PARTNERSTVA                 bigint                         not null,
   ID_NARUDZBE                    bigint,
   FA_BROJ                        numeric(6,0)                   not null,
   FA_TIP                         char(1)                        not null default 'P',
   FA_DATUM                       date                           not null,
   FA_DATUM_VALUTE                date                           not null,
   DATUM_OBRACUNA                 date,
   FA_UKUPNO                      decimal(15,2)                  not null default 0,
   FA_RABAT                       decimal(15,2)                  default 0,
   FA_POREZ                       decimal(15,2)                  not null default 0,
   FA_IZNOS                       decimal(15,2)                  not null default 0,
   FA_TEKRACUN                    varchar(50)                    not null,
   FA_POZIV                       varchar(50),
   STATUS_FAKTURE                 char(1)                        not null default 'E',
   DODATNE_NAPOMENE               varchar(200),
   ADRESA_ISPORUKE                varchar(50),
   BROJ_KAMIONA                   varchar(10),
   PREVOZNIK                      varchar(50),
   ROBU_I_RACUN_IZDAO             varchar(30),
   ROBU_I_RACUN_PREUZEO           varchar(30),
   primary key (ID_FAKTURE_OTPREMNICE)
);

/*==============================================================*/
/* Table: GRUPA_PROIZVODA                                       */
/*==============================================================*/
create table GRUPA_PROIZVODA
(
   ID_GRUPE                       bigint                         not null AUTO_INCREMENT,
   ID_POREZA                      bigint                         not null,
   ID_PREDUZECA                   bigint                         not null,
   SIFRA_GRUPE                    varchar(4)                     not null,
   NAZIV_GRUPE                    varchar(100)                   not null,
   primary key (ID_GRUPE)
);

/*==============================================================*/
/* Table: JEDINICE_MERE                                         */
/*==============================================================*/
create table JEDINICE_MERE
(
   ID_JEDINICE_MERE               bigint                         not null AUTO_INCREMENT,
   OZNAKA_JEDINICE_MERE           varchar(3)                     not null,
   NAZIV_JEDINICE_MERE            varchar(100)                   not null,
   primary key (ID_JEDINICE_MERE)
);

/*==============================================================*/
/* Table: KATALOG_ROBE_I_USLUGA                                 */
/*==============================================================*/
create table KATALOG_ROBE_I_USLUGA
(
   ID_ARTIKLA                     bigint                         not null AUTO_INCREMENT,
   ID_GRUPE                       bigint                         not null,
   ID_JEDINICE_MERE               bigint                         not null,
   SIFRA_ARTIKLA                  varchar(20)                    not null,
   NAZIV_ARTIKLA                  varchar(100)                   not null,
   primary key (ID_ARTIKLA)
);

/*==============================================================*/
/* Table: NARUDZBA                                              */
/*==============================================================*/
create table NARUDZBA
(
   ID_NARUDZBE                    bigint                         not null AUTO_INCREMENT,
   ID_POSLOVNE_GODINE             bigint                         not null,
   ID_PARTNERSTVA                 bigint,
   BROJ_NARUDZBE                  numeric(6,0)                   not null,
   DATUM_NARUCIVANJA              date                           not null,
   ROK_ISPORUKE                   date                           not null,
   NACIN_OTPREME                  varchar(30),
   NACIN_PLACANJA                 varchar(30),
   STATUS_NARUDZBE                char(1)                        not null default 'E',
   primary key (ID_NARUDZBE)
);

/*==============================================================*/
/* Table: PORESKA_STOPA                                         */
/*==============================================================*/
create table PORESKA_STOPA
(
   ID_STOPE                       bigint                         not null AUTO_INCREMENT,
   ID_POREZA                      bigint                         not null,
   IZNOS_STOPE                    decimal(5,2)                   not null,
   DATUM_VAZENJA                  date                           not null,
   primary key (ID_STOPE)
);

/*==============================================================*/
/* Table: POREZ                                                 */
/*==============================================================*/
create table POREZ
(
   ID_POREZA                      bigint                         not null AUTO_INCREMENT,
   POR_NAZIV                      varchar(120)                   not null,
   POS_VAZI                       bool                           not null default 1,
   primary key (ID_POREZA)
);

/*==============================================================*/
/* Table: POSLOVNA_GODINA                                       */
/*==============================================================*/
create table POSLOVNA_GODINA
(
   ID_POSLOVNE_GODINE             bigint                         not null AUTO_INCREMENT,
   ID_PREDUZECA                   bigint                         not null,
   PG_GODINA2                     numeric(4,0)                   not null,
   PG_VAZI2                       bool                           not null default 1,
   primary key (ID_POSLOVNE_GODINE)
);

/*==============================================================*/
/* Table: POSLOVNI_PARTNER                                      */
/*==============================================================*/
create table POSLOVNI_PARTNER
(
   ID_PARTNERSTVA                 bigint                         not null AUTO_INCREMENT,
   ID_PREDUZECA                   bigint                         not null,
   ID_PARTNERA                    bigint                         not null,
   VRSTA                          char(1)                        not null,
   primary key (ID_PARTNERSTVA)
);

/*==============================================================*/
/* Table: PREDUZECE                                             */
/*==============================================================*/
create table PREDUZECE
(
   ID_PREDUZECA                   bigint                         not null AUTO_INCREMENT,
   ID_SIFRE_DELATNOSTI            bigint                         not null,
   NAZIV                          varchar(100)                   not null,
   PIB                            char(9)                        not null,
   MATICNI_BROJ                   char(8)                        not null,
   ADRESA                         varchar(100),
   BROJ_TELEFONA                  varchar(20),
   EMAIL                          varchar(100),
   BANKA                          varchar(100),
   TEKUCI_RACUN                   varchar(100)                   not null,
   primary key (ID_PREDUZECA)
);

/*==============================================================*/
/* Table: SIFRA_DELATNOSTI                                      */
/*==============================================================*/
create table SIFRA_DELATNOSTI
(
   ID_SIFRE_DELATNOSTI            bigint                         not null AUTO_INCREMENT,
   OZNAKA_SIFRE_DELATNOSTI        varchar(4)                     not null,
   NAZIV_SIFRE_DELATNOSTI         varchar(100)                   not null,
   primary key (ID_SIFRE_DELATNOSTI)
);

/*==============================================================*/
/* Table: STAVKE_CENOVNIKA                                      */
/*==============================================================*/
create table STAVKE_CENOVNIKA
(
   ID_STAVKE_CENOVNIKA            bigint                         not null AUTO_INCREMENT,
   ID_CENOVNIKA                   bigint                         not null,
   ID_ARTIKLA                     bigint                         not null,
   JEDINICNA_CENA_STAVKE_CENOVNIKA decimal(15,2)                  not null,
   primary key (ID_STAVKE_CENOVNIKA)
);

/*==============================================================*/
/* Table: STAVKE_FAKTURE_OTPREMNICE                             */
/*==============================================================*/
create table STAVKE_FAKTURE_OTPREMNICE
(
   ID_STAVKE_FAKTURE              bigint                         not null AUTO_INCREMENT,
   ID_ARTIKLA                     bigint                         not null,
   ID_FAKTURE_OTPREMNICE          bigint                         not null,
   KOLICINA                       decimal(15,2)                  not null,
   RABAT                          decimal(15,2),
   OSNOVICA_PDV                   decimal(15,2)                  not null,
   JEDINICNA_CENA_STAVKE_FAKTURE  decimal(15,2)                  not null,
   primary key (ID_STAVKE_FAKTURE)
);

/*==============================================================*/
/* Table: STAVKE_NARUDZBE                                       */
/*==============================================================*/
create table STAVKE_NARUDZBE
(
   ID_STAVKE_NARUDZBE             bigint                         not null AUTO_INCREMENT,
   ID_ARTIKLA                     bigint                         not null,
   ID_NARUDZBE                    bigint                         not null,
   ID_JEDINICE_MERE               bigint                         not null,
   KOLICINA_STAVKE_NARUDZBENICE   numeric(6,0)                   not null,
   CENA_BEZ_PDV_A_STAVKE_NARUDZBENICE decimal(15,2)                  not null,
   IZNOS_STAVKE_NARUDZBENICE      decimal(15,2)                  not null,
   primary key (ID_STAVKE_NARUDZBE)
);

alter table CENOVNIK add constraint FK_CENOVNIK_PREDUZECA foreign key (ID_PREDUZECA)
      references PREDUZECE (ID_PREDUZECA) on delete restrict on update restrict;

alter table FAKTURA_OTPREMNICA add constraint FK_FAKTURE_U_GODINI2 foreign key (ID_POSLOVNE_GODINE)
      references POSLOVNA_GODINA (ID_POSLOVNE_GODINE) on delete restrict on update restrict;

alter table FAKTURA_OTPREMNICA add constraint FK_KUPAC2 foreign key (ID_PARTNERSTVA)
      references POSLOVNI_PARTNER (ID_PARTNERSTVA) on delete restrict on update restrict;

alter table FAKTURA_OTPREMNICA add constraint FK_ZA_OTPREMNICU foreign key (ID_NARUDZBE)
      references NARUDZBA (ID_NARUDZBE) on delete restrict on update restrict;

alter table GRUPA_PROIZVODA add constraint FK_POREZ_ZA_PODGRUPU foreign key (ID_POREZA)
      references POREZ (ID_POREZA) on delete restrict on update restrict;

alter table GRUPA_PROIZVODA add constraint FK_ZA_PREDUZECE foreign key (ID_PREDUZECA)
      references PREDUZECE (ID_PREDUZECA) on delete restrict on update restrict;

alter table KATALOG_ROBE_I_USLUGA add constraint FK_PRIPADA_PODGRUPI foreign key (ID_GRUPE)
      references GRUPA_PROIZVODA (ID_GRUPE) on delete restrict on update restrict;

alter table KATALOG_ROBE_I_USLUGA add constraint FK_RELATIONSHIP_17 foreign key (ID_JEDINICE_MERE)
      references JEDINICE_MERE (ID_JEDINICE_MERE) on delete restrict on update restrict;

alter table NARUDZBA add constraint FK_NARUDZBA_U_GODINI foreign key (ID_POSLOVNE_GODINE)
      references POSLOVNA_GODINA (ID_POSLOVNE_GODINE) on delete restrict on update restrict;

alter table NARUDZBA add constraint FK_NARUDZBA_ZA foreign key (ID_PARTNERSTVA)
      references POSLOVNI_PARTNER (ID_PARTNERSTVA) on delete restrict on update restrict;

alter table PORESKA_STOPA add constraint FK_POREZ foreign key (ID_POREZA)
      references POREZ (ID_POREZA) on delete restrict on update restrict;

alter table POSLOVNA_GODINA add constraint FK_GODINA_ZA_PREDUZECE2 foreign key (ID_PREDUZECA)
      references PREDUZECE (ID_PREDUZECA) on delete restrict on update restrict;

alter table POSLOVNI_PARTNER add constraint FK_JE_PREDUZECE foreign key (ID_PREDUZECA)
      references PREDUZECE (ID_PREDUZECA) on delete restrict on update restrict;

alter table POSLOVNI_PARTNER add constraint FK_PARTNER_PREDUZECA2 foreign key (ID_PARTNERA)
      references PREDUZECE (ID_PREDUZECA) on delete restrict on update restrict;

alter table PREDUZECE add constraint FK_RELATIONSHIP_19 foreign key (ID_SIFRE_DELATNOSTI)
      references SIFRA_DELATNOSTI (ID_SIFRE_DELATNOSTI) on delete restrict on update restrict;

alter table STAVKE_CENOVNIKA add constraint FK_STAVKA_CENOVNIKA foreign key (ID_CENOVNIKA)
      references CENOVNIK (ID_CENOVNIKA) on delete restrict on update restrict;

alter table STAVKE_CENOVNIKA add constraint FK_ZA_ROBU_I_USLUGE foreign key (ID_ARTIKLA)
      references KATALOG_ROBE_I_USLUGA (ID_ARTIKLA) on delete restrict on update restrict;

alter table STAVKE_FAKTURE_OTPREMNICE add constraint FK_IMA_STAVKE2 foreign key (ID_FAKTURE_OTPREMNICE)
      references FAKTURA_OTPREMNICA (ID_FAKTURE_OTPREMNICE) on delete restrict on update restrict;

alter table STAVKE_FAKTURE_OTPREMNICE add constraint FK_PROMETOVANO2 foreign key (ID_ARTIKLA)
      references KATALOG_ROBE_I_USLUGA (ID_ARTIKLA) on delete restrict on update restrict;

alter table STAVKE_NARUDZBE add constraint FK_JE_JEDINICA_MERE_STAVKE_NARUDZBE foreign key (ID_JEDINICE_MERE)
      references JEDINICE_MERE (ID_JEDINICE_MERE) on delete restrict on update restrict;

alter table STAVKE_NARUDZBE add constraint FK_NARUDZBA_IMA foreign key (ID_NARUDZBE)
      references NARUDZBA (ID_NARUDZBE) on delete restrict on update restrict;

alter table STAVKE_NARUDZBE add constraint FK_STAVKA_NARUDZBE_IMA foreign key (ID_ARTIKLA)
      references KATALOG_ROBE_I_USLUGA (ID_ARTIKLA) on delete restrict on update restrict;

