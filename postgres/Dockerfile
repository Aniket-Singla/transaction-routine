FROM postgres:16.1
# ENV POSTGRES_DB my_database
COPY create-schema.sh /docker-entrypoint-initdb.d/
EXPOSE 5432