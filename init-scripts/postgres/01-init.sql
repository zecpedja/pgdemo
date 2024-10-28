-- Grant privileges to user 'puser' on database 'pdb'
GRANT ALL PRIVILEGES ON DATABASE pdb TO puser;

-- Connect to pdb database to grant schema privileges
\connect pdb;

-- Grant schema privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO puser;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO puser;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO puser;

-- Set default privileges for future objects
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO puser;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO puser;