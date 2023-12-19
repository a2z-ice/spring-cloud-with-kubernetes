### STAGE 1: Build ###
FROM node:18.16 AS build
RUN npm install -g @angular/cli@16
WORKDIR /webui-admin-web
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN ng version
RUN ng build --aot --output-hashing=all

### STAGE 2: Run ###
FROM nginx:1.23.3
COPY ./deploy/webui-admin-web.conf /etc/nginx/nginx.conf
COPY --from=build /webui-admin-web/dist/webui-admin-web /usr/share/nginx/html