-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;

DROP TABLE IF EXISTS public.addresses CASCADE;
DROP TABLE IF EXISTS public.accounts CASCADE;
DROP TABLE IF EXISTS public.cities CASCADE;
DROP TABLE IF EXISTS public.orders CASCADE;
DROP TABLE IF EXISTS public.postal_code CASCADE;
DROP TABLE IF EXISTS public.materials CASCADE;
DROP TABLE IF EXISTS public.material_variants CASCADE;
DROP TABLE IF EXISTS public.orders_material_variants CASCADE;

CREATE TABLE IF NOT EXISTS public.accounts
(
    account_id serial NOT NULL,
    role character varying(11) NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(100),
    telephone integer,
    addresses_id integer NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (account_id)
);

CREATE TABLE IF NOT EXISTS public.addresses
(
    addresses_id serial NOT NULL,
    address character varying(64) NOT NULL,
    postal_code_id integer NOT NULL,
    city_id integer NOT NULL,
    CONSTRAINT addresses_pkey PRIMARY KEY (addresses_id)
);

CREATE TABLE IF NOT EXISTS public.cities
(
    city_id serial NOT NULL,
    city character varying(50) NOT NULL,
    CONSTRAINT cities_pkey PRIMARY KEY (city_id)
);

CREATE TABLE IF NOT EXISTS public.material_variants
(
    material_variant_id serial NOT NULL,
    length integer,
    height integer,
    width integer,
    material_id integer NOT NULL,
    CONSTRAINT material_variant_pk PRIMARY KEY (material_variant_id)
);

CREATE TABLE IF NOT EXISTS public.materials
(
    material_id serial NOT NULL,
    name character varying(100) NOT NULL,
    unit character varying(10) NOT NULL,
    price integer NOT NULL,
    order_id integer NOT NULL,
    description character varying(100) NOT NULL,
    CONSTRAINT material_pk PRIMARY KEY (material_id)
);

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id serial NOT NULL,
    customer_id integer NOT NULL,
    carport_id integer NOT NULL,
    salesperson_id integer NOT NULL,
    status character varying(10) NOT NULL,
    price integer,
    order_placed timestamp with time zone,
    order_paid boolean NOT NULL,
    height integer NOT NULL,
    width integer NOT NULL,
    "hasShed" boolean,
    roof_type character varying(6) NOT NULL,
    account_id integer NOT NULL,
    m_id integer NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS public.orders_material_variants
(
    orders_order_id serial NOT NULL,
    material_variants_material_variant_id serial NOT NULL,
    quantity int,
    description character varying(100)
);

CREATE TABLE IF NOT EXISTS public.postal_code
(
    postal_code_id serial NOT NULL,
    postal_code integer NOT NULL,
    CONSTRAINT postal_code_pkey PRIMARY KEY (postal_code_id)
);

ALTER TABLE IF EXISTS public.accounts
    ADD CONSTRAINT addresses_fk FOREIGN KEY (addresses_id)
        REFERENCES public.addresses (addresses_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT city_fk FOREIGN KEY (city_id)
        REFERENCES public.cities (city_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT postal_code_fk FOREIGN KEY (postal_code_id)
        REFERENCES public.postal_code (postal_code_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.material_variants
    ADD CONSTRAINT fk FOREIGN KEY (material_id)
        REFERENCES public.materials (material_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT account_id_fk FOREIGN KEY (account_id)
        REFERENCES public.accounts (account_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.orders_material_variants
    ADD CONSTRAINT orders_material_variants_material_variants_material_varian_fkey FOREIGN KEY (material_variants_material_variant_id)
        REFERENCES public.material_variants (material_variant_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


ALTER TABLE IF EXISTS public.orders_material_variants
    ADD CONSTRAINT orders_material_variants_orders_order_id_fkey FOREIGN KEY (orders_order_id)
        REFERENCES public.orders (order_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;


END;