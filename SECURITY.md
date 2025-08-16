# Política de Seguridad

## 🔒 Reporte de Vulnerabilidades de Seguridad

Tomamos muy en serio la seguridad de la API Backend de CodeCrafters. Si descubres una vulnerabilidad de seguridad, por favor repórtala de manera responsable.

### Cómo Reportar

**Por favor NO reportes vulnerabilidades de seguridad a través de issues públicos de GitHub.**

En su lugar, envía un correo electrónico a: **javier25arojas@gmail.com**

Incluye la siguiente información:
- Tipo de problema (ej: desbordamiento de buffer, inyección SQL, cross-site scripting, etc.)
- Rutas completas de los archivos fuente relacionados con la manifestación del problema
- La ubicación del código fuente afectado (tag/branch/commit o URL directa)
- Cualquier configuración especial requerida para reproducir el problema
- Instrucciones paso a paso para reproducir el problema
- Código de prueba de concepto o exploit (si es posible)
- Impacto del problema, incluyendo cómo un atacante podría explotar el problema

### Cronograma de Respuesta

- **Respuesta Inicial**: Dentro de 48 horas de recibir tu reporte
- **Actualización de Estado**: Dentro de 7 días con un plan detallado para abordar la vulnerabilidad
- **Resolución**: Los parches de seguridad serán priorizados y desplegados lo más rápido posible

## 🛡️ Medidas de Seguridad

### Autenticación y Autorización

- **Tokens JWT**: Autenticación segura basada en tokens con expiración configurable
- **Integración OAuth2**: Google OAuth2 para autenticación segura de terceros
- **Control de Acceso Basado en Roles**: Sistema de permisos de tres niveles (Admin, User, Caregiver)
- **Seguridad de Contraseñas**: Hash BCrypt con salt para todas las contraseñas de usuario
- **Gestión de Sesiones**: Tokens JWT sin estado con manejo adecuado de expiración

### Protección de Datos

- **Validación de Entrada**: Validación completa en todos los endpoints usando Spring Validation
- **Prevención de Inyección SQL**: JPA/Hibernate con consultas parametrizadas
- **Protección XSS**: Codificación de salida y cabeceras Content Security Policy
- **Configuración CORS**: Solicitudes cross-origin restringidas solo a dominios aprobados
- **Datos Sensibles**: Variables de entorno para todos los secretos y claves API

### Seguridad de Comunicación

- **Aplicación HTTPS**: TLS 1.2+ requerido para todos los despliegues de producción
- **Seguridad WebSocket**: Autenticación basada en JWT para conexiones WebSocket
- **Limitación de Rate API**: Protección contra ataques de fuerza bruta y DDoS
- **Límites de Tamaño de Solicitud**: Restricciones de tamaño máximo de payload

### Seguridad de Base de Datos

- **Seguridad de Conexión**: Conexiones de base de datos encriptadas
- **Separación de Privilegios**: Usuario de base de datos con permisos mínimos requeridos
- **Encriptación de Datos**: Datos sensibles encriptados en reposo
- **Seguridad de Respaldos**: Respaldos de base de datos encriptados

### Seguridad de Infraestructura

- **Separación de Entornos**: Entornos aislados de desarrollo, staging y producción
- **Gestión de Secretos**: Sin secretos hardcodeados en el código fuente
- **Escaneo de Dependencias**: Actualizaciones regulares y escaneo de vulnerabilidades de dependencias
- **Seguridad de Contenedores**: Imágenes Docker construidas desde imágenes base mínimas con actualizaciones de seguridad

## 🔍 Pruebas de Seguridad

### Verificaciones Automatizadas de Seguridad

- **Análisis Estático de Código**: Escaneo de seguridad integrado en el pipeline CI/CD
- **Vulnerabilidades de Dependencias**: Escaneo regular con Gradle dependency-check
- **Pruebas Unitarias**: Casos de prueba enfocados en seguridad para autenticación y autorización
- **Pruebas de Integración**: Pruebas de seguridad end-to-end de endpoints API

### Pruebas Manuales de Seguridad

- **Pruebas de Penetración**: Evaluaciones regulares de seguridad
- **Revisiones de Código**: Revisiones por pares enfocadas en seguridad para todos los cambios
- **Pruebas de Autenticación**: Verificación de todos los flujos de autenticación y casos edge

## 📋 Configuración de Seguridad

### Variables de Entorno Requeridas

```properties
# Seguridad JWT
security.jwt.secret-key=<clave-secreta-fuerte-256-bits>
security.jwt.expiration-time=86400000

# Seguridad de Base de Datos
spring.datasource.url=jdbc:mariadb://localhost:3306/database?useSSL=true
spring.datasource.username=<usuario-privilegios-limitados>
spring.datasource.password=<contraseña-fuerte-base-datos>

# Seguridad OAuth2
spring.security.oauth2.client.registration.google.client-id=<google-client-id>
spring.security.oauth2.client.registration.google.client-secret=<google-client-secret>

# Seguridad de Email
spring.mail.username=<nombre-usuario-email>
spring.mail.password=<contraseña-especifica-app>

# Seguridad de Subida de Archivos
cloudinary.cloud_name=<nombre-cloud-cloudinary>
cloudinary.api_key=<clave-api-cloudinary>
cloudinary.api_secret=<secreto-api-cloudinary>
```

### Cabeceras de Seguridad

La aplicación incluye automáticamente cabeceras de seguridad:

- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`
- `Strict-Transport-Security: max-age=31536000; includeSubDomains`
- `Content-Security-Policy: default-src 'self'`

### Configuración CORS

```java
// Configurado en CorsConfig.java
@CrossOrigin(origins = {"http://localhost:3000", "https://tudominio.com"},
            allowedHeaders = "*",
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
// Solo orígenes permitidos pueden hacer solicitudes
```

## 🚨 Consideraciones de Seguridad Conocidas

### Limitaciones Actuales

1. **Limitación de Rate**: Actualmente limitación básica de rate - considerar implementar limitación basada en Redis para producción
2. **Logging de Auditoría**: Logging básico implementado - considerar logging centralizado de eventos de seguridad
3. **Versionado de API**: Considerar implementar versionado de API para compatibilidad hacia atrás
4. **Subida de Archivos**: Validación de tipo de archivo implementada - considerar escaneo adicional de malware

### Mejoras Recomendadas

1. **Autenticación Multi-Factor**: Implementación de 2FA/MFA para cuentas de administrador
2. **API Gateway**: Considerar implementar un API gateway para capas adicionales de seguridad
3. **Monitoreo de Seguridad**: Integración con herramientas de monitoreo de seguridad (SIEM)
4. **Pruebas de Penetración**: Evaluaciones regulares de seguridad por terceros

## 📚 Recursos de Seguridad

### Documentación

- [Documentación de Spring Security](https://spring.io/projects/spring-security)
- [Hoja de Trucos OWASP Spring Security](https://cheatsheetseries.owasp.org/cheatsheets/Spring_Security_Cheat_Sheet.html)
- [Mejores Prácticas de Seguridad JWT](https://auth0.com/blog/a-look-at-the-latest-draft-for-jwt-bcp/)

### Herramientas de Seguridad

- **Dependency Check**: `./gradlew dependencyCheckAnalyze`
- **OWASP ZAP**: Para pruebas de penetración
- **SonarQube**: Para análisis estático de código

## 🔄 Actualizaciones de Seguridad

Esta política de seguridad se revisa y actualiza regularmente. Última actualización: **Agosto 2025**

Para preguntas sobre esta política de seguridad, por favor contacta: javier25arojas@gmail.com