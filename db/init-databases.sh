#!/bin/sh
set -e

psql --username postgres --dbname postgres <<-EOSQL
  CREATE DATABASE schedule;
  CREATE ROLE schedule WITH ENCRYPTED PASSWORD 'schedule' LOGIN;
  GRANT ALL PRIVILEGES ON DATABASE schedule TO schedule;
EOSQL

psql --username postgres --dbname schedule <<-EOSQL
  GRANT ALL ON SCHEMA public TO schedule;
EOSQL
