--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-10-16 21:10:27

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16463)
-- Name: private; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA private;


ALTER SCHEMA private OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 227 (class 1259 OID 16617)
-- Name: applications; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.applications (
    id_application integer NOT NULL,
    id_user integer NOT NULL,
    id_violation integer[] NOT NULL,
    "Информация" text NOT NULL,
    "Аудио" character varying[],
    "Видео" character varying[],
    "Изображение" character varying[] NOT NULL,
    "Дата поступления" date DEFAULT now() NOT NULL,
    id_status integer NOT NULL,
    id_moderator integer,
    "Комментарий модератора" text,
    "Дата проверки" date,
    CONSTRAINT empty_information CHECK (("Информация" <> ''::text))
);


ALTER TABLE private.applications OWNER TO postgres;

--
-- TOC entry 4872 (class 0 OID 0)
-- Dependencies: 227
-- Name: TABLE applications; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.applications IS 'Заявления';


--
-- TOC entry 226 (class 1259 OID 16616)
-- Name: applications_id_application_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.applications_id_application_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.applications_id_application_seq OWNER TO postgres;

--
-- TOC entry 4873 (class 0 OID 0)
-- Dependencies: 226
-- Name: applications_id_application_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.applications_id_application_seq OWNED BY private.applications.id_application;


--
-- TOC entry 223 (class 1259 OID 16565)
-- Name: moderators; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.moderators (
    id_moderators integer NOT NULL,
    id_user integer NOT NULL,
    "Дата начала должности" date DEFAULT now() NOT NULL,
    "Дата окончания должности" date
);


ALTER TABLE private.moderators OWNER TO postgres;

--
-- TOC entry 4874 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE moderators; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.moderators IS 'Модераторы';


--
-- TOC entry 222 (class 1259 OID 16564)
-- Name: moderators_id_moderators_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.moderators_id_moderators_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.moderators_id_moderators_seq OWNER TO postgres;

--
-- TOC entry 4875 (class 0 OID 0)
-- Dependencies: 222
-- Name: moderators_id_moderators_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.moderators_id_moderators_seq OWNED BY private.moderators.id_moderators;


--
-- TOC entry 219 (class 1259 OID 16490)
-- Name: statuses; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.statuses (
    id_status integer NOT NULL,
    "Статус" text NOT NULL,
    CONSTRAINT empty_status CHECK (("Статус" <> ''::text))
);


ALTER TABLE private.statuses OWNER TO postgres;

--
-- TOC entry 4876 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE statuses; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.statuses IS 'Статусы заявления';


--
-- TOC entry 218 (class 1259 OID 16489)
-- Name: statuses_id_status_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.statuses_id_status_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.statuses_id_status_seq OWNER TO postgres;

--
-- TOC entry 4877 (class 0 OID 0)
-- Dependencies: 218
-- Name: statuses_id_status_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.statuses_id_status_seq OWNED BY private.statuses.id_status;


--
-- TOC entry 217 (class 1259 OID 16465)
-- Name: types_violations; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.types_violations (
    id_type integer NOT NULL,
    "Вид" text NOT NULL
);


ALTER TABLE private.types_violations OWNER TO postgres;

--
-- TOC entry 4878 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE types_violations; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.types_violations IS 'Виды правонарушений';


--
-- TOC entry 216 (class 1259 OID 16464)
-- Name: types_violations_id_type_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.types_violations_id_type_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.types_violations_id_type_seq OWNER TO postgres;

--
-- TOC entry 4879 (class 0 OID 0)
-- Dependencies: 216
-- Name: types_violations_id_type_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.types_violations_id_type_seq OWNED BY private.types_violations.id_type;


--
-- TOC entry 225 (class 1259 OID 16578)
-- Name: users; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.users (
    id_user integer NOT NULL,
    "Фамилия" text NOT NULL,
    "Имя" text NOT NULL,
    "Отчество" text NOT NULL,
    "Данные" integer NOT NULL,
    "Дата регистрации" date DEFAULT now() NOT NULL,
    CONSTRAINT empty_name CHECK (("Имя" <> ''::text)),
    CONSTRAINT empty_patronymic CHECK (("Отчество" <> ''::text)),
    CONSTRAINT empty_surname CHECK (("Фамилия" <> ''::text))
);


ALTER TABLE private.users OWNER TO postgres;

--
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE users; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.users IS 'Пользователи';


--
-- TOC entry 221 (class 1259 OID 16500)
-- Name: users_data; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.users_data (
    id_users_data integer NOT NULL,
    "Логин" text NOT NULL,
    "Пароль" text NOT NULL,
    "Электронная почта" text NOT NULL,
    "Аватар" character varying(2083) DEFAULT NULL::character varying,
    CONSTRAINT empty_email CHECK (("Электронная почта" <> ''::text)),
    CONSTRAINT empty_login CHECK (("Логин" <> ''::text)),
    CONSTRAINT empty_password CHECK (("Пароль" <> ''::text)),
    CONSTRAINT weak_password CHECK (((("Пароль" ~ similar_to_escape('*[А-Я]*'::text)) OR ("Пароль" ~ similar_to_escape('*[A-Z]*'::text))) AND ("Пароль" ~ similar_to_escape('*[0-9]*'::text)))),
    CONSTRAINT wrong_email CHECK (("Электронная почта" ~ similar_to_escape('*@*.*'::text)))
);


ALTER TABLE private.users_data OWNER TO postgres;

--
-- TOC entry 4881 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE users_data; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.users_data IS 'Данные пользователей';


--
-- TOC entry 220 (class 1259 OID 16499)
-- Name: users_data_id_users_data_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.users_data_id_users_data_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.users_data_id_users_data_seq OWNER TO postgres;

--
-- TOC entry 4882 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_data_id_users_data_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.users_data_id_users_data_seq OWNED BY private.users_data.id_users_data;


--
-- TOC entry 224 (class 1259 OID 16577)
-- Name: users_id_users_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.users_id_users_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.users_id_users_seq OWNER TO postgres;

--
-- TOC entry 4883 (class 0 OID 0)
-- Dependencies: 224
-- Name: users_id_users_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.users_id_users_seq OWNED BY private.users.id_user;


--
-- TOC entry 229 (class 1259 OID 16652)
-- Name: violations; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.violations (
    id_violation integer NOT NULL,
    "Статья" character varying(20) NOT NULL,
    "Название" text NOT NULL,
    "Вид" integer,
    "Наказание" text NOT NULL,
    CONSTRAINT empty_name CHECK (("Название" <> ''::text))
);


ALTER TABLE private.violations OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16651)
-- Name: violations_id_violation_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.violations_id_violation_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.violations_id_violation_seq OWNER TO postgres;

--
-- TOC entry 4884 (class 0 OID 0)
-- Dependencies: 228
-- Name: violations_id_violation_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.violations_id_violation_seq OWNED BY private.violations.id_violation;


--
-- TOC entry 4673 (class 2604 OID 16620)
-- Name: applications id_application; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.applications ALTER COLUMN id_application SET DEFAULT nextval('private.applications_id_application_seq'::regclass);


--
-- TOC entry 4669 (class 2604 OID 16568)
-- Name: moderators id_moderators; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderators ALTER COLUMN id_moderators SET DEFAULT nextval('private.moderators_id_moderators_seq'::regclass);


--
-- TOC entry 4666 (class 2604 OID 16493)
-- Name: statuses id_status; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.statuses ALTER COLUMN id_status SET DEFAULT nextval('private.statuses_id_status_seq'::regclass);


--
-- TOC entry 4665 (class 2604 OID 16468)
-- Name: types_violations id_type; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.types_violations ALTER COLUMN id_type SET DEFAULT nextval('private.types_violations_id_type_seq'::regclass);


--
-- TOC entry 4671 (class 2604 OID 16581)
-- Name: users id_user; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users ALTER COLUMN id_user SET DEFAULT nextval('private.users_id_users_seq'::regclass);


--
-- TOC entry 4667 (class 2604 OID 16503)
-- Name: users_data id_users_data; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users_data ALTER COLUMN id_users_data SET DEFAULT nextval('private.users_data_id_users_data_seq'::regclass);


--
-- TOC entry 4675 (class 2604 OID 16655)
-- Name: violations id_violation; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violations ALTER COLUMN id_violation SET DEFAULT nextval('private.violations_id_violation_seq'::regclass);


--
-- TOC entry 4864 (class 0 OID 16617)
-- Dependencies: 227
-- Data for Name: applications; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.applications (id_application, id_user, id_violation, "Информация", "Аудио", "Видео", "Изображение", "Дата поступления", id_status, id_moderator, "Комментарий модератора", "Дата проверки") FROM stdin;
\.


--
-- TOC entry 4860 (class 0 OID 16565)
-- Dependencies: 223
-- Data for Name: moderators; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.moderators (id_moderators, id_user, "Дата начала должности", "Дата окончания должности") FROM stdin;
\.


--
-- TOC entry 4856 (class 0 OID 16490)
-- Dependencies: 219
-- Data for Name: statuses; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.statuses (id_status, "Статус") FROM stdin;
\.


--
-- TOC entry 4854 (class 0 OID 16465)
-- Dependencies: 217
-- Data for Name: types_violations; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.types_violations (id_type, "Вид") FROM stdin;
\.


--
-- TOC entry 4862 (class 0 OID 16578)
-- Dependencies: 225
-- Data for Name: users; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.users (id_user, "Фамилия", "Имя", "Отчество", "Данные", "Дата регистрации") FROM stdin;
\.


--
-- TOC entry 4858 (class 0 OID 16500)
-- Dependencies: 221
-- Data for Name: users_data; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.users_data (id_users_data, "Логин", "Пароль", "Электронная почта", "Аватар") FROM stdin;
\.


--
-- TOC entry 4866 (class 0 OID 16652)
-- Dependencies: 229
-- Data for Name: violations; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.violations (id_violation, "Статья", "Название", "Вид", "Наказание") FROM stdin;
\.


--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 226
-- Name: applications_id_application_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.applications_id_application_seq', 1, false);


--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 222
-- Name: moderators_id_moderators_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.moderators_id_moderators_seq', 1, false);


--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 218
-- Name: statuses_id_status_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.statuses_id_status_seq', 1, false);


--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 216
-- Name: types_violations_id_type_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.types_violations_id_type_seq', 1, false);


--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_data_id_users_data_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.users_data_id_users_data_seq', 1, false);


--
-- TOC entry 4890 (class 0 OID 0)
-- Dependencies: 224
-- Name: users_id_users_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.users_id_users_seq', 1, false);


--
-- TOC entry 4891 (class 0 OID 0)
-- Dependencies: 228
-- Name: violations_id_violation_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.violations_id_violation_seq', 1, false);


--
-- TOC entry 4676 (class 2606 OID 16542)
-- Name: types_violations empty_type; Type: CHECK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE private.types_violations
    ADD CONSTRAINT empty_type CHECK (("Вид" <> ''::text)) NOT VALID;


--
-- TOC entry 4701 (class 2606 OID 16626)
-- Name: applications same_id_application; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.applications
    ADD CONSTRAINT same_id_application PRIMARY KEY (id_application);


--
-- TOC entry 4697 (class 2606 OID 16571)
-- Name: moderators same_id_moderators; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderators
    ADD CONSTRAINT same_id_moderators PRIMARY KEY (id_moderators);


--
-- TOC entry 4691 (class 2606 OID 16498)
-- Name: statuses same_id_status; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.statuses
    ADD CONSTRAINT same_id_status PRIMARY KEY (id_status);


--
-- TOC entry 4689 (class 2606 OID 16473)
-- Name: types_violations same_id_type; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.types_violations
    ADD CONSTRAINT same_id_type PRIMARY KEY (id_type);


--
-- TOC entry 4699 (class 2606 OID 16597)
-- Name: users same_id_users; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users
    ADD CONSTRAINT same_id_users PRIMARY KEY (id_user);


--
-- TOC entry 4693 (class 2606 OID 16512)
-- Name: users_data same_id_users_data; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users_data
    ADD CONSTRAINT same_id_users_data PRIMARY KEY (id_users_data);


--
-- TOC entry 4703 (class 2606 OID 16660)
-- Name: violations same_id_violation; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violations
    ADD CONSTRAINT same_id_violation PRIMARY KEY (id_violation);


--
-- TOC entry 4695 (class 2606 OID 16514)
-- Name: users_data users_data_login_key; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users_data
    ADD CONSTRAINT users_data_login_key UNIQUE ("Логин");


--
-- TOC entry 4706 (class 2606 OID 16637)
-- Name: applications applications_missing_moderator_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.applications
    ADD CONSTRAINT applications_missing_moderator_fkey FOREIGN KEY (id_moderator) REFERENCES private.moderators(id_moderators);


--
-- TOC entry 4707 (class 2606 OID 16632)
-- Name: applications applications_missing_status_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.applications
    ADD CONSTRAINT applications_missing_status_fkey FOREIGN KEY (id_status) REFERENCES private.statuses(id_status);


--
-- TOC entry 4708 (class 2606 OID 16627)
-- Name: applications applications_missing_user_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.applications
    ADD CONSTRAINT applications_missing_user_fkey FOREIGN KEY (id_user) REFERENCES private.users(id_user);


--
-- TOC entry 4704 (class 2606 OID 16643)
-- Name: moderators moderators_missing_user; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderators
    ADD CONSTRAINT moderators_missing_user FOREIGN KEY (id_user) REFERENCES private.users(id_user) NOT VALID;


--
-- TOC entry 4705 (class 2606 OID 16590)
-- Name: users users_missing_users_data; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.users
    ADD CONSTRAINT users_missing_users_data FOREIGN KEY ("Данные") REFERENCES private.users_data(id_users_data);


--
-- TOC entry 4709 (class 2606 OID 16661)
-- Name: violations violations_missing_type_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violations
    ADD CONSTRAINT violations_missing_type_fkey FOREIGN KEY ("Вид") REFERENCES private.types_violations(id_type);


-- Completed on 2024-10-16 21:10:28

--
-- PostgreSQL database dump complete
--

