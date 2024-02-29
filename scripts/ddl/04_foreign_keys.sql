-- nombre base de contraints quotfk
-- (quot)     (fk)
-- quote  foreign key


/* quote */
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