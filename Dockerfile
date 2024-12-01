FROM ubuntu:latest
LABEL authors="vahka"

ENTRYPOINT ["top", "-b"]