--postgres - es el usuario creado al instalar postgres
GRANT USAGE ON SCHEMA quote TO postgres;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA quote TO postgres;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA quote TO postgres;