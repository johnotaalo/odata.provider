#!/bin/sh

# To run tests, you have to populate the 'informea_drupal' database with real data
# This script will re-create the views for the toolkit inside informea_odata_test database

mysql -e "DROP DATABASE IF EXISTS informea_odata_test_source; CREATE DATABASE informea_odata_test_source"
mysql informea_odata_test_source < ./etc/informea_odata.sql

