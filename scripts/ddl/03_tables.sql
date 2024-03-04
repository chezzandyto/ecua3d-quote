-- nombre base de tablas
-- (quot)   (t)
-- quote table


-- File
CREATE TABLE quote.quott_file (
	file_id BIGINT DEFAULT nextval('quote.quots_file'),
	name_file VARCHAR(128) NOT NULL,
	location VARCHAR(256) NOT NULL,
	
	status VARCHAR(1) NOT NULL DEFAULT 1,
	created_by_user VARCHAR(36) NOT NULL,
	created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_modified_by_user VARCHAR(36),
	last_modified_date TIMESTAMP WITH TIME ZONE,
	created_from_ip VARCHAR(64) NOT NULL DEFAULT '127.0.0.1',
	updated_from_ip VARCHAR(64),
	CONSTRAINT quotpr_pk_fl PRIMARY KEY (file_id)
);

COMMENT ON TABLE quote.quott_file IS 'File Table';
COMMENT ON COLUMN quote.quott_file.file_id IS 'File id';
COMMENT ON COLUMN quote.quott_file.name_file IS 'File Name';
COMMENT ON COLUMN quote.quott_file.location IS 'File Location URL';

COMMENT ON COLUMN quote.quott_file.status IS 'Record status';
COMMENT ON COLUMN quote.quott_file.created_by_user IS 'Id of the user who created the record';
COMMENT ON COLUMN quote.quott_file.created_date IS 'Record creation date';
COMMENT ON COLUMN quote.quott_file.last_modified_by_user IS 'Id of the user who made the last modification';
COMMENT ON COLUMN quote.quott_file.last_modified_date IS 'Date of the last modification of the record';
COMMENT ON COLUMN quote.quott_file.created_from_ip IS 'IP address of the computer from where the record was created';
COMMENT ON COLUMN quote.quott_file.updated_from_ip IS 'IP address of the equipment from where the last change was made';
COMMENT ON CONSTRAINT quotpr_pk_fl ON quote.quott_file IS 'Restriction PK File';

-- Quote
CREATE TABLE quote.quott_quote (
	quote_id BIGINT DEFAULT nextval('quote.quots_quote'),
	name VARCHAR(64) NOT NULL,
	email VARCHAR(64) NOT NULL, 
	phone VARCHAR(64) NOT NULL,
	filament_id BIGINT NOT NULL,
	material_id BIGINT NOT NULL,
	color_id BIGINT NOT NULL,
	calidad_id BIGINT NOT NULL,
	
	status VARCHAR(1) NOT NULL DEFAULT 1,
	created_by_user VARCHAR(36) NOT NULL,
	created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_modified_by_user VARCHAR(36),
	last_modified_date TIMESTAMP WITH TIME ZONE,
	created_from_ip VARCHAR(64) NOT NULL DEFAULT '127.0.0.1',
	updated_from_ip VARCHAR(64),
	CONSTRAINT quotpr_pk_qte PRIMARY KEY (quote_id)
);

COMMENT ON TABLE quote.quott_quote IS 'quote Table';
COMMENT ON COLUMN quote.quott_quote.quote_id IS 'quote id';
COMMENT ON COLUMN quote.quott_quote.name IS 'Client Name';
COMMENT ON COLUMN quote.quott_quote.email IS 'Client email';
COMMENT ON COLUMN quote.quott_quote.phone IS 'Client Phone';
COMMENT ON COLUMN quote.quott_quote.material_id IS 'Client select filament';
COMMENT ON COLUMN quote.quott_quote.material_id IS 'Client select material';
COMMENT ON COLUMN quote.quott_quote.color_id IS 'Client select color';
COMMENT ON COLUMN quote.quott_quote.calidad_id IS 'Client select calidad';


COMMENT ON COLUMN quote.quott_quote.status IS 'Record status';
COMMENT ON COLUMN quote.quott_quote.created_by_user IS 'Id of the user who created the record';
COMMENT ON COLUMN quote.quott_quote.created_date IS 'Record creation date';
COMMENT ON COLUMN quote.quott_quote.last_modified_by_user IS 'Id of the user who made the last modification';
COMMENT ON COLUMN quote.quott_quote.last_modified_date IS 'Date of the last modification of the record';
COMMENT ON COLUMN quote.quott_quote.created_from_ip IS 'IP address of the computer from where the record was created';
COMMENT ON COLUMN quote.quott_quote.updated_from_ip IS 'IP address of the equipment from where the last change was made';
COMMENT ON CONSTRAINT quotpr_pk_qte ON quote.quott_quote  IS 'Restriction PK quote';

ALTER TABLE "quote".quott_quote RENAME COLUMN calidad_id TO quality_id;
ALTER TABLE "quote".quott_quote ADD state int8 NOT NULL;
COMMENT ON COLUMN "quote".quott_quote.state IS '1 PENDING, 2 DONE';


-- Quote_File
CREATE TABLE quote.quott_quote_file (
	quote_file_id BIGINT DEFAULT nextval('quote.quots_quote_file'),
	quote_id BIGINT NOT NULL,
	file_id BIGINT NOT NULL,
);

COMMENT ON TABLE quote.quott_quote_file IS 'Quote and File Table';
COMMENT ON COLUMN quote.quott_quote_file.quote_file_id IS 'Quote and File id';
COMMENT ON COLUMN quote.quott_quote_file.quote_id IS 'relation with quote table';
COMMENT ON COLUMN quote.quott_quote_file.file_id IS 'relation with file table';


