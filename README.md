#
## docker-compose down -v
## docker-compose up -d

# Verify the database exists and user has correct permissions
## docker exec postgres_container psql -U puser -d pdb -c "\du"
## docker exec postgres_container psql -U puser -d pdb -c "\l"


docker exec -it postgres_container psql -U puser -d pdb -c "
CREATE TABLE IF NOT EXISTS events (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);"

