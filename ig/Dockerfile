FROM node:alpine
# extends official node image version 8.4

COPY . /app
# copy all files(ignore .dockerignore) to dir /app in image file

WORKDIR /app
# define work path as /app

# -----------front end-----------
RUN npm install --registry=https://registry.npm.taobao.org
# run npm install cmd to install dependency in /app


#

EXPOSE 7000
# expose port 7000