CREATE TABLE IF NOT EXISTS "account" (
  "id" TEXT NOT NULL,
  "balance" NUMERIC NOT NULL,
  CONSTRAINT account_pkey PRIMARY KEY (id)
  );

CREATE TABLE IF NOT EXISTS "transaction" (
  "id" TEXT NOT NULL,
  "account_id" TEXT,
  "date" TEXT NOT NULL,
  "amount" NUMERIC NOT NULL,
  "fee" NUMERIC NOT NULL,
  "description" TEXT NULL,
  CONSTRAINT transaction_pkey PRIMARY KEY (id),
  CONSTRAINT transaction_account_fkey FOREIGN KEY (account_id) REFERENCES account (id)
  );

