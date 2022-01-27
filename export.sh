export read_data_from_redis="${1}"
export config_xml_path="${2}"

docker-compose build --no-cache
docker-compose up --force-recreate