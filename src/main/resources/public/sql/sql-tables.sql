-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


-- Drop tables if they exist
DROP TABLE IF EXISTS public.addresses CASCADE;
DROP TABLE IF EXISTS public.accounts CASCADE;
DROP TABLE IF EXISTS public.cities CASCADE;
DROP TABLE IF EXISTS public.orders CASCADE;
DROP TABLE IF EXISTS public.postal_code CASCADE;
DROP TABLE IF EXISTS public.materials CASCADE;
DROP TABLE IF EXISTS public.orders_materials CASCADE;


CREATE TABLE IF NOT EXISTS public.accounts
(
    account_id serial NOT NULL,
    role character varying(11) COLLATE pg_catalog."default" NOT NULL,
    username character varying(64) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default",
    password character varying(100) COLLATE pg_catalog."default",
    telephone integer,
    addresses_id integer NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (account_id)
    );

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

CREATE TABLE IF NOT EXISTS public.materials
(
    material_id serial NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    unit character varying(10) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    length integer,
    height integer,
    width integer,
    type character varying(50) COLLATE pg_catalog."default",
    description character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT material_pk PRIMARY KEY (material_id)
    );

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id serial NOT NULL,
    carport_id character varying(8) COLLATE pg_catalog."default" NOT NULL,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL,
    price integer,
    sales_price integer,
    coverage_ratio_percentage integer,
    order_placed timestamp with time zone,
                               order_paid boolean NOT NULL,
                               height integer,
                               width integer NOT NULL,
                               length integer NOT NULL,
                               has_shed boolean NOT NULL,
                               roof_type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    account_id integer NOT NULL,
    svg text,
    CONSTRAINT orders_pk PRIMARY KEY (order_id)
    );

CREATE TABLE IF NOT EXISTS public.orders_materials
(
    orders_materials_id serial NOT NULL,
    order_id integer NOT NULL,
    material_id integer NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT orders_materials_pk PRIMARY KEY (orders_materials_id)
    );

CREATE TABLE IF NOT EXISTS public.postal_code
(
    postal_code_id serial NOT NULL,
    postal_code integer NOT NULL,
    CONSTRAINT postal_code_pkey PRIMARY KEY (postal_code_id)
    );

ALTER TABLE IF EXISTS public.accounts
    ADD CONSTRAINT accounts_addresses_fk FOREIGN KEY (addresses_id)
    REFERENCES public.addresses (addresses_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT addresses_cities_fk FOREIGN KEY (city_id)
    REFERENCES public.cities (city_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.addresses
    ADD CONSTRAINT addresses_postal_code_fk FOREIGN KEY (postal_code_id)
    REFERENCES public.postal_code (postal_code_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT orders_account_fk FOREIGN KEY (account_id)
    REFERENCES public.accounts (account_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.orders_materials
    ADD CONSTRAINT orders_materials_material_fk FOREIGN KEY (material_id)
    REFERENCES public.materials (material_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.orders_materials
    ADD CONSTRAINT orders_materials_order_fk FOREIGN KEY (order_id)
    REFERENCES public.orders (order_id) MATCH SIMPLE
    ON UPDATE CASCADE
       ON DELETE CASCADE;

-- Insert data into materials
INSERT INTO public.materials (name, unit, price, length, height, width, type, description)
VALUES
    ('25x200 mm. trykimp. Brædt', 'Stk', 177, 360, 25, 200, 'Understernbrædt', 'understernbrædder til for & bag ende'),
    ('25x200 mm. trykimp. Brædt', 'Stk', 265, 540, 25, 200, 'Understernbrædt', 'understernbrædder til siderne'),
    ('25x125mm. trykimp. Brædt', 'Stk', 119, 360, 25, 125, 'Oversternbrædt' , 'oversternbrædder til forenden'),
    ('25x125mm. trykimp. Brædt', 'Stk', 178, 540, 25, 125, 'Oversternbrædt', 'oversternbrædder til siderne'),
    ('38x73 mm. Lægte ubh.', 'Stk', 63, 420, 38, 73, 'Lægte', 'z på bagside af dør'),
    ('45x95 mm. Reglar ub.', 'Stk', 35, 270, 45, 95, 'Reglar', 'løsholter til skur gavle'),
    ('45x95 mm. Reglar ub.', 'Stk', 30, 240, 45, 95, 'Reglar', 'løsholter til skur sider'),
    ('45x195 mm. spærtræ ubh.', 'Stk', 274, 480, 45, 195, 'Rem', 'Remme i sider, sadles ned i stolper (skur del, deles)'),
    ('45x195 mm. spærtræ ubh.', 'Stk', 342, 600, 45, 195, 'Rem', 'Remme i sider, sadles ned i stolper (skur del, deles)'),
    ('45x195 mm. spærtræ ubh.', 'Stk', 274, 480, 45, 195, 'Spær', 'Spær, monteres på rem'),
    ('45x195 mm. spærtræ ubh.', 'Stk', 342, 600, 45, 195, 'Spær', 'Spær, monteres på rem'),
    ('97x97 mm. trykimp. Stolpe', 'Stk', 266, 300, 97, 97, 'Stolpe', 'stolper nedgraves 90 cm i jord'),
    ('19x100 mm. trykimp. Brædt', 'Stk', 19, 210, 19, 100, 'Beklædning', 'beklædning af skur 1 på 2'),
    ('19x100 mm. trykimp. Brædt', 'Stk', 48, 540, 19, 100, 'Vandbrædt', 'vandbrædt på stern i sider'),
    ('19x100 mm. trykimp. Brædt', 'Stk', 32, 360, 19, 100, 'Vandbrædt', 'vandbrædt på stern i forende'),
    ('Plastmo Ecolite blåtonet', 'Stk', 339, 600, 2, 109, 'Tagplade', 'tagplader monteres på spær'),
    ('Plastmo Ecolite blåtonet', 'Stk', 199, 360, 2, 109, 'Tagplade', 'tagplader monteres på spær'),
    ('plastmo bundskruer 200 stk', 'Pakke', 429, 2, 1, 1, 'Bundskruer', 'skruer til tagplader'),
    ('hulbånd 1x20 mm. 10 mtr.', 'Rulle', 349, 1000, 1, 20, 'Hulbånd', 'vindkryds på spær'),
    ('universal 190 mm højre', 'Stk', 50, 5, 150, 5, 'Beslag - Højre', 'Til montering af spær på rem'),
    ('universal 190 mm venstre', 'Stk', 50, 5, 150, 5, 'Beslag - Venstre', 'Til montering af spær på rem'),
    ('4,5 x 60 mm. skruer 200 stk.', 'Pakke', 169, 6, 1, 1, 'Skruer', 'Til montering af stern & vandbrædt'),
    ('4,0 x 50 mm. beslagskruer', 'Pakke', 139, 5, 1, 1, 'Beslagskruer', 'Til montering af universalbeslag + hulbånd'),
    ('bræddebolt 10 x 120 mm.', 'Stk', 16, 1, 1, 1, 'Bræddebolt', 'Til montering af rem på stolper'),
    ('firkantskiver 40x40x11mm', 'Stk', 9, 1, 1, 1, 'Firkantskiver','Til montering af rem på stolper'),
    ('4,5 x 70 mm. Skruer 400 stk.', 'Pakke', 165, 7, 1, 1, 'Beklædningsskruer', 'Til montering af yderste beklædning'),
    ('4,5 x 50 mm. Skruer 300 stk.', 'Pakke', 90, 5, 1, 1, 'Beklædningsskruer', 'Til montering af inderste beklædning'),
    ('stalddørsgreb 50x75', 'Sæt', 269, 3, 1, 1, 'Stalddørsgreb', 'Til lås på dør i skur'),
    ('t hængsel 390 mm', 'Stk', 139, 4, 1, 1, 'Hængsel', 'Til skurdør'),
    ('vinkelbeslag 35', 'Stk', 1, 5, 5, 4, 'Vinkelbeslag', 'Til montering af løsholter i skur');

-- Indsæt en konto med rollen "salesperson"
INSERT INTO public.cities (city) VALUES ('frederiksberg');
INSERT INTO public.postal_code (postal_code) VALUES (2000);
INSERT INTO public.addresses (address, postal_code_id, city_id) VALUES ('Second street', 1, 1);
INSERT INTO public.accounts (role, username, email, password, telephone, addresses_id)
VALUES ('salesperson', 'Martin', 'sales.person.fog@gmail.com', '$2a$10$d4at6bZlDljL1ZOEfx1zR.AkFTiWoaoW4X6np3YoNEH/O23SEKSay', '91919191', 1);


-- Indsæt en konto med rollen "admin"
INSERT INTO public.cities (city) VALUES ('Kælderen');
INSERT INTO public.postal_code (postal_code) VALUES (0000);
INSERT INTO public.addresses (address, postal_code_id, city_id) VALUES ('main street', 2, 2);
INSERT INTO public.accounts (role, username, email, password, telephone, addresses_id)
VALUES ('admin', 'admin', 'admin@cph.dk', '$2a$10$d4at6bZlDljL1ZOEfx1zR.AkFTiWoaoW4X6np3YoNEH/O23SEKSay', '87654321', 2);



END;