FROM gradle:jdk11
RUN apt-get update &&\
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb &&\
    apt install -y ./google-chrome-stable_current_amd64.deb &&\
    rm google-chrome-stable_current_amd64.deb &&\
    apt-get install -y chromium-chromedriver