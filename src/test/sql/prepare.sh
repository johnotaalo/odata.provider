#!/bin/sh

# To run tests, you have to populate the 'informea_drupal' database with real data
# This script will re-create the views for the toolkit inside informea_odata_test database

mysql -e "DROP DATABASE IF EXISTS informea_odata_test; CREATE DATABASE informea_odata_test"
mysql informea_odata_test < ../../../etc/informea_odata.sql

