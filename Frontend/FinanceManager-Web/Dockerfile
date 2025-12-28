
# a complete node image for have the build tools
FROM node:22-alpine AS build

WORKDIR /app

# Copy the dependency files
COPY package*.json ./

# install all dependencies
RUN npm ci --legacy-peer-deps

# copy the code
COPY . .

RUN echo "export const environment = { production: true, apiUrl: 'http://localhost:8080/api' };" > src/environments/environment.ts
RUN echo "export const environment = { production: true, apiUrl: 'http://localhost:8080/api' };" > src/environments/environment.development.ts

# compile for production (generate the dist/)
RUN npm run build

FROM nginx:alpine

# remove the default config of nginx
RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/conf.d/

# copy the compiled files of prev step for the nginx
COPY --from=build /app/dist/FinanceManager-Web/browser /usr/share/nginx/html

# expose the port 80
EXPOSE 80
