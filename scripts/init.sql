CREATE USER admin;
CREATE database "shopme";
ALTER ROLE admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE "shopme" TO admin;
ALTER DATABASE "shopme" OWNER TO admin;