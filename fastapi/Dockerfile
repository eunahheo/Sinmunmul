# KoNLPy는 자바가 필요해서 우분투 환경에 자바랑 파이썬 설치하기
FROM ubuntu:latest

ENV LANG=C.UTF-8
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
  apt-get install -y --no-install-recommends tzdata g++ curl

# install java
RUN apt-get install -y openjdk-8-jdk
ENV JAVA_HOME="/usr/lib/jvm/java-1.8-openjdk"

# install python
RUN apt-get install -y python3-pip python3-dev
RUN cd /usr/local/bin && \
  ln -s /usr/bin/python3 python && \
  ln -s /usr/bin/pip3 pip && \
  pip install --upgrade pip

RUN bash <(curl -s https://raw.githubusercontent.com/konlpy/konlpy/master/scripts/mecab.sh)

# apt clean
RUN apt-get clean && \
  rm -rf /var/lib/apt/lists/*

#
WORKDIR /code

#
COPY ./ /code
COPY ./requirements.txt /code/requirements.txt

#
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

#
COPY ./app /code/app

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "3031"]