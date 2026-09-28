FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt update && \
    apt install -y openssl zip nano && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /proyecto

COPY script.sh .

RUN chmod +x script.sh

CMD ["/bin/bash"]
