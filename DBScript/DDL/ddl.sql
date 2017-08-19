-- Table: reqman.audittrail

-- DROP TABLE reqman.audittrail;

CREATE TABLE reqman.audittrail
(
    id integer NOT NULL DEFAULT nextval('reqman.audittrail_id_seq'::regclass),
    userid character varying(100) COLLATE "default".pg_catalog,
    requestheader text COLLATE "default".pg_catalog,
    responseheader text COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    username character varying(100) COLLATE "default".pg_catalog,
    CONSTRAINT pk_audittrail_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.audittrail
    OWNER to postgres;

GRANT ALL ON TABLE reqman.audittrail TO postgres WITH GRANT OPTION;


-- Table: reqman.category

-- DROP TABLE reqman.category;

CREATE TABLE reqman.category
(
    id integer NOT NULL DEFAULT nextval('reqman.category_id_seq'::regclass),
    name character varying(50) COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_category_id PRIMARY KEY (id),
    CONSTRAINT uni_category_name UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.category
    OWNER to postgres;

GRANT ALL ON TABLE reqman.category TO postgres WITH GRANT OPTION;

-- Table: reqman.menu

-- DROP TABLE reqman.menu;

CREATE TABLE reqman.menu
(
    id integer NOT NULL,
    name character varying(100) COLLATE "default".pg_catalog,
    tabname character varying(100) COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(100) COLLATE "default".pg_catalog,
    CONSTRAINT pk_menu_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.menu
    OWNER to postgres;

GRANT ALL ON TABLE reqman.menu TO postgres WITH GRANT OPTION;

-- Table: reqman.project

-- DROP TABLE reqman.project;

CREATE TABLE reqman.project
(
    id integer NOT NULL DEFAULT nextval('reqman.project_id_seq'::regclass),
    name character varying(50) COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_project_id PRIMARY KEY (id),
    CONSTRAINT uni_project_name UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.project
    OWNER to postgres;

GRANT ALL ON TABLE reqman.project TO postgres WITH GRANT OPTION;

-- Table: reqman.request

-- DROP TABLE reqman.request;

CREATE TABLE reqman.request
(
    id integer NOT NULL DEFAULT nextval('reqman.request_id_seq'::regclass),
    title character varying(100) COLLATE "default".pg_catalog,
    projectid integer,
    categoryid integer,
    requesttypeid integer,
    description character varying(500) COLLATE "default".pg_catalog,
    completiondate timestamp without time zone,
    attachment bytea,
    requeststatus integer,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    datemodified timestamp without time zone,
    modifiedby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_request_id PRIMARY KEY (id),
    CONSTRAINT uni_request_title UNIQUE (requesttypeid, title, projectid, categoryid),
    CONSTRAINT fk_request_category FOREIGN KEY (id)
        REFERENCES reqman.category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_request_project FOREIGN KEY (id)
        REFERENCES reqman.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_request_requesttype FOREIGN KEY (id)
        REFERENCES reqman.requesttype (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.request
    OWNER to postgres;

GRANT ALL ON TABLE reqman.request TO postgres WITH GRANT OPTION;

-- Table: reqman.requesttype

-- DROP TABLE reqman.requesttype;

CREATE TABLE reqman.requesttype
(
    id integer NOT NULL DEFAULT nextval('reqman.requesttype_id_seq'::regclass),
    name character varying COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_requesttype PRIMARY KEY (id),
    CONSTRAINT uni_requesttype_name UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.requesttype
    OWNER to postgres;

GRANT ALL ON TABLE reqman.requesttype TO postgres WITH GRANT OPTION;

-- Table: reqman.requestworkflow

-- DROP TABLE reqman.requestworkflow;

CREATE TABLE reqman.requestworkflow
(
    id integer NOT NULL DEFAULT nextval('reqman.requestworkflow_id_seq'::regclass),
    requestid integer,
    friendid integer,
    completionpercentage real,
    requestby character varying(50) COLLATE "default".pg_catalog,
    requeststatus integer,
    acceptdate timestamp without time zone,
    updatedate timestamp without time zone,
    revisionnumber integer,
    approvedby character varying(50) COLLATE "default".pg_catalog,
    approveddate timestamp without time zone,
    datecreated timestamp without time zone,
    createdby character varying COLLATE "default".pg_catalog,
    datemodified timestamp without time zone,
    modifiedby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_requestworkflow_id PRIMARY KEY (id),
    CONSTRAINT fk_workflow_friendid FOREIGN KEY (id)
        REFERENCES reqman.userfriendlist (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_workflow_requestid FOREIGN KEY (id)
        REFERENCES reqman.request (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.requestworkflow
    OWNER to postgres;

GRANT ALL ON TABLE reqman.requestworkflow TO postgres WITH GRANT OPTION;

-- Table: reqman.rolemenus

-- DROP TABLE reqman.rolemenus;

CREATE TABLE reqman.rolemenus
(
    roleid integer,
    menuid integer,
    CONSTRAINT fk_rolemenu_menuid FOREIGN KEY (menuid)
        REFERENCES reqman.menu (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_rolemenu_roleid FOREIGN KEY (roleid)
        REFERENCES reqman.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.rolemenus
    OWNER to postgres;

GRANT ALL ON TABLE reqman.rolemenus TO postgres WITH GRANT OPTION;

-- Table: reqman.roles

-- DROP TABLE reqman.roles;

CREATE TABLE reqman.roles
(
    id integer NOT NULL,
    name character varying(50) COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(100) COLLATE "default".pg_catalog,
    CONSTRAINT pk_role_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.roles
    OWNER to postgres;

GRANT ALL ON TABLE reqman.roles TO postgres WITH GRANT OPTION;

-- Table: reqman.userfriendlist

-- DROP TABLE reqman.userfriendlist;

CREATE TABLE reqman.userfriendlist
(
    id integer NOT NULL DEFAULT nextval('reqman."userfriendList_id_seq"'::regclass),
    userid integer,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_friendlist_id PRIMARY KEY (id),
    CONSTRAINT fk_friend_userid FOREIGN KEY (id)
        REFERENCES reqman.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.userfriendlist
    OWNER to postgres;

GRANT ALL ON TABLE reqman.userfriendlist TO postgres WITH GRANT OPTION;

-- Table: reqman.userroles

-- DROP TABLE reqman.userroles;

CREATE TABLE reqman.userroles
(
    roleid integer,
    userid integer,
    CONSTRAINT fk_userrole_roleid FOREIGN KEY (roleid)
        REFERENCES reqman.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_userrole_userid FOREIGN KEY (userid)
        REFERENCES reqman.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.userroles
    OWNER to postgres;

GRANT ALL ON TABLE reqman.userroles TO postgres WITH GRANT OPTION;

-- Table: reqman.users

-- DROP TABLE reqman.users;

CREATE TABLE reqman.users
(
    id integer NOT NULL DEFAULT nextval('reqman.users_id_seq'::regclass),
    emailid character varying(100) COLLATE "default".pg_catalog,
    password character varying(12) COLLATE "default".pg_catalog,
    firstname character varying(50) COLLATE "default".pg_catalog,
    lastname character varying(50) COLLATE "default".pg_catalog,
    shortname character varying(100) COLLATE "default".pg_catalog,
    status boolean,
    createdby character varying(50) COLLATE "default".pg_catalog,
    createdon timestamp without time zone,
    lastlogin timestamp without time zone,
    photo bytea,
    CONSTRAINT pk_users_id PRIMARY KEY (id),
    CONSTRAINT uniq_user_id UNIQUE (emailid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.users
    OWNER to postgres;

GRANT ALL ON TABLE reqman.users TO postgres WITH GRANT OPTION;

-- Table: reqman.usertype

-- DROP TABLE reqman.usertype;

CREATE TABLE reqman.usertype
(
    id integer NOT NULL,
    name character varying(100) COLLATE "default".pg_catalog,
    status boolean,
    datecreated timestamp without time zone,
    createdby character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.usertype
    OWNER to postgres;

GRANT ALL ON TABLE reqman.usertype TO postgres;

-- Table: reqman.userusertype

-- DROP TABLE reqman.userusertype;

CREATE TABLE reqman.userusertype
(
    usertypeid integer,
    userid integer,
    CONSTRAINT fk_usertypes FOREIGN KEY (usertypeid)
        REFERENCES reqman.usertype (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_userusertypes_userid FOREIGN KEY (userid)
        REFERENCES reqman.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE reqman.userusertype
    OWNER to postgres;

GRANT ALL ON TABLE reqman.userusertype TO postgres WITH GRANT OPTION;
