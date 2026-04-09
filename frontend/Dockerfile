#FROM node:20-alpine AS builder
#
#WORKDIR /app
#
#COPY package*.json ./
#RUN npm install
#
#COPY . .
#RUN npm run build
#
## Stage 2: Serve with Nginx
#FROM nginx:1.27-alpine 
#
## Copy build output to Nginx HTML folder
#COPY target /usr/share/nginx/html
#
## Copy custom Nginx config (optional, for API proxying)
##COPY nginx.conf /etc/nginx/conf.d/default.conf
#
## Expose port
#EXPOSE 80
#
## Start Nginx
##CMD ["nginx", "-g", "daemon off;"]

FROM node:24-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80