FROM nginx

WORKDIR /app

RUN mkdir ./dist

ADD ./dist ./dist

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]