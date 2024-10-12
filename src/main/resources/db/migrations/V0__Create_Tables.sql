ROLLBACK;
BEGIN TRANSACTION;

CREATE TABLE authors(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url TEXT NOT NULL
);

CREATE TABLE quotes(
    id serial PRIMARY KEY,
    q_text TEXT NOT NULL,
    author_id int REFERENCES authors(id) NOT NULL
);

CREATE TABLE tags(
    id serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    url TEXT NOT NULL
);

CREATE TABLE tag_quote(
    id serial PRIMARY KEY,
    quote_id int REFERENCES quotes(id) NOT NULL,
    tag_id int REFERENCES tags(id) NOT NULL
);

COMMIT;