-- nombre base de secuencias shths
-- (quot)   (s)
-- quote sequence


-- File (Archivo .stl)
CREATE SEQUENCE quote.quots_file START WITH 1 INCREMENT BY 1;
COMMENT ON SEQUENCE quote.quots_file IS 'file sequence';

-- Quote
CREATE SEQUENCE quote.quots_quote START WITH 1 INCREMENT BY 1;
COMMENT ON SEQUENCE quote.quots_quote IS 'Quote sequence';

-- Quote_File
CREATE SEQUENCE quote.quots_quote_file START WITH 1 INCREMENT BY 1;
COMMENT ON SEQUENCE quote.quots_quote_file IS 'Quote and File sequence';
