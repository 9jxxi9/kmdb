FROM ubuntu:latest
LABEL authors="JB"

ENTRYPOINT ["top", "-b"]