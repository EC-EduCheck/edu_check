# 빌드 단계
FROM node:23-alpine AS build

ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL

ARG VITE_HOLIDAY_API_KEY
ENV VITE_HOLIDAY_API_KEY=$VITE_HOLIDAY_API_KEY

ARG VITE_HOLIDAY_BASE_URL
ENV VITE_HOLIDAY_BASE_URL=$VITE_HOLIDAY_BASE_URL

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm ci

COPY . .

RUN npm run build

# 실행 단계
FROM nginx:mainline-alpine-slim

COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY ./templates /etc/nginx/templates

EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]