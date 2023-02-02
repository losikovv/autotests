## e2e автотесты для проектов 
```
DS
SURGE
PB
CS
ODFS
DSPTCH
ODW
ETA
SHPCALC
ODSC
RES
SHAPI
INAPI
FNS
STS
TM
SA
STF
B2B
PM
```
### 1. Установка окружения
#### 1.1 Установка Homebrew
Если homebrew уже установлен, можно перейти к следующему пункту. 

Для тех у кого его нет, необходимо к терминале выполнить команду

`/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`

Далее устанавливаем расширение `cask` для `brew` необходимое для упрощения и повышения скорости установки

```
brew tap homebrew/cask-versions
brew update
brew tap homebrew/cask
```

#### 1.2 Установка Java
При наличии установленного Homebrew: 

```
brew tap adoptopenjdk/openjdk
brew install adoptopenjdk11 --cask
```
Для ОС Windows необходимо скачать и установить версию jdk11 для вашей версии со страницы 
https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html

##### 1.2.1 Переключение версий Java

```
export JAVA_11_HOME=$(/usr/libexec/java_home -v11) -- задает перменную
alias java11='export JAVA_HOME=$JAVA_11_HOME' -- создаем алиас для быстрого переключения
```
Перезагружаем профиль для применения переменных

`source ~/.zshrc`

или bash_profile если вы старовер

`source ~/.bash_profile`

Выбирает текущей версией Java11

`java11`

Проверяем текущую версию Java

`java -version`

```
Если необходимо две версии то общий процесс будет выглядеть следующим образом 

brew install adoptopenjdk/openjdk/adoptopenjdk8 --cask
brew install adoptopenjdk11 --cask

export JAVA_8_HOME=$(/usr/libexec/java_home -v8)
export JAVA_11_HOME=$(/usr/libexec/java_home -v11)

alias java8='export JAVA_HOME=$JAVA_8_HOME'
alias java11='export JAVA_HOME=$JAVA_11_HOME'

source ~/.zshrc

java8 
java -version

java11
java -version
```

#### 1.3 Установка IDE
Переходим на страницу 

`https://www.jetbrains.com/ru-ru/idea/download/#section=mac`

для OC Windows, соответственно:

`https://www.jetbrains.com/ru-ru/idea/download/#section=windows`

скачиваем `Community Edition` и проходим обычный процесс установки

#### 2. Настройка среды разработки
После запуска жмем `Import Project` и в диалоге указываем файл `build.gradle` в папке `dev/automag`

Устанавливаем кодировку в настройках `(cmd+,) Editor → File Encodings`
```
Global Encoding = UTF-8
Project Encoding = UTF-8
Default encoding for properties → ставим галочку на Transparent native-to-ascii conversion → вибираем UTF-8
```
#### 3. Клонирование репозитория

#### 4. Настройка проекта
##### 4.1 Шифрование
В Кракен используется шифрование логинов и паролей, по этому перед запуском, необходимо создать файл с ключом.

Подробнее описано в [Confluence](https://wiki.sbmt.io/pages/viewpage.action?pageId=2210497018)

### 5. Запуск тестов
#### 5.1 Запуск тестов из консоли
#### 5.1.1 Через терминал
Для того, чтобы запустить тесты из консоли, нужно знать название искомого gradle-таска, узнать его название можно,
посмотрев параметр `groups` внутри аннотации `@Test` в ваших тестах, далее соотнеся с `ru.instamart.api.Group`
или `ru.instamart.reforged.Group` получим полное название вашей группы внутри проекта - например `prod_admin_smoke`,
и затем в файлах конфигурации `gradle` - лежащих в корне подпроекта `reforged` и `api` можно узнать искомое название таски,
напр. `apiV2InstamartRegress`, и запустить ее командой `gradle clean test apiV2InstamartRegress`

#### 5.1.1 По средствам IDE
Средствами IDE открыть вкладку `Gradle` - в правой части экрана на панели плагинов, и там просто запустить нужный прогон
из папки напр. `Kraken/api/Tasks/other/apiV2InstamartRegress`, или например `Kraken/reforged/Tasks/other/adminKeycloak` и
так же после прогона можно локально посмотреть аллюр-отчет через запуск `Kraken/reforged/Tasks/verification/allureServe` для 
ui и `Kraken/api/Tasks/verification/allureServe` для api

Очистить локальные данные перед/после прогона(ом) можно выполнив задачу `Kraken/<название_подпроекта>/Tasks/build/clean`
#### 5.2 Запуск тестов в Gitlab
Запуск тестов из Gitlab описан в [статье](https://wiki.sbmt.io/pages/viewpage.action?pageId=2308474075)