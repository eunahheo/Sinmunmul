#!/usr/bin/env bash

if [ "x$1" == "x" ]; then
  echo "usage: $0 j6a406@cluster.p.ssafy.io"
  exit 0
fi

ssh -i ../hadoop.pem $1 'bash -ic "mkdir ~/hadoop_tmp"'

ssh -i ../hadoop.pem $1 'bash -ic "echo \"# Automatically added\" >> ~/.bashrc"'
ssh -i ../hadoop.pem $1 'bash -ic "echo \"export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64\" >> ~/.bashrc"'
ssh -i ../hadoop.pem $1 'bash -ic "echo \"export HADOOP_HOME=/usr/local/hadoop\" >> ~/.bashrc"'
ssh -i ../hadoop.pem $1 'bash -ic "echo \"export PATH=\\\$HADOOP_HOME/bin:\\\$HADOOP_HOME/sbin:\\\$PATH\" >> ~/.bashrc"'

ssh -i ../hadoop.pem $1 'bash -ic "ln -s /usr/local/hadoop ~/hadoop"'

