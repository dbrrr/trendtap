CREATE TABLE tenant (
  id uuid DEFAULT uuid_generate_v4 (),
  name TEXT,
  PRIMARY KEY (id)
);
CREATE TABLE account (
  id uuid DEFAULT uuid_generate_v4 (),
  email citext NOT NULL,
  details JSONB,
  PRIMARY KEY (id),
  CONSTRAINT unique_account_email UNIQUE (email)
);
CREATE TABLE tenant_account_mapping (
  tid uuid NOT NULL,
  account_id uuid NOT NULL,
  CONSTRAINT fk_tenant
    FOREIGN KEY(tid)
      REFERENCES tenant(id)
        ON DELETE CASCADE,
  CONSTRAINT fk_account
    FOREIGN KEY(account_id)
      REFERENCES account(id)
        ON DELETE CASCADE,
  PRIMARY KEY (tid, account_id)
);
CREATE TABLE silo (
  id uuid DEFAULT uuid_generate_v4 (),
  tenant_id uuid NOT NULL,
  details JSONB,
  timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (id),
  CONSTRAINT fk_tenant
    FOREIGN KEY(tenant_id)
      REFERENCES tenant(id)
);
CREATE TABLE silo_sample (
  id uuid DEFAULT uuid_generate_v4 (),
  tenant_id uuid NOT NULL,
  silo_id uuid NOT NULL,
  sampler_key text NOT NULL,
  details JSONB,
  timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (id),
  CONSTRAINT fk_tenant
    FOREIGN KEY(tenant_id)
      REFERENCES tenant(id)
        ON DELETE CASCADE,
  CONSTRAINT fk_silo
    FOREIGN KEY(silo_id)
      REFERENCES silo(id)
        ON DELETE CASCADE
);
CREATE TABLE actor (
  id uuid DEFAULT uuid_generate_v4 (),
  tenant_id uuid NOT NULL,
  silo_id uuid NOT NULL,
  account_id uuid,
  details JSONB,
  PRIMARY KEY (id),
  CONSTRAINT fk_tenant
    FOREIGN KEY(tenant_id)
      REFERENCES tenant(id)
        ON DELETE CASCADE,
  CONSTRAINT fk_account
    FOREIGN KEY(account_id)
      REFERENCES account(id),
  CONSTRAINT fk_silo
    FOREIGN KEY(silo_id)
      REFERENCES silo(id)
        ON DELETE CASCADE
);
