FROM gradle:jdk11
RUN apt-get update &&\
    wget https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_88.0.4324.96-_amd64.deb &&\
    apt install -y ./google-chrome-stable_88.0.4324.96-_amd64.deb &&\
    rm google-chrome-stable_88.0.4324.96-_amd64.deb &&\
    apt-get install -y chromium-chromedriver