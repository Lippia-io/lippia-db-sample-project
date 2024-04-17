FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=5656

COPY ./mysqlsampledatabase.sql /docker-entrypoint-initdb.d/

EXPOSE 3306

VOLUME [ "/docker-entrypoint-initdb.d/" ]