CREATE TABLE gift.categories
(
    id serial NOT NULL,
    name character varying(200) NOT NULL,
    CONSTRAINT "PK_categories" PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
);


CREATE TABLE gift.category_words
(
    category_id integer NOT NULL DEFAULT 1,
    word character varying(100) NOT NULL,
    CONSTRAINT category_words_pkey PRIMARY KEY (category_id, word),
    CONSTRAINT "cat_word_FK" FOREIGN KEY (category_id)
        REFERENCES gift.categories (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX "word_IX (word)"
    ON gift.category_words USING btree
    (word COLLATE pg_catalog."default" ASC NULLS LAST);


CREATE TABLE gift.products
(
    id serial NOT NULL,
    name character varying(300) NOT NULL,
    description character varying(4000),
    price numeric(8,2),
    catalog_url character varying(300) NOT NULL,
    counter integer NOT NULL DEFAULT 0,
    picture_url character varying(300),
    category_id integer NOT NULL,
    CONSTRAINT "PK_products" PRIMARY KEY (id),
    CONSTRAINT "cat_prod_FK" FOREIGN KEY (category_id)
        REFERENCES gift.categories (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX "product_id_IX (product_id)"
    ON gift.products USING btree
    (id ASC NULLS LAST, id ASC NULLS LAST);


CREATE TABLE gift."user"
(
    user_id serial NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    picture_url character varying(255),
    provider character varying(20) NOT NULL,
    email character varying(200),
    provider_user_id integer NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (user_id)
);


CREATE TABLE gift.transactions
(
    tid serial NOT NULL,
    recipient character varying(50) NOT NULL,
    sender_id integer NOT NULL,
    is_wish boolean NOT NULL DEFAULT false,
    CONSTRAINT "PK_transaction" PRIMARY KEY (tid),
    CONSTRAINT "user_FK" FOREIGN KEY (sender_id)
        REFERENCES gift."user" (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE gift.products_transaction
(
    tid integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT "FK_prod_tran" FOREIGN KEY (product_id)
        REFERENCES gift.products (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "tran_prod_FK" FOREIGN KEY (tid)
        REFERENCES gift.transactions (tid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX "product_IX (product_id)"
    ON gift.products_transaction USING btree
        (product_id ASC NULLS LAST);

CREATE INDEX "tid_IX (tid)"
    ON gift.products_transaction USING btree
        (tid ASC NULLS LAST);


CREATE TABLE gift.role
(
    role_id serial NOT NULL,
    name character varying(50),
    CONSTRAINT role_pk PRIMARY KEY (role_id)
);


CREATE TABLE gift.user_role
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)
);


CREATE TABLE gift.authentication
(
    id serial NOT NULL,
    code character varying(400) NOT NULL,
    token character varying(400) NOT NULL,
    user_id bigint NOT NULL,
    uuid character varying(200) NOT NULL,
    CONSTRAINT "Authentication_pkey" PRIMARY KEY (id)
);
