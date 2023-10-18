FROM --platform=linux/x86_64 amazoncorretto:21-alpine-jdk

RUN apk update \
    && apk upgrade \
    && apk --no-cache add --update bash tcl apache2 ca-certificates \
    apk-tools curl build-base supervisor cups-client dcron bind-tools rsync libxml2-utils libxslt unzip dos2unix && \
    rm -rf /var/cache/apk/*

ENV USER_GROUP=jaitechltd
ENV USER_NAME=jaitechltd

# create usergroup & user
RUN addgroup -S $USER_GROUP && \
    adduser -S $USER_NAME -G $USER_GROUP && \
    mkdir /home/app && \
    chown $USER_NAME /home/app

USER $USER_NAME
WORKDIR /home/app

COPY --chown=$USER_NAME:$USER_GROUP /build/libs/*.jar app.jar

EXPOSE ${SERVER_PORT} 8080 8090

ENTRYPOINT exec java \
           -jar app.jar --spring.config.location=classpath:/application.properties