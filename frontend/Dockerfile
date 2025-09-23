# ---- Build stage ----
FROM node:24.8-bookworm AS build

# Set work directory
WORKDIR /app

# Copy package.json and package-lock.json first
COPY package.json .
COPY package-lock.json .

# Download dependencies (cached if pom.xml unchanged)
RUN npm ci

# Copy everything else, except files in .dockerignore
# Copy eslint.config.js, index.html, package.json, package-lock.json, public folder, src folder, tsconfig.app.json, tsconfig.json, tsconfig.node.json, vite.config.ts
COPY . . 

# Build the application
RUN npm run build


FROM alpine:3.22 AS exporter
WORKDIR /dist

COPY --from=build /app/dist .