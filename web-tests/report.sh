#!/bin/bash

# Дирректория где хранятся результаты алюра
ALLURE_RESULTS_DIRECTORY=$1
# URL куда будут отправляться данные
ALLURE_SERVER=$2
# Проект в который будет записываться отчет
PROJECT_ID=$3

# Получаем путь до баш крипта
SCRIPT_PATH="${BASH_SOURCE[0]}"
# Строим полный путь
DIR="$(cd "$(dirname "$SCRIPT_PATH")" >/dev/null 2>&1 && pwd)"
# Собираем файлы для отправки
FILES_TO_SEND=$(ls -dp $DIR/$ALLURE_RESULTS_DIRECTORY/* | grep -v /$)

# Если нет файлов прерываем процесс
if [ -z "$FILES_TO_SEND" ]; then
  exit 1
fi

# Строим url с файлами
FILES=''
for FILE in $FILES_TO_SEND; do
  FILES+="-F files[]=@$FILE "
done

# Отправляем через curl запрос на заливку отчета
set -o xtrace
echo "------------------SEND-RESULTS------------------"
curl -X POST "$ALLURE_SERVER/allure-docker-service/send-results?project_id=$PROJECT_ID" -H 'Content-Type: multipart/form-data' $FILES -ik


# Если нужно сгенерировать отчет, нужно отправить запрос на эндпоинт GET /generate-report и выставить >> CHECK_RESULTS_EVERY_SECONDS: NONE в контейнере с отчетами
#curl -X GET 'http://localhost:5050/allure-docker-service/generate-report?project_id=default&execution_name=test_exec&execution_from=http://local.com&execution_type=bobobob'
echo "------------------GENERATE-REPORT------------------"
EXECUTION_NAME='Gitlab-CI'
EXECUTION_FROM='https://gitlab.sbermarket.tech/qa/automag/'
EXECUTION_TYPE='Gitlab-Gradle'
# Отправляем через curl запрос на генерацию отчета
RESPONSE=$(curl -X GET "$ALLURE_SERVER/allure-docker-service/generate-report?project_id=$PROJECT_ID&execution_name=$EXECUTION_NAME&execution_from=$EXECUTION_FROM&execution_type=$EXECUTION_TYPE" $FILES)