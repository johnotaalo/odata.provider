CREATE OR REPLACE VIEW informea_treaty_machine_name AS
  SELECT 
    nid,
    uuid,
    CASE 
      WHEN nid = 1 THEN 'cms'
      WHEN nid = 2 THEN 'aewa'
      WHEN nid = 3 THEN 'eurobats'
      WHEN nid = 4 THEN 'ascobans'
      WHEN nid = 5 THEN 'accobams'
      WHEN nid = 6 THEN 'wadden-sea-seals'
      WHEN nid = 7 THEN 'acap'
      WHEN nid = 8 THEN 'gorilla'
      WHEN nid = 9 THEN 'siberian-crane'
      WHEN nid = 10 THEN 'slender-billed-curlew'
      WHEN nid = 11 THEN 'middle-european-great-bustard'
      WHEN nid = 12 THEN 'atlantic-turtles'
      WHEN nid = 13 THEN 'iosea-marine-turtles'
      WHEN nid = 14 THEN 'bukhara-deer'
      WHEN nid = 15 THEN 'aquatic-warbler'
      WHEN nid = 16 THEN 'west-african-elephants'
      WHEN nid = 17 THEN 'saiga-antelope'
      WHEN nid = 18 THEN 'pacific-inslands-cetaceans'
      WHEN nid = 19 THEN 'ruddy-headed-goose'
      WHEN nid = 20 THEN 'southern-south-american-grassland-birds'  
      WHEN nid = 21 THEN 'monk-seal-atlantic'  
      WHEN nid = 22 THEN 'dugong'  
      WHEN nid = 23 THEN 'western-african-aquatic-mammals'  
      WHEN nid = 24 THEN 'birds-of-prey'
      WHEN nid = 25 THEN 'high-andean-flamingos'
      WHEN nid = 26 THEN 'sharks'
      WHEN nid = 27 THEN 'south-andean-huemul'
      WHEN nid = 28 THEN 'central-asian-mammals'  
      WHEN nid = 29 THEN 'sahelo-saharan-megafauna'  
      WHEN nid = 30 THEN 'central-asian-flyway'
    ELSE
      NULL 
    END treaty,
    title
  FROM node
  WHERE `type` = 'legal_instrument'

--
-- Document entity view
--
CREATE OR REPLACE VIEW informea_documents AS
  SELECT 
    1 schemaVersion,
    node.uuid id,
    CONVERT(field_publication_published_date_timestamp, DATE) AS published,
    node.changed updated,
    treaty.treaty,
    REPLACE(thumbnails.uri, 'public://', 'http://www.cms.int/sites/default/files') thumbnailUrl,
    0 displayOrder
  FROM node node
    LEFT JOIN field_data_field_publication_published_date pdate ON node.nid = pdate.entity_id
    INNER JOIN field_data_field_instrument instr ON node.nid = instr.entity_id
    INNER JOIN informea_treaty_machine_name treaty ON treaty.nid = instr.field_instrument_target_id
    LEFT JOIN field_data_field_publication_image img ON node.nid = img.entity_id
    LEFT JOIN file_managed thumbnails ON field_publication_image_fid = thumbnails.fid
  WHERE
    treaty IS NOT NULL
    AND node.type = 'publication';


--
-- Documents `type` navigation property
--
CREATE OR REPLACE VIEW informea_documents_types AS
  SELECT
    CONCAT(id, '-', 'publication') AS id,
    'publication' `value`
  FROM informea_documents;
