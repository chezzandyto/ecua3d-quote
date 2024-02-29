-- nombre base de contraints quotfk
-- (quot)     (fk)
-- quote  foreign key


/* quote */
-- User created
ALTER TABLE quote.quott_quote ADD CONSTRAINT quotfk_qt_pk_use_created
FOREIGN KEY (created_by_user) REFERENCES public.user_entity (id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_qt_pk_use_created ON quote.quott_quote IS 'Restriction FK Quote PK user for created record';

-- User modified
ALTER TABLE quote.quott_quote ADD CONSTRAINT quotfk_qt_pk_use_modified
FOREIGN KEY (last_modified_by_user) REFERENCES public.user_entity (id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_qt_pk_use_modified ON quote.quott_quote IS 'Restriction FK Quote PK user for last modified record';

-- material
ALTER TABLE quote.quott_quote ADD CONSTRAINT quotfk_fl_pk_material_id
FOREIGN KEY (material_id) REFERENCES corporative.corpt_material (material_id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_fl_pk_material_id ON quote.quott_quote IS 'Restriction FK quote PK material_id';

-- color
ALTER TABLE quote.quott_quote ADD CONSTRAINT quotfk_fl_pk_color_id
FOREIGN KEY (color_id) REFERENCES corporative.corpt_color (color_id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_fl_pk_color_id ON quote.quott_quote IS 'Restriction FK quote PK color_id';

/* file */
-- User created
ALTER TABLE quote.quott_file ADD CONSTRAINT quotfk_fl_pk_use_created
FOREIGN KEY (created_by_user) REFERENCES public.user_entity (id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_fl_pk_use_created ON quote.quott_file IS 'Restriction FK File PK user for created record';

-- User modified
ALTER TABLE quote.quott_file ADD CONSTRAINT quotfk_fl_pk_use_modified
FOREIGN KEY (last_modified_by_user) REFERENCES public.user_entity (id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfk_fl_pk_use_modified ON quote.quott_file IS 'Restriction FK File PK user for last modified record';


/* quote_file */
-- quote
ALTER TABLE quote.quott_quote_file ADD CONSTRAINT quotfilefk_fl_pk_quote_id
FOREIGN KEY (quote_id) REFERENCES quote.quott_quote (quote_id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfilefk_fl_pk_quote_id ON quote.quott_quote_file IS 'Restriction FK quote_file PK quote_id';

-- file
ALTER TABLE quote.quott_quote_file ADD CONSTRAINT quotfilefk_fl_pk_file_id
FOREIGN KEY (file_id) REFERENCES quote.quott_file (file_id)
ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMENT ON CONSTRAINT quotfilefk_fl_pk_file_id ON quote.quott_quote_file IS 'Restriction FK quote_file PK file_id';