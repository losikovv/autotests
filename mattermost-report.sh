#!/bin/bash

# Дирректория где хранятся результаты алюра
ALLURE_RESULTS_DIRECTORY="web-tests/"$1"/build/allure-results"
# Проект в который будет записываться отчет
PROJECT_ID=$2

# Очистить директорию с результатами
clearResult=$(curl -u $WEB_LOGIN:$WEB_PASSWORD -X GET $ALLURE_SERVER"/allure-docker-service/clean-results?project_id=$PROJECT_ID" -H  "accept: */*")
echo "$clearResult"
# Получаем путь до баш крипта
SCRIPT_PATH="${BASH_SOURCE[0]}"
# Строим полный путь
DIR="$(cd "$(dirname "$SCRIPT_PATH")" >/dev/null 2>&1 && pwd)"
# Собираем файлы для отправки
FILES_TO_SEND=$(find $DIR/$ALLURE_RESULTS_DIRECTORY/ -type f | grep -v /$)

# Если нет файлов прерываем процесс
if [ -z "$FILES_TO_SEND" ]; then
  echo "Nothing to send"
  exit 0
fi

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
# Если нужно сгенерировать отчет, нужно отправить запрос на эндпоинт GET /generate-report и выставить >> CHECK_RESULTS_EVERY_SECONDS: NONE в контейнере с отчетами
#curl -X GET 'http://localhost:5050/allure-docker-service/generate-report?project_id=default&execution_name=test_exec&execution_from=http://local.com&execution_type=bobobob'
echo "------------------GENERATE-REPORT------------------"
EXECUTION_NAME='Gitlab-CI'
EXECUTION_FROM=$CI_PIPELINE_URL
EXECUTION_TYPE='Gitlab-Gradle'
# Отправляем через curl запрос на генерацию отчета
generateResponse=$(curl -u $WEB_LOGIN:$WEB_PASSWORD -X GET $ALLURE_SERVER"/allure-docker-service/generate-report?project_id=$PROJECT_ID&execution_name=$EXECUTION_NAME&execution_from=$EXECUTION_FROM&execution_type=$EXECUTION_TYPE" $FILES)
echo "$generateResponse"

allureReport=$(sed -E 's/.*"report_url":"?([^,"]*)"?.*/\1/' <<<"$generateResponse")

# Директория где хранятся результаты
GRADLE_RESULTS_DIRECTORY="web-tests/"$1"/build/reports/tests/"$3

text="$(sed 's:^ *::g' < $DIR/$GRADLE_RESULTS_DIRECTORY/index.html | tr -d \\n)"

# Количество всех тестов
total=$(sed 's:.*<div class="infoBox" id="tests"><div class="counter">\([^<]*\)<.*:\1:' <<<"$text")

# Количество упавших тестов
failures=$(sed 's:.*<div class="infoBox" id="failures"><div class="counter">\([^<]*\)<.*:\1:' <<<"$text")
# Количество проигнорированных тестов
ignored=$(sed 's:.*<div class="infoBox" id="ignored"><div class="counter">\([^<]*\)<.*:\1:' <<<"$text")
# Время прогона
duration=$(sed 's:.*<div class="infoBox" id="duration"><div class="counter">\([^<]*\)<.*:\1:' <<<"$text")
# Процент пройденных тестов
successRate=$(sed 's:.*<div class="percent">\([^<]*\)<.*:\1:' <<<"$text")

if ["$failures"!="0"]; then
  #Text message
  TEXT_MSG="*Результаты:* \n*Рабочее окружение: :* $3 \n*Продолжительность:* $duration \n *Всего сценариев:* $total \n *Всего успешных тестов:* $total-$failures-$ignored \n *Всего упавших тестов: :* $failures \n *Всего пропущенных тестов:* $ignored \n *% прошедших тестов:* $successRate \n *Отчет доступен по ссылке:* $allureReport \n"

  #set -o xtrace
  echo "------------------SEND-RESULTS------------------"
  sendResultsResponse=$(curl -ik -X POST $MATTERMOST_SERVER"/hooks/"$MATTERMOST_KEY -H 'Content-Type: application/json' -d "{\"text\": \"$TEXT_MSG\"}")
  echo "$sendResultsResponse"
else
  echo "no failed tests"
fi