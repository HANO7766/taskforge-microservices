# TaskForge - Sistema de Gestión de Proyectos con Microservicios

## 🚀 Descripción
TaskForge es un sistema avanzado de gestión de proyectos basado en arquitectura de microservicios, diseñado para manejar proyectos, tareas, usuarios y notificaciones de manera escalable.

## 🏗️ Arquitectura
- **Arquitectura**: Microservicios
- **Service Discovery**: Eureka Server
- **API Gateway**: Spring Cloud Gateway
- **Comunicación**: HTTP REST + RabbitMQ (eventos asíncronos)
- **Base de Datos**: PostgreSQL (una instancia por servicio)
- **Autenticación**: JWT Stateless

## 📦 Módulos del Proyecto

### 1. **Eureka Server** (`eureka-server`)
- Servidor de descubrimiento de microservicios
- Puerto: `8761`

### 2. **API Gateway** (`api-gateway`)
- Enrutador centralizado
- Validación de JWT
- Puerto: `8080`

### 3. **User Service** (`user-service`)
- Gestión de usuarios (registro, login)
- Autenticación JWT
- Encriptación bcrypt

### 4. **Project Service** (`project-service`)
- Gestión de proyectos y tareas
- Publicación de eventos a RabbitMQ

### 5. **Notification Service** (`notification-service`)
- Consumo de eventos desde RabbitMQ
- Notificaciones asíncronas (simulación de emails)

### 6. **AI Assistant Service** (`ai-assistant-service`)
- Integración con DeepSeek AI
- Asistente inteligente para tareas

### 7. **Common Library** (`common-lib`)
- DTOs y utilidades compartidas
- Patrón `ApiResponse` estandarizado

## 🛠️ Tecnologías
- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Cloud 2023.0.1**
- **Spring AI 1.0.0-M1** (DeepSeek)
- **Maven 3.9.x**
- **PostgreSQL 16**
- **RabbitMQ 3**
- **Docker & Docker Compose**

## 🚀 Cómo Ejecutar

### 1. Requisitos Previos
- JDK 21
- Maven 3.9+
- Docker & Docker Compose
- Git

### 2. Iniciar Infraestructura
```bash
docker-compose up -d
```

### 3. Compilar y Ejecutar Microservicios
```bash
mvn clean compile
mvn spring-boot:run
```

### 4. Puertos de los Servicios
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **User Service**: 8081
- **Project Service**: 8082
- **Notification Service**: 8083
- **AI Assistant Service**: 8084

## 📁 Estructura del Proyecto
```
taskforge/
├── pom.xml                      # POM padre
├── docker-compose.yml           # Infraestructura Docker
├── .gitignore
├── README.md
├── common-lib/                  # Librería común
├── eureka-server/              # Servicio de descubrimiento
├── api-gateway/                # API Gateway
├── user-service/               # Servicio de usuarios
├── project-service/            # Servicio de proyectos
├── notification-service/       # Servicio de notificaciones
└── ai-assistant-service/       # Servicio de IA
```

## 🔐 Autenticación
El sistema utiliza JWT (JSON Web Tokens) para autenticación stateless. Los tokens se generan en el login y deben incluirse en el header `Authorization` de las peticiones protegidas.

## 📡 Comunicación entre Servicios
1. **Síncrona**: REST HTTP a través del API Gateway
2. **Asíncrona**: RabbitMQ para eventos (creación/actualización de tareas)

## 🤖 Integración con IA
El servicio `ai-assistant-service` se integra con DeepSeek AI para proporcionar asistencia inteligente. Configurado mediante `spring-ai-openai-spring-boot-starter` redirigido a la API de DeepSeek.

## 📄 Licencia
MIT
