 #!/bin/sh
result=$(/sbin/ifconfig eth1 | grep 'inet addr' | cut -d: -f2 | awk '{print $1}')
sed -i 's/localhost/'$result'/g' ./src/main/resources/application.conf
mvn clean install