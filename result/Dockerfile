FROM node:18-slim

# add curl for healthcheck
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl tini && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /usr/local/app

# have nodemon available for local dev use (file watching)
RUN npm install -g nodemon

COPY package*.json ./

RUN npm ci && \
 npm cache clean --force && \
 mv /usr/local/app/node_modules /node_modules

RUN apt-get update && apt-get install -y iputils-ping
RUN apt-get update && apt-get install -y redis-tools

COPY . .

ENV PORT=4000
EXPOSE 4000

ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["node", "server.js"]
