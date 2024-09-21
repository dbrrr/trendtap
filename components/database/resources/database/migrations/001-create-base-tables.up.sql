CREATE TABLE tenant (
  id uuid DEFAULT uuid_generate_v4 (),
  name TEXT,
  PRIMARY KEY (id)
);
CREATE TABLE silo (
  id uuid DEFAULT uuid_generate_v4 (),
  tenant_id uuid NOT NULL,
  details JSONB,
  PRIMARY KEY (id),
  CONSTRAINT fk_tenant
    FOREIGN KEY(tenant_id)
      REFERENCES tenant(id)
);
CREATE TABLE actor (
  id uuid DEFAULT uuid_generate_v4 (),
  tenant_id uuid NOT NULL,
  silo_id uuid NOT NULL,
  details JSONB,
  PRIMARY KEY (id),
  CONSTRAINT fk_tenant
    FOREIGN KEY(tenant_id)
      REFERENCES tenant(id),
  CONSTRAINT fk_silo
    FOREIGN KEY(silo_id)
      REFERENCES silo(id)
);
