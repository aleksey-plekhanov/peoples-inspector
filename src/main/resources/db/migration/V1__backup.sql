--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-12-20 17:57:54

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
-- TOC entry 5 (class 2615 OID 17572)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 245 (class 1255 OID 25457)
-- Name: add_file(integer, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.add_file(IN id_app integer, IN filepath character varying, IN filetype character varying)
    LANGUAGE plpgsql
    AS $$
begin
    insert into public.file (id_application, "Путь", "Тип файла")
	values (id_app, filePath, fileType);

    commit;
end;$$;


ALTER PROCEDURE public.add_file(IN id_app integer, IN filepath character varying, IN filetype character varying) OWNER TO postgres;

--
-- TOC entry 232 (class 1255 OID 25432)
-- Name: application_add_violations(integer, character varying[]); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_add_violations(IN id_app integer, IN violations character varying[])
    LANGUAGE plpgsql
    AS $$
DECLARE
    viol varchar;
begin
    FOREACH viol SLICE 0 IN ARRAY violations LOOP
    insert into public.application_violation (id_application, "Статья")
    values (id_app, viol);
    END LOOP;

    commit;
end;$$;


ALTER PROCEDURE public.application_add_violations(IN id_app integer, IN violations character varying[]) OWNER TO postgres;

--
-- TOC entry 246 (class 1255 OID 25474)
-- Name: application_create(integer, character varying, text, timestamp without time zone, character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_create(IN applicant integer, IN title character varying, IN information text, IN date_violation timestamp without time zone, IN district character varying, IN address character varying, IN status character varying, OUT id_app integer)
    LANGUAGE plpgsql
    AS $$
begin
	insert into public.application ("Заявитель", "Название", "Информация", "Время нарушения", "Район", "Адрес", "Статус")
	values (applicant, title, information, date_violation, district, address, status)
	returning id_application into id_app;
	
    commit;
end;$$;


ALTER PROCEDURE public.application_create(IN applicant integer, IN title character varying, IN information text, IN date_violation timestamp without time zone, IN district character varying, IN address character varying, IN status character varying, OUT id_app integer) OWNER TO postgres;

--
-- TOC entry 244 (class 1255 OID 25434)
-- Name: application_get_data(integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_get_data(IN id_app integer, OUT filepath character varying[], OUT violations character varying[])
    LANGUAGE plpgsql
    AS $$
begin
	select ARRAY(select "Путь" from public.file
				where id_application = id_app) into filePath;
	select ARRAY(select "Статья" from public.application_violation
				where id_application = id_app) into violations;
	commit;
end;$$;


ALTER PROCEDURE public.application_get_data(IN id_app integer, OUT filepath character varying[], OUT violations character varying[]) OWNER TO postgres;

--
-- TOC entry 231 (class 1255 OID 25382)
-- Name: application_moderate(integer, integer, text, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_moderate(IN id_app integer, IN moderator integer, IN commentary text, IN status character varying)
    LANGUAGE plpgsql
    AS $$
begin
	update public.application
	set "Статус" = status, "Модератор" = moderator, "Комментарий модератора" = commentary, "Время проверки" = now()
	where id_application = id_app;

    commit;
end;$$;


ALTER PROCEDURE public.application_moderate(IN id_app integer, IN moderator integer, IN commentary text, IN status character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 225 (class 1259 OID 25385)
-- Name: application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application (
    id_application integer NOT NULL,
    "Заявитель" integer NOT NULL,
    "Название" character varying(50) NOT NULL,
    "Информация" text NOT NULL,
    "Время поступления" timestamp without time zone DEFAULT now() NOT NULL,
    "Время нарушения" timestamp without time zone NOT NULL,
    "Район" character varying(30) NOT NULL,
    "Адрес" character varying(100) NOT NULL,
    "Статус" character varying(25) NOT NULL,
    "Модератор" integer,
    "Комментарий модератора" text,
    "Время проверки" timestamp without time zone,
    CONSTRAINT empty_address CHECK ((("Адрес")::text <> ''::text)),
    CONSTRAINT empty_information CHECK (("Информация" <> ''::text)),
    CONSTRAINT empty_name CHECK ((("Название")::text <> ''::text))
);


ALTER TABLE public.application OWNER TO postgres;

--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE application; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application IS 'Заявления';


--
-- TOC entry 224 (class 1259 OID 25384)
-- Name: application_id_application_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_id_application_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.application_id_application_seq OWNER TO postgres;

--
-- TOC entry 4911 (class 0 OID 0)
-- Dependencies: 224
-- Name: application_id_application_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_id_application_seq OWNED BY public.application.id_application;


--
-- TOC entry 226 (class 1259 OID 25419)
-- Name: application_violation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_violation (
    id_application integer NOT NULL,
    "Статья" character varying(20) NOT NULL
);


ALTER TABLE public.application_violation OWNER TO postgres;

--
-- TOC entry 4912 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE application_violation; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_violation IS 'Нарушения заявления';


--
-- TOC entry 220 (class 1259 OID 18332)
-- Name: district; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.district (
    "Район" character varying(30) NOT NULL,
    CONSTRAINT empty_district CHECK ((("Район")::text <> ''::text))
);


ALTER TABLE public.district OWNER TO postgres;

--
-- TOC entry 4913 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE district; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.district IS 'Районы';


--
-- TOC entry 228 (class 1259 OID 25436)
-- Name: file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file (
    id_file integer NOT NULL,
    id_application integer NOT NULL,
    "Путь" character varying NOT NULL,
    "Тип файла" character varying(5) NOT NULL,
    CONSTRAINT empty_path CHECK ((("Путь")::text <> ''::text))
);


ALTER TABLE public.file OWNER TO postgres;

--
-- TOC entry 4914 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE file; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.file IS 'Файлы заявлений';


--
-- TOC entry 227 (class 1259 OID 25435)
-- Name: file_id_file_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.file_id_file_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.file_id_file_seq OWNER TO postgres;

--
-- TOC entry 4915 (class 0 OID 0)
-- Dependencies: 227
-- Name: file_id_file_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.file_id_file_seq OWNED BY public.file.id_file;


--
-- TOC entry 221 (class 1259 OID 18370)
-- Name: file_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file_type (
    "Тип файла" character varying(5) NOT NULL
);


ALTER TABLE public.file_type OWNER TO postgres;

--
-- TOC entry 4916 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE file_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.file_type IS 'Типы файла';


--
-- TOC entry 218 (class 1259 OID 18178)
-- Name: moderator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.moderator (
    id_moderator integer NOT NULL,
    "Пользователь" integer NOT NULL,
    "Дата начала должности" timestamp without time zone DEFAULT now() NOT NULL,
    "Дата окончания должности" timestamp without time zone
);


ALTER TABLE public.moderator OWNER TO postgres;

--
-- TOC entry 4917 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE moderator; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.moderator IS 'Модераторы';


--
-- TOC entry 217 (class 1259 OID 18177)
-- Name: moderator_id_moderator_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.moderator_id_moderator_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.moderator_id_moderator_seq OWNER TO postgres;

--
-- TOC entry 4918 (class 0 OID 0)
-- Dependencies: 217
-- Name: moderator_id_moderator_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.moderator_id_moderator_seq OWNED BY public.moderator.id_moderator;


--
-- TOC entry 219 (class 1259 OID 18326)
-- Name: status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status (
    "Статус" character varying(25) NOT NULL,
    CONSTRAINT empty_status CHECK ((("Статус")::text <> ''::text))
);


ALTER TABLE public.status OWNER TO postgres;

--
-- TOC entry 4919 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.status IS 'Статусы заявления';


--
-- TOC entry 230 (class 1259 OID 25459)
-- Name: user_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_data (
    id_user_data integer NOT NULL,
    "Логин" character varying(128) NOT NULL,
    "Пароль" character varying(128) NOT NULL,
    "Электронная почта" character varying(128) NOT NULL,
    "Аватар" character varying,
    CONSTRAINT empty_email CHECK ((("Электронная почта")::text <> ''::text)),
    CONSTRAINT empty_login CHECK ((("Логин")::text <> ''::text)),
    CONSTRAINT empty_password CHECK ((("Пароль")::text <> ''::text))
);


ALTER TABLE public.user_data OWNER TO postgres;

--
-- TOC entry 4920 (class 0 OID 0)
-- Dependencies: 230
-- Name: TABLE user_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.user_data IS 'Данные пользователей';


--
-- TOC entry 229 (class 1259 OID 25458)
-- Name: user_data_id_user_data_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_data_id_user_data_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_data_id_user_data_seq OWNER TO postgres;

--
-- TOC entry 4921 (class 0 OID 0)
-- Dependencies: 229
-- Name: user_data_id_user_data_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_data_id_user_data_seq OWNED BY public.user_data.id_user_data;


--
-- TOC entry 216 (class 1259 OID 18151)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id_user integer NOT NULL,
    "Фамилия" character varying(50) NOT NULL,
    "Имя" character varying(50) NOT NULL,
    "Отчество" character varying(50),
    "Данные" integer NOT NULL,
    "Дата регистрации" timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT empty_name CHECK ((("Имя")::text <> ''::text)),
    CONSTRAINT empty_surname CHECK ((("Фамилия")::text <> ''::text))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.users IS 'Пользователи';


--
-- TOC entry 215 (class 1259 OID 18150)
-- Name: users_id_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_user_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_user_seq OWNER TO postgres;

--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_user_seq OWNED BY public.users.id_user;


--
-- TOC entry 223 (class 1259 OID 18593)
-- Name: violation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.violation (
    "Статья" character varying(20) NOT NULL,
    "Название" text NOT NULL,
    "Вид" character varying(128) NOT NULL,
    "Наказание" text NOT NULL,
    CONSTRAINT empty_name CHECK (("Название" <> ''::text))
);


ALTER TABLE public.violation OWNER TO postgres;

--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE violation; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.violation IS 'Правонарушения';


--
-- TOC entry 222 (class 1259 OID 18588)
-- Name: violation_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.violation_type (
    "Вид" character varying(128) NOT NULL
);


ALTER TABLE public.violation_type OWNER TO postgres;

--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE violation_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.violation_type IS 'Виды правонарушений';


--
-- TOC entry 4687 (class 2604 OID 25388)
-- Name: application id_application; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application ALTER COLUMN id_application SET DEFAULT nextval('public.application_id_application_seq'::regclass);


--
-- TOC entry 4689 (class 2604 OID 25439)
-- Name: file id_file; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file ALTER COLUMN id_file SET DEFAULT nextval('public.file_id_file_seq'::regclass);


--
-- TOC entry 4685 (class 2604 OID 18181)
-- Name: moderator id_moderator; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator ALTER COLUMN id_moderator SET DEFAULT nextval('public.moderator_id_moderator_seq'::regclass);


--
-- TOC entry 4690 (class 2604 OID 25462)
-- Name: user_data id_user_data; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data ALTER COLUMN id_user_data SET DEFAULT nextval('public.user_data_id_user_data_seq'::regclass);


--
-- TOC entry 4683 (class 2604 OID 18154)
-- Name: users id_user; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id_user SET DEFAULT nextval('public.users_id_user_seq'::regclass);


--
-- TOC entry 4898 (class 0 OID 25385)
-- Dependencies: 225
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application (id_application, "Заявитель", "Название", "Информация", "Время поступления", "Время нарушения", "Район", "Адрес", "Статус", "Модератор", "Комментарий модератора", "Время проверки") FROM stdin;
\.


--
-- TOC entry 4899 (class 0 OID 25419)
-- Dependencies: 226
-- Data for Name: application_violation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_violation (id_application, "Статья") FROM stdin;
\.


--
-- TOC entry 4893 (class 0 OID 18332)
-- Dependencies: 220
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.district ("Район") FROM stdin;
\.


--
-- TOC entry 4901 (class 0 OID 25436)
-- Dependencies: 228
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file (id_file, id_application, "Путь", "Тип файла") FROM stdin;
\.


--
-- TOC entry 4894 (class 0 OID 18370)
-- Dependencies: 221
-- Data for Name: file_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file_type ("Тип файла") FROM stdin;
\.


--
-- TOC entry 4891 (class 0 OID 18178)
-- Dependencies: 218
-- Data for Name: moderator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.moderator (id_moderator, "Пользователь", "Дата начала должности", "Дата окончания должности") FROM stdin;
\.


--
-- TOC entry 4892 (class 0 OID 18326)
-- Dependencies: 219
-- Data for Name: status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.status ("Статус") FROM stdin;
\.


--
-- TOC entry 4903 (class 0 OID 25459)
-- Dependencies: 230
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_data (id_user_data, "Логин", "Пароль", "Электронная почта", "Аватар") FROM stdin;
\.


--
-- TOC entry 4889 (class 0 OID 18151)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id_user, "Фамилия", "Имя", "Отчество", "Данные", "Дата регистрации") FROM stdin;
\.


--
-- TOC entry 4896 (class 0 OID 18593)
-- Dependencies: 223
-- Data for Name: violation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.violation ("Статья", "Название", "Вид", "Наказание") FROM stdin;
\.


--
-- TOC entry 4895 (class 0 OID 18588)
-- Dependencies: 222
-- Data for Name: violation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.violation_type ("Вид") FROM stdin;
\.


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 224
-- Name: application_id_application_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.application_id_application_seq', 1, false);


--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 227
-- Name: file_id_file_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.file_id_file_seq', 1, false);


--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 217
-- Name: moderator_id_moderator_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.moderator_id_moderator_seq', 1, false);


--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 229
-- Name: user_data_id_user_data_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_data_id_user_data_seq', 1, false);


--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_user_seq', 1, false);


--
-- TOC entry 4722 (class 2606 OID 25396)
-- Name: application application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT application_pkey PRIMARY KEY (id_application);


--
-- TOC entry 4724 (class 2606 OID 25398)
-- Name: application application_Заявитель_Название_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT "application_Заявитель_Название_key" UNIQUE ("Заявитель", "Название");


--
-- TOC entry 4712 (class 2606 OID 18337)
-- Name: district district_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT district_pkey PRIMARY KEY ("Район");


--
-- TOC entry 4726 (class 2606 OID 25444)
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (id_file);


--
-- TOC entry 4714 (class 2606 OID 18374)
-- Name: file_type file_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file_type
    ADD CONSTRAINT file_type_pkey PRIMARY KEY ("Тип файла");


--
-- TOC entry 4728 (class 2606 OID 25446)
-- Name: file file_Путь_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT "file_Путь_key" UNIQUE ("Путь");


--
-- TOC entry 4708 (class 2606 OID 18184)
-- Name: moderator moderator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT moderator_pkey PRIMARY KEY (id_moderator);


--
-- TOC entry 4710 (class 2606 OID 18331)
-- Name: status status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_pkey PRIMARY KEY ("Статус");


--
-- TOC entry 4730 (class 2606 OID 25469)
-- Name: user_data user_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id_user_data);


--
-- TOC entry 4732 (class 2606 OID 25471)
-- Name: user_data user_data_Логин_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT "user_data_Логин_key" UNIQUE ("Логин");


--
-- TOC entry 4734 (class 2606 OID 25473)
-- Name: user_data user_data_Электронная почта_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT "user_data_Электронная почта_key" UNIQUE ("Электронная почта");


--
-- TOC entry 4704 (class 2606 OID 18159)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 4706 (class 2606 OID 18161)
-- Name: users users_Данные_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "users_Данные_key" UNIQUE ("Данные");


--
-- TOC entry 4718 (class 2606 OID 18600)
-- Name: violation violation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT violation_pkey PRIMARY KEY ("Статья");


--
-- TOC entry 4716 (class 2606 OID 18592)
-- Name: violation_type violation_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation_type
    ADD CONSTRAINT violation_type_pkey PRIMARY KEY ("Вид");


--
-- TOC entry 4720 (class 2606 OID 18602)
-- Name: violation violation_Название_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT "violation_Название_key" UNIQUE ("Название");


--
-- TOC entry 4741 (class 2606 OID 25422)
-- Name: application_violation adviol_missing_application_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_violation
    ADD CONSTRAINT adviol_missing_application_fkey FOREIGN KEY (id_application) REFERENCES public.application(id_application);


--
-- TOC entry 4742 (class 2606 OID 25427)
-- Name: application_violation adviol_missing_violation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_violation
    ADD CONSTRAINT adviol_missing_violation_fkey FOREIGN KEY ("Статья") REFERENCES public.violation("Статья");


--
-- TOC entry 4737 (class 2606 OID 25399)
-- Name: application applications_missing_district_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_district_fkey FOREIGN KEY ("Район") REFERENCES public.district("Район");


--
-- TOC entry 4738 (class 2606 OID 25404)
-- Name: application applications_missing_moderator_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_moderator_fkey FOREIGN KEY ("Модератор") REFERENCES public.moderator(id_moderator);


--
-- TOC entry 4739 (class 2606 OID 25409)
-- Name: application applications_missing_status_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_status_fkey FOREIGN KEY ("Статус") REFERENCES public.status("Статус");


--
-- TOC entry 4740 (class 2606 OID 25414)
-- Name: application applications_missing_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_user_fkey FOREIGN KEY ("Заявитель") REFERENCES public.users(id_user);


--
-- TOC entry 4743 (class 2606 OID 25447)
-- Name: file file_missing_application_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_missing_application_fkey FOREIGN KEY (id_application) REFERENCES public.application(id_application);


--
-- TOC entry 4744 (class 2606 OID 25452)
-- Name: file file_missing_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_missing_type_fkey FOREIGN KEY ("Тип файла") REFERENCES public.file_type("Тип файла");


--
-- TOC entry 4735 (class 2606 OID 18185)
-- Name: moderator moderators_missing_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT moderators_missing_user FOREIGN KEY ("Пользователь") REFERENCES public.users(id_user);


--
-- TOC entry 4736 (class 2606 OID 18603)
-- Name: violation violations_missing_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT violations_missing_type_fkey FOREIGN KEY ("Вид") REFERENCES public.violation_type("Вид");


--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2024-12-20 17:57:54

--
-- PostgreSQL database dump complete
--

