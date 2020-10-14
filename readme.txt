
// generate jooq classes
mvn clean install -Plocal -e

// generate docker image
mvn clean install dockerfile:build

docker image list

docker-compose up --build

docker-compose down

docker push  bunyawat/freemarker-app

docker ps

docker ps -a -q  --filter ancestor=bunyawat/freemarker-app

docker rm $(docker stop $(docker ps -a -q --filter ancestor=bunyawat/freemarker-app   --format="{{.ID}}"))

curl -o /dev/null -s -w "%{http_code}\n" localhost:8080/viewpage?pageName=goodbye