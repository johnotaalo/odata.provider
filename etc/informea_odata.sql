-- informea_meetings
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_meetings` AS
  SELECT
    a.nid AS id,
    c.field_odata_identifier_value AS treaty,
    url.field_url_url AS url,
    d.event_calendar_date_value AS `start`,
    d.event_calendar_date_value2 AS `end`,
    t1.name AS `repetition`,
    t2.name AS `kind`,
    t3.name AS `type`,
    t4.name AS `access`,
    t5.name AS `status`,
    NULL AS imageUrl,
    NULL AS imageCopyright,
    loc.field_location_value AS location,
    city.field_city_value AS city,
    iso2.field_country_iso2_value AS country,
    lat.field_latitude_value AS latitude,
    lon.field_longitude_value AS longitude,
    FROM_UNIXTIME(a.changed) AS updated
  FROM
    `informea_drupal`.node a
    INNER JOIN `informea_drupal`.field_data_field_treaty b ON a.nid = b.entity_id
    INNER JOIN `informea_drupal`.field_data_field_odata_identifier c ON b.field_treaty_target_id = c.entity_id
    INNER JOIN `informea_drupal`.field_data_field_url url ON url.entity_id = a.nid
    INNER JOIN `informea_drupal`.field_data_event_calendar_date d ON d.entity_id = a.nid
    
    LEFT JOIN `informea_drupal`.field_data_field_event_repetition rep ON rep.entity_id = a.nid
    LEFT JOIN `informea_drupal`.taxonomy_term_data t1 ON rep.field_event_repetition_tid = t1.tid
    
    LEFT JOIN `informea_drupal`.field_data_field_event_kind k ON k.entity_id = a.nid
    LEFT JOIN `informea_drupal`.taxonomy_term_data t2 ON k.field_event_kind_tid = t2.tid
    
    LEFT JOIN `informea_drupal`.field_data_field_event_type et ON et.entity_id = a.nid
    LEFT JOIN `informea_drupal`.taxonomy_term_data t3 ON et.field_event_type_tid = t3.tid
    
    LEFT JOIN `informea_drupal`.field_data_field_event_access ac ON ac.entity_id = a.nid
    LEFT JOIN `informea_drupal`.taxonomy_term_data t4 ON ac.field_event_access_tid = t4.tid

    LEFT JOIN `informea_drupal`.field_data_field_event_calendar_status st ON st.entity_id = a.nid
    LEFT JOIN `informea_drupal`.taxonomy_term_data t5 ON st.field_event_calendar_status_tid = t5.tid

    LEFT JOIN `informea_drupal`.field_data_field_location loc ON loc.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_city city ON city.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_country cou ON cou.entity_id = a.nid
    LEFT JOIN `informea_drupal`.node nc ON (cou.field_country_target_id = nc.nid AND nc.type = 'country')
    LEFT JOIN `informea_drupal`.field_data_field_country_iso2 iso2 ON nc.nid = iso2.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_latitude lat ON lat.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_longitude lon ON lon.entity_id = a.nid

  WHERE
    a.`TYPE` = 'event_calendar'
    AND d.event_calendar_date_value IS NOT NULL
    GROUP BY a.nid;


-- informea_meetings_description
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_meetings_description` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS meeting_id,
    c.language AS `language`,
    c.body_value AS description
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_meetings` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_body c ON a.nid = c.entity_id;


-- informea_meetings_title
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_meetings_title` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS meeting_id,
    c.language AS `language`,
    c.title_field_value AS title
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_meetings` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_title_field c ON a.nid = c.entity_id;

-- DECISIONS 
-- informea_decisions
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions` AS
  SELECT
    a.nid AS id,
    IF (url.field_url_url IS NULL, concat('http://www.informea.org/node/' , a.nid), url.field_url_url) AS link,
    t1.name AS `type`,
    t2.name AS `status`,
    field_decision_number_value AS number,
    c.field_odata_identifier_value AS treaty,
    dp.field_decision_published_value AS published,
    du.field_decision_updated_value AS updated,
    n2.nid AS meetingId,
    n2.title AS meetingTitle,
    urlm.field_url_url AS meetingUrl
  FROM
    `informea_drupal`.node a
    INNER JOIN `informea_drupal`.field_data_field_treaty b ON a.nid = b.entity_id
    INNER JOIN `informea_drupal`.field_data_field_odata_identifier c ON b.field_treaty_target_id = c.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_url url ON url.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_decision_type dt ON dt.entity_id = a.nid
    INNER JOIN `informea_drupal`.taxonomy_term_data t1 ON dt.field_decision_type_tid = t1.tid

    LEFT JOIN `informea_drupal`.field_data_field_decision_status ds ON ds.entity_id = a.nid
    INNER JOIN `informea_drupal`.taxonomy_term_data t2 ON ds.field_decision_status_tid = t2.tid

    INNER JOIN `informea_drupal`.field_data_field_decision_number dn ON dn.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_decision_published dp ON dp.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_decision_updated du ON du.entity_id = a.nid

    INNER JOIN `informea_drupal`.field_data_field_meeting m ON m.entity_id = a.nid  
    INNER JOIN `informea_drupal`.node n2 ON m.field_meeting_target_id = n2.nid

    LEFT JOIN `informea_drupal`.field_data_field_url urlm ON urlm.entity_id = m.field_meeting_target_id
    WHERE 
      a.type = 'decision';

-- informea_decisions_title
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_title` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS decision_id,
    c.language AS `language`,
    c.title_field_value AS title
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_decisions` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_title_field c ON a.nid = c.entity_id;

-- informea_decisions_content
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_content` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS decision_id,
    c.language AS `language`,
    c.body_value AS content
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_decisions` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_body c ON a.nid = c.entity_id
  WHERE c.body_value IS NOT NULL AND TRIM(c.body_value) <> '';

-- informea_decisions_documents
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_documents` AS
  SELECT
    CONCAT(a.nid, '-', fm.fid) AS id,
    a.nid AS decision_id,
    fm.uri AS diskPath,
    IF (f.field_files_description IS NULL, CONCAT('http://www.informea.org/sites/default/files/', REPLACE(fm.uri, 'public://', '')), f.field_files_description) AS url,
    fm.filemime AS mimeType,
    f.`language` AS `language`,
    fm.filename AS filename
  FROM `informea_drupal`.node a
    INNER JOIN `informea_decisions` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_field_files f ON f.entity_id = a.nid
    INNER JOIN `informea_drupal`.file_managed fm ON fm.fid = field_files_fid;

-- informea_decisions_keywords
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_keywords` AS
  SELECT 
    CONCAT(b.id, '-en') AS id, 
    b.id AS decision_id,
    'http://www.informea.org/terms/' AS namespace,
    t1.name AS term
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_decisions` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_field_informea_tags c ON a.nid = c.entity_id
    INNER JOIN `informea_drupal`.taxonomy_term_data t1 ON c.field_informea_tags_tid = t1.tid;

-- informea_decisions_longtitle
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_longtitle` AS
  SELECT
    NULL AS id,
    NULL AS decision_id,
    NULL AS `language`,
    NULL AS long_title
  LIMIT 0;

-- informea_decisions_summary
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_decisions_summary` AS
  SELECT
    NULL AS id,
    NULL AS decision_id,
    NULL AS LANGUAGE,
    NULL AS summary
  LIMIT 0;
  
-- COUNTRY REPORTS (National Reports)

-- informea_country_reports
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_country_reports` AS
  SELECT
    a.nid AS id,
    c.field_odata_identifier_value AS treaty,
    iso2.field_country_iso2_value AS country,
    sd.field_report_submission_date_value AS submission,
    durl.field_document_url_url AS url,
    FROM_UNIXTIME(a.changed) AS updated
  FROM
    `informea_drupal`.node a
    INNER JOIN `informea_drupal`.field_data_field_treaty b ON a.nid = b.entity_id
    INNER JOIN `informea_drupal`.field_data_field_odata_identifier c ON b.field_treaty_target_id = c.entity_id
    LEFT JOIN `informea_drupal`.field_data_field_country cou ON cou.entity_id = a.nid
    INNER JOIN `informea_drupal`.node nc ON (cou.field_country_target_id = nc.nid AND nc.type = 'country')
    INNER JOIN `informea_drupal`.field_data_field_country_iso2 iso2 ON nc.nid = iso2.entity_id
    LEFT JOIN `informea_drupal`.field_data_field_report_submission_date sd ON sd.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_document_url durl ON durl.entity_id = a.nid
  WHERE 
      a.`type` = 'national_report';

-- informea_country_reports_title
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_country_reports_title` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS country_report_id,
    c.language AS `language`,
    c.title_field_value AS title
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_country_reports` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_title_field c ON a.nid = c.entity_id;

-- informea_country_reports_documents
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_country_reports_documents` AS
  SELECT
    CONCAT(a.nid, '-', fm.fid) AS id,
    a.nid AS decision_id,
    fm.uri AS diskPath,
    IF (f.field_files_description = '', CONCAT('http://www.informea.org/sites/default/files/', REPLACE(fm.uri, 'public://', '')), f.field_files_description) AS url,
    fm.filemime AS mimeType,
    f.`language` AS `language`,
    fm.filename AS filename
  FROM `informea_drupal`.node a
    INNER JOIN `informea_country_reports` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_field_files f ON f.entity_id = a.nid
    INNER JOIN `informea_drupal`.file_managed fm ON fm.fid = field_files_fid;

-- NATIONAL PLANS (Action Plans)

-- informea_national_plans
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_national_plans` AS
  SELECT
    a.nid AS id,
    c.field_odata_identifier_value AS treaty,
    iso2.field_country_iso2_value AS country,
    sd.field_report_submission_date_value AS submission,
    durl.field_document_url_url AS url,
    t1.name AS `type`,
    FROM_UNIXTIME(a.changed) AS updated
  FROM
    `informea_drupal`.node a
    INNER JOIN `informea_drupal`.field_data_field_treaty b ON a.nid = b.entity_id
    INNER JOIN `informea_drupal`.field_data_field_odata_identifier c ON b.field_treaty_target_id = c.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_country cou ON cou.entity_id = a.nid
    INNER JOIN `informea_drupal`.node nc ON (cou.field_country_target_id = nc.nid AND nc.type = 'country')
    INNER JOIN `informea_drupal`.field_data_field_country_iso2 iso2 ON nc.nid = iso2.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_action_plan_type apt ON apt.entity_id = a.nid
    INNER JOIN `informea_drupal`.taxonomy_term_data t1 ON apt.field_action_plan_type_tid = t1.tid

    LEFT JOIN `informea_drupal`.field_data_field_report_submission_date sd ON sd.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_document_url durl ON durl.entity_id = a.nid
  WHERE 
    a.type = 'action_plan';

-- informea_national_plans_title
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_national_plans_title` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS national_plan_id,
    c.language AS `language`,
    c.title_field_value AS title
  FROM `informea_drupal`.node a 
  INNER JOIN `informea_national_plans` b ON a.nid = b.id
  INNER JOIN `informea_drupal`.field_data_title_field c ON a.nid = c.entity_id;

-- informea_national_plans_documents
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_country_reports_documents` AS
  SELECT
    CONCAT(a.nid, '-', fm.fid)  AS id,
    a.nid AS decision_id,
    fm.uri AS diskPath,
    IF (f.field_files_description = '', CONCAT('http://www.informea.org/sites/default/files/', REPLACE(fm.uri, 'public://', '')), f.field_files_description) AS url,
    fm.filemime AS mimeType,
    f.`language` AS `language`,
    fm.filename AS filename
  FROM `informea_drupal`.node a
    INNER JOIN `informea_national_plans` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_field_files f ON f.entity_id = a.nid
    INNER JOIN `informea_drupal`.file_managed fm ON fm.fid = field_files_fid;

-- CONTACTS (Focal Points)
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_contacts` AS
  SELECT
    a.nid AS id,
    iso2.field_country_iso2_value AS country,
    prf.field_person_prefix_value AS prefix,
    fst.field_person_first_name_value AS firstName,
    lst.field_person_last_name_value AS lastName,
    pos.field_person_position_value AS `position`,
    inst.field_person_institution_value AS institution,
    dept.field_person_department_value AS department,
    t1.name AS `type`,
    addr.field_address_value AS ADDRESS,
    mail.field_person_email_email AS email,
    tel.field_contact_telephone_value AS phoneNumber,
    fax.field_contact_fax_value AS fax,
    pri.field_contact_primary_nfp_value AS `primary`,
    FROM_UNIXTIME(a.changed) AS `updated`
  FROM `informea_drupal`.node a
    LEFT JOIN `informea_drupal`.field_data_field_country cou ON cou.entity_id = a.nid
    INNER JOIN `informea_drupal`.node nc ON (cou.field_country_target_id = nc.nid AND nc.type = 'country')
    INNER JOIN `informea_drupal`.field_data_field_country_iso2 iso2 ON nc.nid = iso2.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_person_prefix prf ON prf.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_person_first_name fst ON fst.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_person_last_name lst ON lst.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_person_position  pos ON pos.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_person_institution inst ON inst.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_person_department dept ON dept.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_person_type ptype ON ptype.entity_id = a.nid
    INNER JOIN `informea_drupal`.taxonomy_term_data t1 ON ptype.field_person_type_tid = t1.tid

    LEFT JOIN `informea_drupal`.field_data_field_address addr ON addr.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_person_email mail ON mail.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_contact_telephone tel ON tel.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_contact_fax fax ON fax.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_contact_primary_nfp pri ON pri.entity_id = a.nid

  WHERE 
    a.`type` = 'contact_person'
  GROUP BY a.nid;

-- informea_contacts_treaties
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_contacts_treaties` AS
  SELECT
    CONCAT(a.nid, '-', d.field_odata_identifier_value) AS id,
    a.nid AS contact_id,
    d.field_odata_identifier_value AS treaty
  FROM `informea_drupal`.node a
  INNER JOIN `informea_contacts` b ON a.nid = b.id
  INNER JOIN `informea_drupal`.field_data_field_treaty c ON a.nid = c.entity_id
  INNER JOIN `informea_drupal`.field_data_field_odata_identifier d ON c.field_treaty_target_id = d.entity_id;
  
-- SITES
-- informea_sites
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_sites` AS
  SELECT
    a.nid AS id,
    c.field_odata_identifier_value AS `type`,
    iso2.field_country_iso2_value AS country,
    c.field_odata_identifier_value AS treaty,
    url.field_url_url AS url,
    lat.field_latitude_value AS latitude,
    lon.field_longitude_value AS longitude,
    FROM_UNIXTIME(a.changed) AS updated
  FROM `informea_drupal`.node a
    INNER JOIN `informea_drupal`.field_data_field_treaty b ON a.nid = b.entity_id
    INNER JOIN `informea_drupal`.field_data_field_odata_identifier c ON b.field_treaty_target_id = c.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_country cou ON cou.entity_id = a.nid
    INNER JOIN `informea_drupal`.node nc ON (cou.field_country_target_id = nc.nid AND nc.type = 'country')
    INNER JOIN `informea_drupal`.field_data_field_country_iso2 iso2 ON nc.nid = iso2.entity_id

    LEFT JOIN `informea_drupal`.field_data_field_url url ON url.entity_id = a.nid

    LEFT JOIN `informea_drupal`.field_data_field_latitude lat ON lat.entity_id = a.nid
    LEFT JOIN `informea_drupal`.field_data_field_longitude lon ON lon.entity_id = a.nid

    WHERE a.`TYPE` = 'geographical_site'
    GROUP BY a.nid;

-- informea_sites_name
CREATE OR REPLACE DEFINER =`informea`@`localhost` SQL SECURITY DEFINER VIEW `informea_sites_name` AS
  SELECT 
    CONCAT(b.id, '-', c.language) AS id, 
    b.id AS site_id,
    c.language AS `language`,
    c.title_field_value AS `name`
  FROM `informea_drupal`.node a 
    INNER JOIN `informea_sites` b ON a.nid = b.id
    INNER JOIN `informea_drupal`.field_data_title_field c ON a.nid = c.entity_id;

