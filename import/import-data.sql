--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 16.0

-- Started on 2024-02-24 11:18:39

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
-- TOC entry 218 (class 1255 OID 60174)
-- Name: service_audit_trigger_fnc(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.service_audit_trigger_fnc() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	IF NEW.description <> OLD.description THEN
		 INSERT INTO AUDIT_DATA(entity_id,entity_name,column_name,old_value,new_value,modified,modified_user_id)
		 VALUES(OLD.id,'SERVICE','DESCRIPTION',OLD.description,NEW.description,new.modified,new.modified_user_id);
	END IF;
	RETURN NEW;
END;$$;


ALTER FUNCTION public.service_audit_trigger_fnc() OWNER TO postgres;

--
-- TOC entry 219 (class 1255 OID 60175)
-- Name: service_provider_audit_trigger_fnc(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.service_provider_audit_trigger_fnc() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	IF NEW.name <> OLD.name THEN
		 INSERT INTO AUDIT_DATA(entity_id,entity_name,column_name,old_value,new_value,modified,modified_user_id)
		 VALUES(OLD.id,'SERVICE PROVIDER','NAME',OLD.name,NEW.name,old.modified,new.modified_user_id);
	END IF;
	RETURN NEW;
END;$$;


ALTER FUNCTION public.service_provider_audit_trigger_fnc() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 60147)
-- Name: audit_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_data (
    entity_id character varying(255),
    entity_name character varying(255),
    column_name character varying(255),
    old_value character varying(255),
    new_value character varying(255),
    modified timestamp with time zone,
    modified_user_id character varying(255)
);


ALTER TABLE public.audit_data OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 60144)
-- Name: ln_service_provider_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ln_service_provider_service (
    service_provider_id character varying NOT NULL,
    service_id character varying NOT NULL
);


ALTER TABLE public.ln_service_provider_service OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 60138)
-- Name: service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service (
    id character varying NOT NULL,
    description character varying NOT NULL,
    created timestamp with time zone,
    created_user_id character varying,
    modified timestamp with time zone,
    modified_user_id character varying
);


ALTER TABLE public.service OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 60141)
-- Name: service_provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service_provider (
    id character varying NOT NULL,
    name character varying NOT NULL,
    created timestamp with time zone,
    created_user_id character varying,
    modified timestamp with time zone,
    modified_user_id character varying
);


ALTER TABLE public.service_provider OWNER TO postgres;

--
-- TOC entry 3341 (class 0 OID 60147)
-- Dependencies: 217
-- Data for Name: audit_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.audit_data (entity_id, entity_name, column_name, old_value, new_value, modified, modified_user_id) FROM stdin;
a9303273-b241-4458-ae41-fee211b53696	SERVICE	DESCRIPTION	Moibtel na pretplatu	Uređaj uz ugovornu obvezu	2024-02-24 11:08:26.595546+01	PUBLIC
\.


--
-- TOC entry 3340 (class 0 OID 60144)
-- Dependencies: 216
-- Data for Name: ln_service_provider_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ln_service_provider_service (service_provider_id, service_id) FROM stdin;
c9bc5ae6-aa55-4efb-82fc-04d3fbad5feb	6e092a95-e0bf-4e81-8972-c68090877fd8
c9bc5ae6-aa55-4efb-82fc-04d3fbad5feb	571b88f9-3c34-437c-ab1f-a3678b4dc45c
c9bc5ae6-aa55-4efb-82fc-04d3fbad5feb	09c64285-a38f-4cde-9fbc-9e01ccbbf891
c2472367-ab37-4e1f-910b-08208e3f7faa	a4653edd-4664-410a-8986-f0ea0329b44d
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	09c64285-a38f-4cde-9fbc-9e01ccbbf891
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	571b88f9-3c34-437c-ab1f-a3678b4dc45c
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	fdfb46c3-33b7-474c-b6f9-468ddb3dda4d
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	6e092a95-e0bf-4e81-8972-c68090877fd8
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	a9303273-b241-4458-ae41-fee211b53696
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	a4653edd-4664-410a-8986-f0ea0329b44d
32a543b3-14cd-4324-96eb-04162d5b854a	09c64285-a38f-4cde-9fbc-9e01ccbbf891
32a543b3-14cd-4324-96eb-04162d5b854a	6e092a95-e0bf-4e81-8972-c68090877fd8
32a543b3-14cd-4324-96eb-04162d5b854a	fdfb46c3-33b7-474c-b6f9-468ddb3dda4d
32a543b3-14cd-4324-96eb-04162d5b854a	571b88f9-3c34-437c-ab1f-a3678b4dc45c
32a543b3-14cd-4324-96eb-04162d5b854a	a9303273-b241-4458-ae41-fee211b53696
32a543b3-14cd-4324-96eb-04162d5b854a	a4653edd-4664-410a-8986-f0ea0329b44d
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	6e092a95-e0bf-4e81-8972-c68090877fd8
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	fdfb46c3-33b7-474c-b6f9-468ddb3dda4d
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	09c64285-a38f-4cde-9fbc-9e01ccbbf891
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	a4653edd-4664-410a-8986-f0ea0329b44d
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	571b88f9-3c34-437c-ab1f-a3678b4dc45c
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	a9303273-b241-4458-ae41-fee211b53696
\.


--
-- TOC entry 3338 (class 0 OID 60138)
-- Dependencies: 214
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.service (id, description, created, created_user_id, modified, modified_user_id) FROM stdin;
fdfb46c3-33b7-474c-b6f9-468ddb3dda4d	Tarifa na pretplatu	2024-02-24 11:05:51.356774+01	PUBLIC	\N	\N
09c64285-a38f-4cde-9fbc-9e01ccbbf891	Internet	2024-02-24 11:05:58.622623+01	PUBLIC	\N	\N
6e092a95-e0bf-4e81-8972-c68090877fd8	Televizija	2024-02-24 11:06:03.319698+01	PUBLIC	\N	\N
571b88f9-3c34-437c-ab1f-a3678b4dc45c	Telefon	2024-02-24 11:06:08.872917+01	PUBLIC	\N	\N
a4653edd-4664-410a-8986-f0ea0329b44d	Tarifa na bonove	2024-02-24 11:06:26.180128+01	PUBLIC	\N	\N
a9303273-b241-4458-ae41-fee211b53696	Uređaj uz ugovornu obvezu	2024-02-24 11:05:43.675804+01	PUBLIC	2024-02-24 11:08:26.595546+01	PUBLIC
\.


--
-- TOC entry 3339 (class 0 OID 60141)
-- Dependencies: 215
-- Data for Name: service_provider; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.service_provider (id, name, created, created_user_id, modified, modified_user_id) FROM stdin;
c9bc5ae6-aa55-4efb-82fc-04d3fbad5feb	Iskon	2024-02-24 11:10:24.290142+01	PUBLIC	2024-02-24 11:14:36.081791+01	PUBLIC
c2472367-ab37-4e1f-910b-08208e3f7faa	BonBon	2024-02-24 11:10:17.177329+01	PUBLIC	2024-02-24 11:15:29.668679+01	PUBLIC
b576e1ce-7a4b-431e-a80a-1b45c3c4075a	Telemach	2024-02-24 11:10:06.381861+01	PUBLIC	2024-02-24 11:17:06.970555+01	PUBLIC
32a543b3-14cd-4324-96eb-04162d5b854a	T-com	2024-02-24 11:10:11.667749+01	PUBLIC	2024-02-24 11:17:30.197686+01	PUBLIC
22158c0c-ad71-4b63-97f8-a2afdd66d5b9	A1	2024-02-24 11:10:00.183943+01	PUBLIC	2024-02-24 11:17:44.840903+01	PUBLIC
\.


--
-- TOC entry 3191 (class 2606 OID 60173)
-- Name: ln_service_provider_service ln_service_provider_services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ln_service_provider_service
    ADD CONSTRAINT ln_service_provider_services_pkey PRIMARY KEY (service_provider_id, service_id);


--
-- TOC entry 3187 (class 2606 OID 60155)
-- Name: service service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);


--
-- TOC entry 3189 (class 2606 OID 60159)
-- Name: service_provider service_provider_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service_provider
    ADD CONSTRAINT service_provider_pkey PRIMARY KEY (id);


--
-- TOC entry 3194 (class 2620 OID 60176)
-- Name: service service_audit_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER service_audit_trigger AFTER INSERT OR DELETE OR UPDATE OF id, description, modified, modified_user_id ON public.service FOR EACH ROW EXECUTE FUNCTION public.service_audit_trigger_fnc();


--
-- TOC entry 3195 (class 2620 OID 60177)
-- Name: service_provider service_provider_audit_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER service_provider_audit_trigger AFTER INSERT OR DELETE OR UPDATE OF id, name, modified, modified_user_id ON public.service_provider FOR EACH ROW EXECUTE FUNCTION public.service_provider_audit_trigger_fnc();


--
-- TOC entry 3192 (class 2606 OID 60162)
-- Name: ln_service_provider_service fk_service_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ln_service_provider_service
    ADD CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES public.service(id) NOT VALID;


--
-- TOC entry 3193 (class 2606 OID 60167)
-- Name: ln_service_provider_service fk_service_provider_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ln_service_provider_service
    ADD CONSTRAINT fk_service_provider_id FOREIGN KEY (service_provider_id) REFERENCES public.service_provider(id) NOT VALID;


-- Completed on 2024-02-24 11:18:39

--
-- PostgreSQL database dump complete
--

