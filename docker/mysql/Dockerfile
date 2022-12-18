FROM mysql:5.7

COPY ./my.cnf /etc/mysql/conf.d/

RUN mkdir -p /var/log/mysql
RUN chown mysql.mysql /var/log/mysql