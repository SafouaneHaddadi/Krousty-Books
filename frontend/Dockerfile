ARG NODE_VERSION=24.7.0-alpine
FROM node:${NODE_VERSION} AS builder

WORKDIR /app

COPY package.json package-lock.json ./
RUN --mount=type=cache,target=/root/.npm npm ci

COPY . .
RUN npm run build -- --configuration production

FROM nginxinc/nginx-unprivileged:alpine3.22

USER nginx

COPY nginx.conf /etc/nginx/nginx.conf

#COPY --chown=nginx:nginx --from=builder /app/dist/frontend /usr/share/nginx/html
COPY --chown=nginx:nginx --from=builder /app/dist/frontend/browser/. /usr/share/nginx/html/

#Faux, Nginx n'écoute pas sur 8080
#EXPOSE 8080 
EXPOSE 80

ENTRYPOINT ["nginx", "-c", "/etc/nginx/nginx.conf"]
CMD ["-g", "daemon off;"]
