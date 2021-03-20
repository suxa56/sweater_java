#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    targer/sweater-1.0-SNAPSHOT.jar \
    suxa@192.168.100.132:/home/suxa/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa suxa@192.168.100.132 << EOF
pgrep java | xargs kill -9
nohup java -jar sweater-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'