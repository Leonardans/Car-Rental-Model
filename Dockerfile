FROM ubuntu:latest
LABEL authors="leonardan"

ENTRYPOINT ["top", "-b"]