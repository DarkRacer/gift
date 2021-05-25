ALTER TABLE gift.categories
    DROP CONSTRAINT IF EXISTS "PK_categories";

ALTER TABLE gift.category_words
    DROP CONSTRAINT IF EXISTS category_words_pkey;

ALTER TABLE gift.category_words
    DROP CONSTRAINT IF EXISTS "cat_word_FK";

ALTER TABLE gift.products
    DROP CONSTRAINT IF EXISTS "PK_products";

ALTER TABLE gift.products
    DROP CONSTRAINT IF EXISTS "cat_prod_FK";

ALTER TABLE gift."user"
    DROP CONSTRAINT IF EXISTS user_pkey;

ALTER TABLE gift.transactions
    DROP CONSTRAINT IF EXISTS "user_FK";

ALTER TABLE gift.transactions
    DROP CONSTRAINT IF EXISTS "PK_transaction";

ALTER TABLE gift.products_transaction
    DROP CONSTRAINT IF EXISTS "tran_prod_FK";

ALTER TABLE gift.products_transaction
    DROP CONSTRAINT IF EXISTS "FK_prod_tran";

ALTER TABLE gift.role
    DROP CONSTRAINT IF EXISTS role_pk;

ALTER TABLE gift.user_role
    DROP CONSTRAINT IF EXISTS user_role_pkey;

ALTER TABLE gift.authentication
    DROP CONSTRAINT IF EXISTS "Authentication_pkey";


DROP INDEX IF EXISTS gift."word_IX (word)";

DROP INDEX IF EXISTS gift."product_id_IX (product_id)";

DROP INDEX IF EXISTS gift."product_IX (product_id)";

DROP INDEX IF EXISTS gift."tid_IX (tid)";


DROP TABLE IF EXISTS gift.categories;

DROP TABLE IF EXISTS gift.category_words;

DROP TABLE IF EXISTS gift.products;

DROP TABLE IF EXISTS gift."user";

DROP TABLE IF EXISTS gift.transactions;

DROP TABLE IF EXISTS gift.products_transaction;

DROP TABLE IF EXISTS gift.role;

DROP TABLE IF EXISTS gift.user_role;

DROP TABLE IF EXISTS gift.authentication;
