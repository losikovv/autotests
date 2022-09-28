#!/bin/bash

source .env

# Получаем путь до баш крипта
SCRIPT_PATH="${BASH_SOURCE[0]}"
# Строим полный путь
DIR="$(cd "$(dirname "$SCRIPT_PATH")" >/dev/null 2>&1 && pwd)"
# Директория где хранятся результаты
GRADLE_RESULTS_DIRECTORY="web-tests/"$1"/build/reports/tests/"$2

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

if [[ "$failures" != "0" || $4 == "true" ]]; then
  #Text message
  TEXT_MSG="*Результаты:* \n*Рабочее окружение: :* $2 \n*Продолжительность:* $duration \n *Всего сценариев:* $total \n *Всего успешных тестов:* $(($total-$failures-$ignored)) \n *Всего упавших тестов: :* $failures \n *Всего пропущенных тестов:* $ignored \n *% прошедших тестов:* $successRate \n *CI JOB URL* $CI_JOB_URL \n *CI_PIPELINE_URL* $CI_PIPELINE_URL \n *Отчет доступен по ссылке:* ${ALLURE_REPORT_URL} \n"

  set -o xtrace
  echo "------------------SEND-RESULTS------------------"
  sendResultsResponse=$(curl -ik -X POST $MATTERMOST_SERVER"/hooks/"$3 -H 'Content-Type: application/json' -d "{\"text\": \"$TEXT_MSG\"}")
  echo "$sendResultsResponse"
else
  echo "no failed tests"
fi