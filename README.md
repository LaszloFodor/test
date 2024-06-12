# build
./mvnw clean package

# docker-compose build
docker-compose -f docker-compose-infra.yml build

# docker-compose indítás
docker-compose -f docker-compose-infra.yml up
