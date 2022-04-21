#!/bin/bash

# Дирректория где хранятся результаты алюра
ALLURE_RESULTS_DIRECTORY="web-tests/"$1"/build/allure-results"
# Проект в который будет записываться отчет
PROJECT_ID=$2

# Получаем путь до баш крипта
SCRIPT_PATH="${BASH_SOURCE[0]}"
# Строим полный путь
DIR="$(cd "$(dirname "$SCRIPT_PATH")" >/dev/null 2>&1 && pwd)"
# Собираем файлы для отправки
FILES_TO_SEND=$(find $DIR/$ALLURE_RESULTS_DIRECTORY/ -type f | grep -v /$)

# Строим url с файлами
FILES=''
INDEX=0
for FILE in $FILES_TO_SEND; do
  FILES+="-F files[]=@$FILE "
  INDEX=$((INDEX+1))
     if [ $((INDEX % 1000)) -eq 0 ]; then
        # Отправляем через curl запрос на заливку отчета
        #set -o xtrace
        echo "------------------SEND-RESULTS------------------"
        sendResultsResponse=$(curl  -u $WEB_LOGIN:$WEB_PASSWORD -X POST $ALLURE_SERVER"/allure-docker-service/send-results?project_id=$PROJECT_ID" -H 'Content-Type: multipart/form-data' $FILES -ik)
        echo "$sendResultsResponse"
        FILES=''
      fi
done

# Отправляем через curl запрос на заливку отчета
#set -o xtrace
echo "------------------SEND-RESULTS------------------"
sendResultsResponse=$(curl  -u $WEB_LOGIN:$WEB_PASSWORD -X POST $ALLURE_SERVER"/allure-docker-service/send-results?project_id=$PROJECT_ID" -H 'Content-Type: multipart/form-data' $FILES -ik)
echo "$sendResultsResponse"