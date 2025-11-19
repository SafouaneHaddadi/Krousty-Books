# ---------- BUILD STAGE ---------
ARG NODE_VERSION=24-alpine
FROM node:${NODE_VERSION} AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

RUN npm install bootstrap @popperjs/core

COPY . .

# Build production (utilisé pour le mode prod)
RUN npm run build -- --configuration production

# ------- MODE DEV (pr le hot reload) ------
FROM node:${NODE_VERSION} AS development

WORKDIR /app

COPY package*.json ./
RUN npm ci
RUN npm install bootstrap @popperjs/core

COPY . .

EXPOSE 4200

CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--poll", "1000"]

# ------ MODE PROD (Nginx) ---------
FROM nginxinc/nginx-unprivileged:alpine-slim AS production

COPY nginx.conf /etc/nginx/nginx.conf

COPY --chown=nginx:nginx --from=builder /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]