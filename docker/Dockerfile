##############
#build image##
##############
FROM gradle:7-jdk11

RUN  apt-get -y update \
     && apt-get -y autoremove \
     && apt-get clean \
     && apt-get install -y zip \
     unzip \
     && rm -rf /var/lib/apt/lists/*
# install jq to parse json within bash scripts
RUN curl -o /usr/local/bin/jq http://stedolan.github.io/jq/download/linux64/jq && \
  chmod +x /usr/local/bin/jq
