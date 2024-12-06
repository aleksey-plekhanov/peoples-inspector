--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-12-05 19:53:41

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
-- TOC entry 250 (class 1255 OID 18293)
-- Name: application_create(integer, integer, integer, integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_create(IN applicant integer, IN appdata integer, IN district integer, IN status integer)
    LANGUAGE plpgsql
    AS $$
begin
	insert into public.application ("Заявитель", "Данные", "Район", "Статус")
	values (applicant, appData, district, status);

    commit;
end;$$;


ALTER PROCEDURE public.application_create(IN applicant integer, IN appdata integer, IN district integer, IN status integer) OWNER TO postgres;

--
-- TOC entry 237 (class 1255 OID 18291)
-- Name: application_data_create(text); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_data_create(IN information text)
    LANGUAGE plpgsql
    AS $$
begin
	insert into public.application_data ("Информация")
	values (information)
	returning id_application_data;
	
    commit;
end;$$;


ALTER PROCEDURE public.application_data_create(IN information text) OWNER TO postgres;

--
-- TOC entry 249 (class 1255 OID 18292)
-- Name: application_data_fill(integer, character varying[], character varying[], character varying[], integer[]); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_data_fill(IN id_app_data integer, IN audiopath character varying[], IN photopath character varying[], IN videopath character varying[], IN violations integer[])
    LANGUAGE plpgsql
    AS $$
DECLARE
	tempPath varchar;
	viol int;
begin
	FOREACH tempPath SLICE 0 IN ARRAY audioPath LOOP
    insert into public.application_data_audio (id_application_data, "Аудио")
	values (id_app_data, tempPath);
  	END LOOP;

	FOREACH tempPath SLICE 0 IN ARRAY photoPath LOOP
    insert into public.application_data_photo (id_application_data, "Фото")
	values (id_app_data, tempPath);
  	END LOOP;

	FOREACH tempPath SLICE 0 IN ARRAY videoPath LOOP
    insert into public.application_data_video (id_application_data, "Видео")
	values (id_app_data, tempPath);
  	END LOOP;

	FOREACH viol SLICE 0 IN ARRAY violations LOOP
    insert into public.application_data_violation (id_application_data, id_violation)
	values (id_app_data, viol);
  	END LOOP;

    commit;
end;$$;


ALTER PROCEDURE public.application_data_fill(IN id_app_data integer, IN audiopath character varying[], IN photopath character varying[], IN videopath character varying[], IN violations integer[]) OWNER TO postgres;

--
-- TOC entry 252 (class 1255 OID 18295)
-- Name: application_get_data(integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_get_data(IN id_app_data integer, OUT audiopath character varying[], OUT photopath character varying[], OUT videopath character varying[], OUT violations integer[])
    LANGUAGE plpgsql
    AS $$
begin
	select ARRAY(select "Аудио" from public.application_data_audio
				where id_application_data = id_app_data) into audioPath;
	select ARRAY(select "Видео" from public.application_data_photo
				where id_application_data = id_app_data) into photoPath;
	select ARRAY(select "Фото" from public.application_data_video
				where id_application_data = id_app_data) into videoPath;
	select ARRAY(select id_violation from public.application_data_violation
				where id_application_data = id_app_data) into violations;
	commit;
end;$$;


ALTER PROCEDURE public.application_get_data(IN id_app_data integer, OUT audiopath character varying[], OUT photopath character varying[], OUT videopath character varying[], OUT violations integer[]) OWNER TO postgres;

--
-- TOC entry 251 (class 1255 OID 18294)
-- Name: application_moderate(integer, integer, text); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.application_moderate(IN id_app integer, IN moderator integer, IN commentary text)
    LANGUAGE plpgsql
    AS $$
begin
	update public.application
	set "Модератор" = moderator, "Комментарий модератора" = commentary
	where id_application = id_app;

    commit;
end;$$;


ALTER PROCEDURE public.application_moderate(IN id_app integer, IN moderator integer, IN commentary text) OWNER TO postgres;

--
-- TOC entry 253 (class 1255 OID 18296)
-- Name: violation_find(character varying, text, integer, text); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.violation_find(IN article character varying, IN title text, IN type_viol integer, IN punishment text, OUT id_viol integer[])
    LANGUAGE plpgsql
    AS $$
begin
	if type_viol != null then	
		select ARRAY(select id_violation from public.violation
				where "Статья" = '%' + article + '%' 
				and "Название" = '%' + title + '%'
				and "Вид" = type_viol
				and "Наказание" = '%' + punishment + '%') into id_viol;
	else
		select ARRAY(select id_violation from public.violation
				where "Статья" = '%' + article + '%' 
				and "Название" = '%' + title + '%'
				and "Наказание" = '%' + punishment + '%') into id_viol;
	end if;
	
	commit;
end;$$;


ALTER PROCEDURE public.violation_find(IN article character varying, IN title text, IN type_viol integer, IN punishment text, OUT id_viol integer[]) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 236 (class 1259 OID 18257)
-- Name: application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application (
    id_application integer NOT NULL,
    "Заявитель" integer NOT NULL,
    "Данные" integer NOT NULL,
    "Время поступления" timestamp without time zone DEFAULT now() NOT NULL,
    "Район" integer NOT NULL,
    "Статус" integer NOT NULL,
    "Модератор" integer,
    "Комментарий модератора" text,
    "Время проверки" timestamp without time zone
);


ALTER TABLE public.application OWNER TO postgres;

--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 236
-- Name: TABLE application; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application IS 'Заявления';


--
-- TOC entry 230 (class 1259 OID 18201)
-- Name: application_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_data (
    id_application_data integer NOT NULL,
    "Информация" text NOT NULL,
    CONSTRAINT empty_information CHECK (("Информация" <> ''::text))
);


ALTER TABLE public.application_data OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 230
-- Name: TABLE application_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_data IS 'Данные заявлений';


--
-- TOC entry 231 (class 1259 OID 18210)
-- Name: application_data_audio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_data_audio (
    id_application_data integer NOT NULL,
    "Аудио" character varying(2083) NOT NULL,
    CONSTRAINT empty_information CHECK ((("Аудио")::text <> ''::text))
);


ALTER TABLE public.application_data_audio OWNER TO postgres;

--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 231
-- Name: TABLE application_data_audio; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_data_audio IS 'Аудио заявлений';


--
-- TOC entry 229 (class 1259 OID 18200)
-- Name: application_data_id_application_data_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_data_id_application_data_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.application_data_id_application_data_seq OWNER TO postgres;

--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 229
-- Name: application_data_id_application_data_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_data_id_application_data_seq OWNED BY public.application_data.id_application_data;


--
-- TOC entry 233 (class 1259 OID 18232)
-- Name: application_data_photo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_data_photo (
    id_application_data integer NOT NULL,
    "Фото" character varying(2083) NOT NULL,
    CONSTRAINT empty_photo CHECK ((("Фото")::text <> ''::text))
);


ALTER TABLE public.application_data_photo OWNER TO postgres;

--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 233
-- Name: TABLE application_data_photo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_data_photo IS 'Фото заявлений';


--
-- TOC entry 232 (class 1259 OID 18221)
-- Name: application_data_video; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_data_video (
    id_application_data integer NOT NULL,
    "Видео" character varying(2083) NOT NULL,
    CONSTRAINT empty_video CHECK ((("Видео")::text <> ''::text))
);


ALTER TABLE public.application_data_video OWNER TO postgres;

--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 232
-- Name: TABLE application_data_video; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_data_video IS 'Видео заявлений';


--
-- TOC entry 234 (class 1259 OID 18243)
-- Name: application_data_violation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_data_violation (
    id_application_data integer NOT NULL,
    id_violation integer NOT NULL
);


ALTER TABLE public.application_data_violation OWNER TO postgres;

--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 234
-- Name: TABLE application_data_violation; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_data_violation IS 'Нарушения заявления';


--
-- TOC entry 235 (class 1259 OID 18256)
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
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 235
-- Name: application_id_application_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_id_application_seq OWNED BY public.application.id_application;


--
-- TOC entry 228 (class 1259 OID 18191)
-- Name: district; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.district (
    id_district integer NOT NULL,
    "Район" character varying(50) NOT NULL,
    CONSTRAINT empty_district CHECK ((("Район")::text <> ''::text))
);


ALTER TABLE public.district OWNER TO postgres;

--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE district; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.district IS 'Районы';


--
-- TOC entry 227 (class 1259 OID 18190)
-- Name: district_id_district_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.district_id_district_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.district_id_district_seq OWNER TO postgres;

--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 227
-- Name: district_id_district_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.district_id_district_seq OWNED BY public.district.id_district;


--
-- TOC entry 226 (class 1259 OID 18178)
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
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE moderator; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.moderator IS 'Модераторы';


--
-- TOC entry 225 (class 1259 OID 18177)
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
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 225
-- Name: moderator_id_moderator_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.moderator_id_moderator_seq OWNED BY public.moderator.id_moderator;


--
-- TOC entry 224 (class 1259 OID 18168)
-- Name: status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status (
    id_status integer NOT NULL,
    "Статус" character varying(50) NOT NULL,
    CONSTRAINT empty_status CHECK ((("Статус")::text <> ''::text))
);


ALTER TABLE public.status OWNER TO postgres;

--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.status IS 'Статусы заявления';


--
-- TOC entry 223 (class 1259 OID 18167)
-- Name: status_id_status_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.status_id_status_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.status_id_status_seq OWNER TO postgres;

--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 223
-- Name: status_id_status_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.status_id_status_seq OWNED BY public.status.id_status;


--
-- TOC entry 220 (class 1259 OID 18135)
-- Name: user_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_data (
    id_user_data integer NOT NULL,
    "Логин" character varying(128) NOT NULL,
    "Пароль" character varying(128) NOT NULL,
    "Электронная почта" character varying(128) NOT NULL,
    "Аватар" bytea,
    CONSTRAINT empty_email CHECK ((("Электронная почта")::text <> ''::text)),
    CONSTRAINT empty_login CHECK ((("Логин")::text <> ''::text)),
    CONSTRAINT empty_password CHECK ((("Пароль")::text <> ''::text))
);


ALTER TABLE public.user_data OWNER TO postgres;

--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE user_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.user_data IS 'Данные пользователей';


--
-- TOC entry 219 (class 1259 OID 18134)
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
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 219
-- Name: user_data_id_user_data_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_data_id_user_data_seq OWNED BY public.user_data.id_user_data;


--
-- TOC entry 222 (class 1259 OID 18151)
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
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.users IS 'Пользователи';


--
-- TOC entry 221 (class 1259 OID 18150)
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
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_user_seq OWNED BY public.users.id_user;


--
-- TOC entry 218 (class 1259 OID 18118)
-- Name: violation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.violation (
    id_violation integer NOT NULL,
    "Статья" character varying(20) NOT NULL,
    "Название" text NOT NULL,
    "Вид" integer NOT NULL,
    "Наказание" text NOT NULL,
    CONSTRAINT empty_name CHECK (("Название" <> ''::text))
);


ALTER TABLE public.violation OWNER TO postgres;

--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE violation; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.violation IS 'Правонарушения';


--
-- TOC entry 217 (class 1259 OID 18117)
-- Name: violation_id_violation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.violation_id_violation_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.violation_id_violation_seq OWNER TO postgres;

--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 217
-- Name: violation_id_violation_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.violation_id_violation_seq OWNED BY public.violation.id_violation;


--
-- TOC entry 216 (class 1259 OID 18109)
-- Name: violation_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.violation_type (
    id_type integer NOT NULL,
    "Вид" character varying(128) NOT NULL
);


ALTER TABLE public.violation_type OWNER TO postgres;

--
-- TOC entry 4956 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE violation_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.violation_type IS 'Виды правонарушений';


--
-- TOC entry 215 (class 1259 OID 18108)
-- Name: violation_type_id_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.violation_type_id_type_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.violation_type_id_type_seq OWNER TO postgres;

--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 215
-- Name: violation_type_id_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.violation_type_id_type_seq OWNED BY public.violation_type.id_type;


--
-- TOC entry 4706 (class 2604 OID 18260)
-- Name: application id_application; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application ALTER COLUMN id_application SET DEFAULT nextval('public.application_id_application_seq'::regclass);


--
-- TOC entry 4705 (class 2604 OID 18204)
-- Name: application_data id_application_data; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data ALTER COLUMN id_application_data SET DEFAULT nextval('public.application_data_id_application_data_seq'::regclass);


--
-- TOC entry 4704 (class 2604 OID 18194)
-- Name: district id_district; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.district ALTER COLUMN id_district SET DEFAULT nextval('public.district_id_district_seq'::regclass);


--
-- TOC entry 4702 (class 2604 OID 18181)
-- Name: moderator id_moderator; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator ALTER COLUMN id_moderator SET DEFAULT nextval('public.moderator_id_moderator_seq'::regclass);


--
-- TOC entry 4701 (class 2604 OID 18171)
-- Name: status id_status; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status ALTER COLUMN id_status SET DEFAULT nextval('public.status_id_status_seq'::regclass);


--
-- TOC entry 4698 (class 2604 OID 18138)
-- Name: user_data id_user_data; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data ALTER COLUMN id_user_data SET DEFAULT nextval('public.user_data_id_user_data_seq'::regclass);


--
-- TOC entry 4699 (class 2604 OID 18154)
-- Name: users id_user; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id_user SET DEFAULT nextval('public.users_id_user_seq'::regclass);


--
-- TOC entry 4697 (class 2604 OID 18121)
-- Name: violation id_violation; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation ALTER COLUMN id_violation SET DEFAULT nextval('public.violation_id_violation_seq'::regclass);


--
-- TOC entry 4696 (class 2604 OID 18112)
-- Name: violation_type id_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation_type ALTER COLUMN id_type SET DEFAULT nextval('public.violation_type_id_type_seq'::regclass);


--
-- TOC entry 4929 (class 0 OID 18257)
-- Dependencies: 236
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application (id_application, "Заявитель", "Данные", "Время поступления", "Район", "Статус", "Модератор", "Комментарий модератора", "Время проверки") FROM stdin;
\.


--
-- TOC entry 4923 (class 0 OID 18201)
-- Dependencies: 230
-- Data for Name: application_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_data (id_application_data, "Информация") FROM stdin;
\.


--
-- TOC entry 4924 (class 0 OID 18210)
-- Dependencies: 231
-- Data for Name: application_data_audio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_data_audio (id_application_data, "Аудио") FROM stdin;
\.


--
-- TOC entry 4926 (class 0 OID 18232)
-- Dependencies: 233
-- Data for Name: application_data_photo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_data_photo (id_application_data, "Фото") FROM stdin;
\.


--
-- TOC entry 4925 (class 0 OID 18221)
-- Dependencies: 232
-- Data for Name: application_data_video; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_data_video (id_application_data, "Видео") FROM stdin;
\.


--
-- TOC entry 4927 (class 0 OID 18243)
-- Dependencies: 234
-- Data for Name: application_data_violation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application_data_violation (id_application_data, id_violation) FROM stdin;
\.


--
-- TOC entry 4921 (class 0 OID 18191)
-- Dependencies: 228
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.district (id_district, "Район") FROM stdin;
\.


--
-- TOC entry 4919 (class 0 OID 18178)
-- Dependencies: 226
-- Data for Name: moderator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.moderator (id_moderator, "Пользователь", "Дата начала должности", "Дата окончания должности") FROM stdin;
\.


--
-- TOC entry 4917 (class 0 OID 18168)
-- Dependencies: 224
-- Data for Name: status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.status (id_status, "Статус") FROM stdin;
\.


--
-- TOC entry 4913 (class 0 OID 18135)
-- Dependencies: 220
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_data (id_user_data, "Логин", "Пароль", "Электронная почта", "Аватар") FROM stdin;
\.


--
-- TOC entry 4915 (class 0 OID 18151)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id_user, "Фамилия", "Имя", "Отчество", "Данные", "Дата регистрации") FROM stdin;
\.


--
-- TOC entry 4911 (class 0 OID 18118)
-- Dependencies: 218
-- Data for Name: violation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.violation (id_violation, "Статья", "Название", "Вид", "Наказание") FROM stdin;
\.


--
-- TOC entry 4909 (class 0 OID 18109)
-- Dependencies: 216
-- Data for Name: violation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.violation_type (id_type, "Вид") FROM stdin;
\.


--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 229
-- Name: application_data_id_application_data_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.application_data_id_application_data_seq', 1, false);


--
-- TOC entry 4959 (class 0 OID 0)
-- Dependencies: 235
-- Name: application_id_application_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.application_id_application_seq', 1, false);


--
-- TOC entry 4960 (class 0 OID 0)
-- Dependencies: 227
-- Name: district_id_district_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.district_id_district_seq', 1, false);


--
-- TOC entry 4961 (class 0 OID 0)
-- Dependencies: 225
-- Name: moderator_id_moderator_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.moderator_id_moderator_seq', 1, false);


--
-- TOC entry 4962 (class 0 OID 0)
-- Dependencies: 223
-- Name: status_id_status_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.status_id_status_seq', 1, false);


--
-- TOC entry 4963 (class 0 OID 0)
-- Dependencies: 219
-- Name: user_data_id_user_data_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_data_id_user_data_seq', 1, false);


--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_user_seq', 1, false);


--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 217
-- Name: violation_id_violation_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.violation_id_violation_seq', 1, false);


--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 215
-- Name: violation_type_id_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.violation_type_id_type_seq', 1, false);


--
-- TOC entry 4749 (class 2606 OID 18209)
-- Name: application_data application_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data
    ADD CONSTRAINT application_data_pkey PRIMARY KEY (id_application_data);


--
-- TOC entry 4751 (class 2606 OID 18265)
-- Name: application application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT application_pkey PRIMARY KEY (id_application);


--
-- TOC entry 4745 (class 2606 OID 18197)
-- Name: district district_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT district_pkey PRIMARY KEY (id_district);


--
-- TOC entry 4747 (class 2606 OID 18199)
-- Name: district district_Район_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT "district_Район_key" UNIQUE ("Район");


--
-- TOC entry 4743 (class 2606 OID 18184)
-- Name: moderator moderator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT moderator_pkey PRIMARY KEY (id_moderator);


--
-- TOC entry 4739 (class 2606 OID 18174)
-- Name: status status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id_status);


--
-- TOC entry 4741 (class 2606 OID 18176)
-- Name: status status_Статус_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT "status_Статус_key" UNIQUE ("Статус");


--
-- TOC entry 4729 (class 2606 OID 18145)
-- Name: user_data user_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id_user_data);


--
-- TOC entry 4731 (class 2606 OID 18147)
-- Name: user_data user_data_Логин_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT "user_data_Логин_key" UNIQUE ("Логин");


--
-- TOC entry 4733 (class 2606 OID 18149)
-- Name: user_data user_data_Электронная почта_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT "user_data_Электронная почта_key" UNIQUE ("Электронная почта");


--
-- TOC entry 4735 (class 2606 OID 18159)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 4737 (class 2606 OID 18161)
-- Name: users users_Данные_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "users_Данные_key" UNIQUE ("Данные");


--
-- TOC entry 4725 (class 2606 OID 18126)
-- Name: violation violation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT violation_pkey PRIMARY KEY (id_violation);


--
-- TOC entry 4721 (class 2606 OID 18114)
-- Name: violation_type violation_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation_type
    ADD CONSTRAINT violation_type_pkey PRIMARY KEY (id_type);


--
-- TOC entry 4723 (class 2606 OID 18116)
-- Name: violation_type violation_type_Вид_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation_type
    ADD CONSTRAINT "violation_type_Вид_key" UNIQUE ("Вид");


--
-- TOC entry 4727 (class 2606 OID 18128)
-- Name: violation violation_Название_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT "violation_Название_key" UNIQUE ("Название");


--
-- TOC entry 4755 (class 2606 OID 18216)
-- Name: application_data_audio ada_missing_data_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data_audio
    ADD CONSTRAINT ada_missing_data_fkey FOREIGN KEY (id_application_data) REFERENCES public.application_data(id_application_data);


--
-- TOC entry 4757 (class 2606 OID 18238)
-- Name: application_data_photo adp_missing_data_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data_photo
    ADD CONSTRAINT adp_missing_data_fkey FOREIGN KEY (id_application_data) REFERENCES public.application_data(id_application_data);


--
-- TOC entry 4756 (class 2606 OID 18227)
-- Name: application_data_video advid_missing_data_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data_video
    ADD CONSTRAINT advid_missing_data_fkey FOREIGN KEY (id_application_data) REFERENCES public.application_data(id_application_data);


--
-- TOC entry 4758 (class 2606 OID 18246)
-- Name: application_data_violation adviol_missing_data_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data_violation
    ADD CONSTRAINT adviol_missing_data_fkey FOREIGN KEY (id_application_data) REFERENCES public.application_data(id_application_data);


--
-- TOC entry 4759 (class 2606 OID 18251)
-- Name: application_data_violation adviol_missing_violation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_data_violation
    ADD CONSTRAINT adviol_missing_violation_fkey FOREIGN KEY (id_violation) REFERENCES public.violation(id_violation);


--
-- TOC entry 4760 (class 2606 OID 18266)
-- Name: application applications_missing_data_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_data_fkey FOREIGN KEY ("Данные") REFERENCES public.application_data(id_application_data);


--
-- TOC entry 4761 (class 2606 OID 18271)
-- Name: application applications_missing_district_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_district_fkey FOREIGN KEY ("Район") REFERENCES public.district(id_district);


--
-- TOC entry 4762 (class 2606 OID 18276)
-- Name: application applications_missing_moderator_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_moderator_fkey FOREIGN KEY ("Модератор") REFERENCES public.moderator(id_moderator);


--
-- TOC entry 4763 (class 2606 OID 18281)
-- Name: application applications_missing_status_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_status_fkey FOREIGN KEY ("Статус") REFERENCES public.status(id_status);


--
-- TOC entry 4764 (class 2606 OID 18286)
-- Name: application applications_missing_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT applications_missing_user_fkey FOREIGN KEY ("Заявитель") REFERENCES public.users(id_user);


--
-- TOC entry 4754 (class 2606 OID 18185)
-- Name: moderator moderators_missing_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderator
    ADD CONSTRAINT moderators_missing_user FOREIGN KEY ("Пользователь") REFERENCES public.users(id_user);


--
-- TOC entry 4753 (class 2606 OID 18162)
-- Name: users users_missing_users_data; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_missing_users_data FOREIGN KEY ("Данные") REFERENCES public.user_data(id_user_data);


--
-- TOC entry 4752 (class 2606 OID 18129)
-- Name: violation violations_missing_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.violation
    ADD CONSTRAINT violations_missing_type_fkey FOREIGN KEY ("Вид") REFERENCES public.violation_type(id_type);


--
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2024-12-05 19:53:42

--
-- PostgreSQL database dump complete
--

