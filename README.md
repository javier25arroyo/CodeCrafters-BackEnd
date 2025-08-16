# CodeCrafters Backend API

Una API REST desarrollada con Spring Boot para el proyecto CodeCrafters, que proporciona servicios de autenticación, gestión de usuarios, juegos educativos, logros y comunicación en tiempo real con WebSockets.

## 🚀 Tecnologías

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.2.5** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **MariaDB** - Base de datos principal
- **H2** - Base de datos para testing
- **JWT** - Tokens de autenticación
- **Google OAuth2** - Autenticación con Google
- **Cloudinary** - Almacenamiento de imágenes
- **Spring Mail** - Envío de correos electrónicos
- **Spring WebSocket** - Comunicación en tiempo real
- **Gradle** - Gestión de dependencias
- **JUnit 5** - Testing framework

## 📋 Características

### Módulos Principales

- **Autenticación y Autorización**
  - Login con email/contraseña
  - Autenticación OAuth2 con Google
  - JWT para sesiones
  - Recuperación de contraseña por email
  - Roles de usuario (Admin, User, Caregiver)

- **Gestión de Usuarios**
  - Registro y perfil de usuarios
  - Configuraciones personalizadas
  - Historial de actividad y login
  - Sistema de niveles

- **Sistema de Juegos**
  - Catálogo de juegos educativos
  - Sesiones de juego
  - Reportes de progreso
  - Componentes de juego
  - Juegos favoritos
  

- **Sistema de Logros**
  - Logros personalizados
  - Tracking de progreso
  - Asociación usuario-logro

- **Notificaciones y Sugerencias**
  - Sistema de notificaciones
  - Sugerencias personalizadas
  - Gestión por administradores

- **Cuidadores (Caregivers)**
  - Gestión de cuidadores
  - Relación usuario-cuidador
  - Roles específicos

- **Gestión de Archivos**
  - Subida de archivos
  - Integración con Cloudinary

- **Comunicación en Tiempo Real**
  - WebSocket para chat y eventos
  - Sistema de presencia de usuarios
  - Notificaciones push

## 🛠️ Instalación y Configuración

### Prerrequisitos

- Java 21 o superior
- MariaDB 10.6+
- Gradle 8.0+

### 1. Clonar el repositorio

```bash
git clone https://github.com/javier25arroyo/CodeCrafters-BackEnd.git
cd CodeCrafters-BackEnd
```

### 2. Configurar la base de datos

Crear una base de datos MariaDB:

```sql
CREATE DATABASE tu-base-de-datos;
CREATE USER 'tu-usuario'@'localhost' IDENTIFIED BY 'tu-password';
GRANT ALL PRIVILEGES ON tu-base-de-datos.* TO 'tu-usuario'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar variables de entorno

Copiar `src/main/resources/application.properties` y ajustar:

```properties
# Base de datos
spring.datasource.url=jdbc:mariadb://localhost:3306/tu-base-de-datos
spring.datasource.username=tu-usuario
spring.datasource.password=tu-password

# JWT
security.jwt.secret-key=tu-secret-key-jwt

# Email (Gmail)
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-app-password

# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=tu-google-client-id
spring.security.oauth2.client.registration.google.client-secret=tu-google-client-secret

# Cloudinary
cloudinary.cloud_name=tu-cloud-name
cloudinary.api_key=tu-api-key
cloudinary.api_secret=tu-api-secret
```

### 4. Ejecutar la aplicación

```bash
# Compilar
./gradlew build

# Ejecutar
./gradlew bootRun
```

La API estará disponible en `http://localhost:8080`

## 📚 Endpoints Principales

### Autenticación
- `POST /auth/register` - Registro de usuario
- `POST /auth/authenticate` - Login
- `POST /auth/google` - Login con Google
- `POST /auth/forgot-password` - Recuperar contraseña

### Usuarios
- `GET /users/profile` - Obtener perfil
- `PUT /users/profile` - Actualizar perfil
- `GET /users/summary` - Resumen del usuario

### Juegos
- `GET /games` - Listar juegos
- `POST /games` - Crear juego (Admin)
- `GET /games/{id}` - Obtener juego
- `POST /game-sessions` - Registrar sesión de juego

### Logros
- `GET /achievements` - Listar logros
- `GET /users/{id}/achievements` - Logros del usuario

### Admin
- `GET /admin/users` - Listar usuarios (Admin)
- `POST /admin/suggestions` - Crear sugerencias

### WebSocket
- `/ws` - Conexión WebSocket principal
- `/timeline` - Eventos de timeline en tiempo real

## 🗂️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/project/demo/
│   │   ├── logic/
│   │   │   ├── entity/          # Entidades JPA
│   │   │   │   ├── auth/        # Autenticación
│   │   │   │   ├── user/        # Usuarios
│   │   │   │   ├── game/        # Juegos
│   │   │   │   ├── achievement/ # Logros
│   │   │   │   ├── caregiver/   # Cuidadores
│   │   │   │   ├── notification/ # Notificaciones
│   │   │   │   └── settings/    # Configuraciones
│   │   │   ├── exceptions/      # Manejo de errores
│   │   │   └── service/         # Servicios de lógica
│   │   ├── rest/               # Controladores REST
│   │   ├── service/            # Servicios de negocio
│   │   ├── timeline/           # WebSocket y tiempo real
│   │   └── DemoApplication.java
│   └── resources/
│       ├── application.properties
│       └── Insomnia.json       # Colección de endpoints
└── test/                       # Tests unitarios
```

## 🧪 Testing

```bash
# Ejecutar tests
./gradlew test

# Ejecutar tests con cobertura
./gradlew test jacocoTestReport
```


## 📄 Documentación API

Importar `src/main/resources/Insomnia.json` en Insomnia o Postman para probar todos los endpoints.

### Variables de Entorno para Testing

```properties
# Archivo: src/test/resources/application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.profiles.active=test
```

## 🔒 Seguridad

- Autenticación JWT obligatoria para endpoints protegidos
- CORS configurado para frontend específico
- Validación de roles por endpoint
- Encriptación de contraseñas con BCrypt
- Filtros CORS configurados
- Validación de entrada en todos los endpoints
- Manejo seguro de WebSockets con autenticación JWT

Para más detalles sobre seguridad, consulta [SECURITY.md](SECURITY.md).

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## 👥 Equipo

Desarrollado por el equipo CodeCrafters para el Proyecto 3.

---

⚡ **Versión**: 1.0.0
📧 **Soporte**: javier25arojas@gmail.com
