FROM ubuntu:latest
LABEL authors="carlo"

ENTRYPOINT ["top", "-b"]