--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-10-26 19:19:52

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
-- TOC entry 233 (class 1259 OID 16752)
-- Name: application; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.application (
    id_application integer NOT NULL,
    "Заявитель" integer NOT NULL,
    "Нарушение" integer[] NOT NULL,
    "Данные" integer NOT NULL,
    "Дата поступления" date DEFAULT now() NOT NULL,
    "Район" integer NOT NULL,
    "Статус" integer NOT NULL,
    "Модератор" integer,
    "Комментарий модератора" text,
    "Дата проверки" date
);


ALTER TABLE private.application OWNER TO postgres;

--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 233
-- Name: TABLE application; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.application IS 'Заявления';


--
-- TOC entry 231 (class 1259 OID 16728)
-- Name: application_data; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.application_data (
    id_application_data integer NOT NULL,
    "Информация" text NOT NULL,
    "Аудио" character varying[],
    "Видео" character varying[],
    "Изображение" character varying[] NOT NULL,
    CONSTRAINT empty_information CHECK (("Информация" <> ''::text))
);


ALTER TABLE private.application_data OWNER TO postgres;

--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 231
-- Name: TABLE application_data; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.application_data IS 'Данные заявлений';


--
-- TOC entry 230 (class 1259 OID 16727)
-- Name: application_data_id_application_data_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.application_data_id_application_data_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.application_data_id_application_data_seq OWNER TO postgres;

--
-- TOC entry 4911 (class 0 OID 0)
-- Dependencies: 230
-- Name: application_data_id_application_data_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.application_data_id_application_data_seq OWNED BY private.application_data.id_application_data;


--
-- TOC entry 232 (class 1259 OID 16751)
-- Name: application_id_application_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.application_id_application_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.application_id_application_seq OWNER TO postgres;

--
-- TOC entry 4912 (class 0 OID 0)
-- Dependencies: 232
-- Name: application_id_application_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.application_id_application_seq OWNED BY private.application.id_application;


--
-- TOC entry 229 (class 1259 OID 16667)
-- Name: district; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.district (
    id_district integer NOT NULL,
    "Район" character varying(50) NOT NULL,
    CONSTRAINT empty_district CHECK ((("Район")::text <> ''::text))
);


ALTER TABLE private.district OWNER TO postgres;

--
-- TOC entry 4913 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE district; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.district IS 'Районы';


--
-- TOC entry 228 (class 1259 OID 16666)
-- Name: district_id_district_seq; Type: SEQUENCE; Schema: private; Owner: postgres
--

CREATE SEQUENCE private.district_id_district_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE private.district_id_district_seq OWNER TO postgres;

--
-- TOC entry 4914 (class 0 OID 0)
-- Dependencies: 228
-- Name: district_id_district_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.district_id_district_seq OWNED BY private.district.id_district;


--
-- TOC entry 223 (class 1259 OID 16565)
-- Name: moderator; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.moderator (
    id_moderator integer NOT NULL,
    "Пользователь" integer NOT NULL,
    "Дата начала должности" date DEFAULT now() NOT NULL,
    "Дата окончания должности" date
);


ALTER TABLE private.moderator OWNER TO postgres;

--
-- TOC entry 4915 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE moderator; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.moderator IS 'Модераторы';


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
-- TOC entry 4916 (class 0 OID 0)
-- Dependencies: 222
-- Name: moderators_id_moderators_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.moderators_id_moderators_seq OWNED BY private.moderator.id_moderator;


--
-- TOC entry 219 (class 1259 OID 16490)
-- Name: status; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.status (
    id_status integer NOT NULL,
    "Статус" text NOT NULL,
    CONSTRAINT empty_status CHECK (("Статус" <> ''::text))
);


ALTER TABLE private.status OWNER TO postgres;

--
-- TOC entry 4917 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE status; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.status IS 'Статусы заявления';


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
-- TOC entry 4918 (class 0 OID 0)
-- Dependencies: 218
-- Name: statuses_id_status_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.statuses_id_status_seq OWNED BY private.status.id_status;


--
-- TOC entry 217 (class 1259 OID 16465)
-- Name: type_violation; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.type_violation (
    id_type integer NOT NULL,
    "Вид" text NOT NULL
);


ALTER TABLE private.type_violation OWNER TO postgres;

--
-- TOC entry 4919 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE type_violation; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.type_violation IS 'Виды правонарушений';


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
-- TOC entry 4920 (class 0 OID 0)
-- Dependencies: 216
-- Name: types_violations_id_type_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.types_violations_id_type_seq OWNED BY private.type_violation.id_type;


--
-- TOC entry 225 (class 1259 OID 16578)
-- Name: user; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private."user" (
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


ALTER TABLE private."user" OWNER TO postgres;

--
-- TOC entry 4921 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE "user"; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private."user" IS 'Пользователи';


--
-- TOC entry 221 (class 1259 OID 16500)
-- Name: user_data; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.user_data (
    id_user_data integer NOT NULL,
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


ALTER TABLE private.user_data OWNER TO postgres;

--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE user_data; Type: COMMENT; Schema: private; Owner: postgres
--

COMMENT ON TABLE private.user_data IS 'Данные пользователей';


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
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_data_id_users_data_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.users_data_id_users_data_seq OWNED BY private.user_data.id_user_data;


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
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 224
-- Name: users_id_users_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.users_id_users_seq OWNED BY private."user".id_user;


--
-- TOC entry 227 (class 1259 OID 16652)
-- Name: violation; Type: TABLE; Schema: private; Owner: postgres
--

CREATE TABLE private.violation (
    id_violation integer NOT NULL,
    "Статья" character varying(20) NOT NULL,
    "Название" text NOT NULL,
    "Вид" integer,
    "Наказание" text NOT NULL,
    CONSTRAINT empty_name CHECK (("Название" <> ''::text))
);


ALTER TABLE private.violation OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16651)
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
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 226
-- Name: violations_id_violation_seq; Type: SEQUENCE OWNED BY; Schema: private; Owner: postgres
--

ALTER SEQUENCE private.violations_id_violation_seq OWNED BY private.violation.id_violation;


--
-- TOC entry 4686 (class 2604 OID 16755)
-- Name: application id_application; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application ALTER COLUMN id_application SET DEFAULT nextval('private.application_id_application_seq'::regclass);


--
-- TOC entry 4685 (class 2604 OID 16731)
-- Name: application_data id_application_data; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application_data ALTER COLUMN id_application_data SET DEFAULT nextval('private.application_data_id_application_data_seq'::regclass);


--
-- TOC entry 4684 (class 2604 OID 16670)
-- Name: district id_district; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.district ALTER COLUMN id_district SET DEFAULT nextval('private.district_id_district_seq'::regclass);


--
-- TOC entry 4679 (class 2604 OID 16568)
-- Name: moderator id_moderator; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderator ALTER COLUMN id_moderator SET DEFAULT nextval('private.moderators_id_moderators_seq'::regclass);


--
-- TOC entry 4676 (class 2604 OID 16493)
-- Name: status id_status; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.status ALTER COLUMN id_status SET DEFAULT nextval('private.statuses_id_status_seq'::regclass);


--
-- TOC entry 4675 (class 2604 OID 16468)
-- Name: type_violation id_type; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.type_violation ALTER COLUMN id_type SET DEFAULT nextval('private.types_violations_id_type_seq'::regclass);


--
-- TOC entry 4681 (class 2604 OID 16581)
-- Name: user id_user; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private."user" ALTER COLUMN id_user SET DEFAULT nextval('private.users_id_users_seq'::regclass);


--
-- TOC entry 4677 (class 2604 OID 16503)
-- Name: user_data id_user_data; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.user_data ALTER COLUMN id_user_data SET DEFAULT nextval('private.users_data_id_users_data_seq'::regclass);


--
-- TOC entry 4683 (class 2604 OID 16655)
-- Name: violation id_violation; Type: DEFAULT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violation ALTER COLUMN id_violation SET DEFAULT nextval('private.violations_id_violation_seq'::regclass);


--
-- TOC entry 4903 (class 0 OID 16752)
-- Dependencies: 233
-- Data for Name: application; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.application (id_application, "Заявитель", "Нарушение", "Данные", "Дата поступления", "Район", "Статус", "Модератор", "Комментарий модератора", "Дата проверки") FROM stdin;
\.


--
-- TOC entry 4901 (class 0 OID 16728)
-- Dependencies: 231
-- Data for Name: application_data; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.application_data (id_application_data, "Информация", "Аудио", "Видео", "Изображение") FROM stdin;
\.


--
-- TOC entry 4899 (class 0 OID 16667)
-- Dependencies: 229
-- Data for Name: district; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.district (id_district, "Район") FROM stdin;
\.


--
-- TOC entry 4893 (class 0 OID 16565)
-- Dependencies: 223
-- Data for Name: moderator; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.moderator (id_moderator, "Пользователь", "Дата начала должности", "Дата окончания должности") FROM stdin;
\.


--
-- TOC entry 4889 (class 0 OID 16490)
-- Dependencies: 219
-- Data for Name: status; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.status (id_status, "Статус") FROM stdin;
\.


--
-- TOC entry 4887 (class 0 OID 16465)
-- Dependencies: 217
-- Data for Name: type_violation; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.type_violation (id_type, "Вид") FROM stdin;
\.


--
-- TOC entry 4895 (class 0 OID 16578)
-- Dependencies: 225
-- Data for Name: user; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private."user" (id_user, "Фамилия", "Имя", "Отчество", "Данные", "Дата регистрации") FROM stdin;
\.


--
-- TOC entry 4891 (class 0 OID 16500)
-- Dependencies: 221
-- Data for Name: user_data; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.user_data (id_user_data, "Логин", "Пароль", "Электронная почта", "Аватар") FROM stdin;
\.


--
-- TOC entry 4897 (class 0 OID 16652)
-- Dependencies: 227
-- Data for Name: violation; Type: TABLE DATA; Schema: private; Owner: postgres
--

COPY private.violation (id_violation, "Статья", "Название", "Вид", "Наказание") FROM stdin;
\.


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 230
-- Name: application_data_id_application_data_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.application_data_id_application_data_seq', 1, false);


--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 232
-- Name: application_id_application_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.application_id_application_seq', 1, false);


--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 228
-- Name: district_id_district_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.district_id_district_seq', 1, false);


--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 222
-- Name: moderators_id_moderators_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.moderators_id_moderators_seq', 1, false);


--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 218
-- Name: statuses_id_status_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.statuses_id_status_seq', 5, true);


--
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 216
-- Name: types_violations_id_type_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.types_violations_id_type_seq', 1, true);


--
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_data_id_users_data_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.users_data_id_users_data_seq', 1, false);


--
-- TOC entry 4933 (class 0 OID 0)
-- Dependencies: 224
-- Name: users_id_users_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.users_id_users_seq', 1, false);


--
-- TOC entry 4934 (class 0 OID 0)
-- Dependencies: 226
-- Name: violations_id_violation_seq; Type: SEQUENCE SET; Schema: private; Owner: postgres
--

SELECT pg_catalog.setval('private.violations_id_violation_seq', 53, true);


--
-- TOC entry 4722 (class 2606 OID 16677)
-- Name: violation article_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violation
    ADD CONSTRAINT article_exist UNIQUE ("Статья");


--
-- TOC entry 4718 (class 2606 OID 16683)
-- Name: user data_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private."user"
    ADD CONSTRAINT data_exist UNIQUE ("Данные");


--
-- TOC entry 4728 (class 2606 OID 16675)
-- Name: district district_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.district
    ADD CONSTRAINT district_exist UNIQUE ("Район");


--
-- TOC entry 4710 (class 2606 OID 16681)
-- Name: user_data email_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.user_data
    ADD CONSTRAINT email_exist UNIQUE ("Электронная почта");


--
-- TOC entry 4688 (class 2606 OID 16542)
-- Name: type_violation empty_type; Type: CHECK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE private.type_violation
    ADD CONSTRAINT empty_type CHECK (("Вид" <> ''::text)) NOT VALID;


--
-- TOC entry 4732 (class 2606 OID 16735)
-- Name: application_data id_application_data_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application_data
    ADD CONSTRAINT id_application_data_exist PRIMARY KEY (id_application_data);


--
-- TOC entry 4734 (class 2606 OID 16760)
-- Name: application id_application_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT id_application_exist PRIMARY KEY (id_application);


--
-- TOC entry 4730 (class 2606 OID 16673)
-- Name: district id_district_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.district
    ADD CONSTRAINT id_district_exist PRIMARY KEY (id_district);


--
-- TOC entry 4716 (class 2606 OID 16571)
-- Name: moderator id_moderators_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderator
    ADD CONSTRAINT id_moderators_exist PRIMARY KEY (id_moderator);


--
-- TOC entry 4706 (class 2606 OID 16498)
-- Name: status id_status_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.status
    ADD CONSTRAINT id_status_exist PRIMARY KEY (id_status);


--
-- TOC entry 4702 (class 2606 OID 16473)
-- Name: type_violation id_type_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.type_violation
    ADD CONSTRAINT id_type_exist PRIMARY KEY (id_type);


--
-- TOC entry 4712 (class 2606 OID 16512)
-- Name: user_data id_user_data_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.user_data
    ADD CONSTRAINT id_user_data_exist PRIMARY KEY (id_user_data);


--
-- TOC entry 4720 (class 2606 OID 16597)
-- Name: user id_user_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private."user"
    ADD CONSTRAINT id_user_exist PRIMARY KEY (id_user);


--
-- TOC entry 4724 (class 2606 OID 16660)
-- Name: violation id_violation_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violation
    ADD CONSTRAINT id_violation_exist PRIMARY KEY (id_violation);


--
-- TOC entry 4714 (class 2606 OID 16514)
-- Name: user_data login_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.user_data
    ADD CONSTRAINT login_exist UNIQUE ("Логин");


--
-- TOC entry 4726 (class 2606 OID 16679)
-- Name: violation name_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violation
    ADD CONSTRAINT name_exist UNIQUE ("Название");


--
-- TOC entry 4708 (class 2606 OID 16687)
-- Name: status status_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.status
    ADD CONSTRAINT status_exist UNIQUE ("Статус");


--
-- TOC entry 4704 (class 2606 OID 16685)
-- Name: type_violation type_exist; Type: CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.type_violation
    ADD CONSTRAINT type_exist UNIQUE ("Вид");


--
-- TOC entry 4738 (class 2606 OID 16761)
-- Name: application applications_missing_data_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT applications_missing_data_fkey FOREIGN KEY ("Данные") REFERENCES private.application_data(id_application_data);


--
-- TOC entry 4739 (class 2606 OID 16766)
-- Name: application applications_missing_district_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT applications_missing_district_fkey FOREIGN KEY ("Район") REFERENCES private.district(id_district);


--
-- TOC entry 4740 (class 2606 OID 16771)
-- Name: application applications_missing_moderator_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT applications_missing_moderator_fkey FOREIGN KEY ("Модератор") REFERENCES private.moderator(id_moderator);


--
-- TOC entry 4741 (class 2606 OID 16776)
-- Name: application applications_missing_status_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT applications_missing_status_fkey FOREIGN KEY ("Статус") REFERENCES private.status(id_status);


--
-- TOC entry 4742 (class 2606 OID 16781)
-- Name: application applications_missing_user_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.application
    ADD CONSTRAINT applications_missing_user_fkey FOREIGN KEY ("Заявитель") REFERENCES private."user"(id_user);


--
-- TOC entry 4735 (class 2606 OID 16643)
-- Name: moderator moderators_missing_user; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.moderator
    ADD CONSTRAINT moderators_missing_user FOREIGN KEY ("Пользователь") REFERENCES private."user"(id_user) NOT VALID;


--
-- TOC entry 4736 (class 2606 OID 16590)
-- Name: user users_missing_users_data; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private."user"
    ADD CONSTRAINT users_missing_users_data FOREIGN KEY ("Данные") REFERENCES private.user_data(id_user_data);


--
-- TOC entry 4737 (class 2606 OID 16661)
-- Name: violation violations_missing_type_fkey; Type: FK CONSTRAINT; Schema: private; Owner: postgres
--

ALTER TABLE ONLY private.violation
    ADD CONSTRAINT violations_missing_type_fkey FOREIGN KEY ("Вид") REFERENCES private.type_violation(id_type);


-- Completed on 2024-10-26 19:19:53

--
-- PostgreSQL database dump complete
--

