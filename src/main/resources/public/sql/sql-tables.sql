-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


DROP TABLE IF EXISTS public.addresses CASCADE;
DROP TABLE IF EXISTS public.cities CASCADE;
DROP TABLE IF EXISTS public.order_lines CASCADE;
DROP TABLE IF EXISTS public.orders CASCADE;
DROP TABLE IF EXISTS public.postal_code CASCADE;
DROP TABLE IF EXISTS public.product CASCADE;
DROP TABLE IF EXISTS public.product_variant CASCADE;

CREATE TABLE IF NOT EXISTS public.addresses
(
    addresses_id serial NOT NULL,
    address character varying(64) COLLATE pg_catalog."default" NOT NULL,
    postal_code_id integer NOT NULL,
    city_id integer NOT NULL,
    CONSTRAINT addresses_pkey PRIMARY KEY (addresses_id)
);

CREATE TABLE IF NOT EXISTS public.cities
(
    city_id serial NOT NULL,
    city character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cities_pkey PRIMARY KEY (city_id)
);

CREATE TABLE IF NOT EXISTS public.order_lines
(
    order_line_id serial NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    carport_width integer NOT NULL,
    carport_length integer NOT NULL,
    roof_type character varying(64) COLLATE pg_catalog."default" NOT NULL,
    roof_inclination integer,
    shed_length integer,
    shed_width integer,
    price integer NOT NULL,
    quantity integer NOT NULL,
    order_id integer NOT NULL,
    svg text COLLATE pg_catalog."default" NOT NULL,
    description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    address_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT order_lines_pkey PRIMARY KEY (order_line_id)
);

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id serial NOT NULL,
    name character varying(64) COLLATE pg_catalog."default" NOT NULL,
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    order_placed timestamp with time zone,
    order_paid timestamp with time zone,
    order_complete timestamp with time zone,
    CONSTRAINT orders_pkey PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS public.postal_code
(
    postal_code_id serial NOT NULL,
    postal_code integer NOT NULL,
    PRIMARY KEY (postal_code_id)
);

CREATE TABLE IF NOT EXISTS public.product
(
    product_id serial NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    unit character varying(10) COLLATE pg_catalog."default" NOT NULL,
    order_line_id integer NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS public.product_variant
(
    product_variant_id serial NOT NULL,
    length integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT product_variant_pkey PRIMARY KEY (product_variant_id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    role character varying(11) COLLATE pg_catalog."default" NOT NULL,
    username character varying(64) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default",
    telephone integer,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT postal_code_fk FOREIGN KEY (postal_code_id)
        REFERENCES public.postal_code (postal_code_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT city_fk FOREIGN KEY (city_id)
        REFERENCES public.cities (city_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.order_lines
    ADD CONSTRAINT order_id_fk FOREIGN KEY (order_id)
        REFERENCES public.orders (order_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.order_lines
    ADD CONSTRAINT address_id_fk FOREIGN KEY (address_id)
        REFERENCES public.addresses (addresses_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.order_lines
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.product
    ADD FOREIGN KEY (order_line_id)
        REFERENCES public.order_lines (order_line_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.product_variant
    ADD CONSTRAINT fk FOREIGN KEY (product_id)
        REFERENCES public.product (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

END;