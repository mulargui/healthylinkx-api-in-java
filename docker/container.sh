sudo docker rm javaapi
sudo docker run -ti -p 8081:8081 -v /vagrant/apps/healthylinkx-api-in-java:/myapp --name javaapi --link MySQLDB:MySQLDB java /bin/bash