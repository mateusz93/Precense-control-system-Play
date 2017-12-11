# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table course_date (
  id                            integer auto_increment not null,
  teachercourseid               integer,
  starttime                     time,
  finishtime                    time,
  date                          date,
  constraint pk_course_date primary key (id)
);

create table field (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_field primary key (id)
);

create table grade (
  id                            integer auto_increment not null,
  teachercourseid               integer,
  studentid                     bigint,
  value                         integer not null,
  isfinalgrade                  tinyint(1) default 0 not null,
  time                          datetime(6),
  constraint pk_grade primary key (id)
);

create table student_course (
  id                            integer auto_increment not null,
  teachercourseid               integer,
  studentid                     bigint,
  constraint pk_student_course primary key (id)
);

create table student_group (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_student_group primary key (id)
);

create table subject (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  fieldid                       integer,
  yearofstudy                   integer not null,
  quantity                      integer not null,
  minquantity                   integer not null,
  constraint pk_subject primary key (id)
);

create table teacher_course (
  id                            integer auto_increment not null,
  subjectid                     integer,
  studentgroup                  varchar(255),
  teacherid                     bigint,
  description                   varchar(255),
  constraint pk_teacher_course primary key (id)
);

create table token (
  id                            integer auto_increment not null,
  token                         varchar(255),
  userid                        bigint,
  expirydate                    date,
  constraint uq_token_userid unique (userid),
  constraint pk_token primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  username                      varchar(255),
  firstname                     varchar(255),
  lastname                      varchar(255),
  type                          varchar(255),
  password                      varchar(255),
  lastlogin                     datetime(6),
  status                        varchar(255),
  fieldid                       integer,
  groupid                       integer,
  yearofstudy                   integer not null,
  email                         varchar(255),
  phone                         varchar(255),
  street                        varchar(255),
  city                          varchar(255),
  constraint pk_user primary key (id)
);

alter table course_date add constraint fk_course_date_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;
create index ix_course_date_teachercourseid on course_date (teachercourseid);

alter table grade add constraint fk_grade_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;
create index ix_grade_teachercourseid on grade (teachercourseid);

alter table grade add constraint fk_grade_studentid foreign key (studentid) references user (id) on delete restrict on update restrict;
create index ix_grade_studentid on grade (studentid);

alter table student_course add constraint fk_student_course_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;
create index ix_student_course_teachercourseid on student_course (teachercourseid);

alter table student_course add constraint fk_student_course_studentid foreign key (studentid) references user (id) on delete restrict on update restrict;
create index ix_student_course_studentid on student_course (studentid);

alter table subject add constraint fk_subject_fieldid foreign key (fieldid) references field (id) on delete restrict on update restrict;
create index ix_subject_fieldid on subject (fieldid);

alter table teacher_course add constraint fk_teacher_course_subjectid foreign key (subjectid) references subject (id) on delete restrict on update restrict;
create index ix_teacher_course_subjectid on teacher_course (subjectid);

alter table teacher_course add constraint fk_teacher_course_teacherid foreign key (teacherid) references user (id) on delete restrict on update restrict;
create index ix_teacher_course_teacherid on teacher_course (teacherid);

alter table token add constraint fk_token_userid foreign key (userid) references user (id) on delete restrict on update restrict;

alter table user add constraint fk_user_fieldid foreign key (fieldid) references field (id) on delete restrict on update restrict;
create index ix_user_fieldid on user (fieldid);

alter table user add constraint fk_user_groupid foreign key (groupid) references student_group (id) on delete restrict on update restrict;
create index ix_user_groupid on user (groupid);


# --- !Downs

alter table course_date drop foreign key fk_course_date_teachercourseid;
drop index ix_course_date_teachercourseid on course_date;

alter table grade drop foreign key fk_grade_teachercourseid;
drop index ix_grade_teachercourseid on grade;

alter table grade drop foreign key fk_grade_studentid;
drop index ix_grade_studentid on grade;

alter table student_course drop foreign key fk_student_course_teachercourseid;
drop index ix_student_course_teachercourseid on student_course;

alter table student_course drop foreign key fk_student_course_studentid;
drop index ix_student_course_studentid on student_course;

alter table subject drop foreign key fk_subject_fieldid;
drop index ix_subject_fieldid on subject;

alter table teacher_course drop foreign key fk_teacher_course_subjectid;
drop index ix_teacher_course_subjectid on teacher_course;

alter table teacher_course drop foreign key fk_teacher_course_teacherid;
drop index ix_teacher_course_teacherid on teacher_course;

alter table token drop foreign key fk_token_userid;

alter table user drop foreign key fk_user_fieldid;
drop index ix_user_fieldid on user;

alter table user drop foreign key fk_user_groupid;
drop index ix_user_groupid on user;

drop table if exists course_date;

drop table if exists field;

drop table if exists grade;

drop table if exists student_course;

drop table if exists student_group;

drop table if exists subject;

drop table if exists teacher_course;

drop table if exists token;

drop table if exists user;

