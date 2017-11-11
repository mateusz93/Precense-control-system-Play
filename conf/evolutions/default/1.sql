# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table course_date (
  id                            integer auto_increment not null,
  teachercourseid               integer,
  starttime                     time,
  finishtime                    time,
  date                          date,
  constraint uq_course_date_teachercourseid unique (teachercourseid),
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
  previousgradeid               integer,
  studentid                     bigint,
  value                         integer not null,
  isfinalgrade                  tinyint(1) default 0 not null,
  time                          datetime(6),
  constraint uq_grade_teachercourseid unique (teachercourseid),
  constraint uq_grade_previousgradeid unique (previousgradeid),
  constraint uq_grade_studentid unique (studentid),
  constraint pk_grade primary key (id)
);

create table student_course (
  id                            integer auto_increment not null,
  teachercourseid               integer,
  studentid                     bigint,
  constraint uq_student_course_teachercourseid unique (teachercourseid),
  constraint uq_student_course_studentid unique (studentid),
  constraint pk_student_course primary key (id)
);

create table student_presence (
  id                            integer auto_increment not null,
  status                        varchar(255),
  studentid                     bigint,
  coursedateid                  integer,
  constraint uq_student_presence_studentid unique (studentid),
  constraint uq_student_presence_coursedateid unique (coursedateid),
  constraint pk_student_presence primary key (id)
);

create table subject (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  fieldid                       integer,
  yearofstudy                   integer not null,
  quantity                      integer not null,
  minquantity                   integer not null,
  constraint uq_subject_fieldid unique (fieldid),
  constraint pk_subject primary key (id)
);

create table teacher_course (
  id                            integer auto_increment not null,
  subjectid                     integer,
  studentgroup                  varchar(255),
  teacherid                     bigint,
  description                   varchar(255),
  constraint uq_teacher_course_subjectid unique (subjectid),
  constraint uq_teacher_course_teacherid unique (teacherid),
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
  `group`                       varchar(255),
  yearofstudy                   integer not null,
  email                         varchar(255),
  phone                         varchar(255),
  street                        varchar(255),
  city                          varchar(255),
  constraint uq_user_fieldid unique (fieldid),
  constraint pk_user primary key (id)
);

alter table course_date add constraint fk_course_date_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;

alter table grade add constraint fk_grade_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;

alter table grade add constraint fk_grade_previousgradeid foreign key (previousgradeid) references grade (id) on delete restrict on update restrict;

alter table grade add constraint fk_grade_studentid foreign key (studentid) references user (id) on delete restrict on update restrict;

alter table student_course add constraint fk_student_course_teachercourseid foreign key (teachercourseid) references teacher_course (id) on delete restrict on update restrict;

alter table student_course add constraint fk_student_course_studentid foreign key (studentid) references user (id) on delete restrict on update restrict;

alter table student_presence add constraint fk_student_presence_studentid foreign key (studentid) references user (id) on delete restrict on update restrict;

alter table student_presence add constraint fk_student_presence_coursedateid foreign key (coursedateid) references course_date (id) on delete restrict on update restrict;

alter table subject add constraint fk_subject_fieldid foreign key (fieldid) references field (id) on delete restrict on update restrict;

alter table teacher_course add constraint fk_teacher_course_subjectid foreign key (subjectid) references subject (id) on delete restrict on update restrict;

alter table teacher_course add constraint fk_teacher_course_teacherid foreign key (teacherid) references user (id) on delete restrict on update restrict;

alter table token add constraint fk_token_userid foreign key (userid) references user (id) on delete restrict on update restrict;

alter table user add constraint fk_user_fieldid foreign key (fieldid) references field (id) on delete restrict on update restrict;


# --- !Downs

alter table course_date drop foreign key fk_course_date_teachercourseid;

alter table grade drop foreign key fk_grade_teachercourseid;

alter table grade drop foreign key fk_grade_previousgradeid;

alter table grade drop foreign key fk_grade_studentid;

alter table student_course drop foreign key fk_student_course_teachercourseid;

alter table student_course drop foreign key fk_student_course_studentid;

alter table student_presence drop foreign key fk_student_presence_studentid;

alter table student_presence drop foreign key fk_student_presence_coursedateid;

alter table subject drop foreign key fk_subject_fieldid;

alter table teacher_course drop foreign key fk_teacher_course_subjectid;

alter table teacher_course drop foreign key fk_teacher_course_teacherid;

alter table token drop foreign key fk_token_userid;

alter table user drop foreign key fk_user_fieldid;

drop table if exists course_date;

drop table if exists field;

drop table if exists grade;

drop table if exists student_course;

drop table if exists student_presence;

drop table if exists subject;

drop table if exists teacher_course;

drop table if exists token;

drop table if exists user;

