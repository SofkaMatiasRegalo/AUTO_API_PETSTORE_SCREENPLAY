
---

## description: 'Skill que especifica la arquitectura y buenas prácticas para el mapeo centralizado de endpoints y recursos de una API REST (Swagger), garantizando la reutilización, la prevención de URLs hardcodeadas y la correcta gestión de parámetros de ruta (Path Params).'

# Skill: api-resource-mapper [QA]

## Responsabilidad

Centralizar, mapear y gestionar todos los recursos (endpoints) expuestos en la documentación de la API (ej. Swagger). Su objetivo es proporcionar constantes inmutables que las Tareas (Tasks) y las Interacciones puedan consumir de manera segura, eliminando la duplicación de rutas y facilitando el mantenimiento cuando las versiones de la API cambian.

---

## ⚠️ REGLA ABSOLUTA — Gestión de Recursos REST

```
PROHIBIDO ABSOLUTAMENTE:
  - Hardcodear URLs o URIs directamente dentro de las Tasks o Interacciones.
  - Concatenar cadenas manualmente para formar parámetros de ruta (ej. "/user/" + username).
  - Mezclar la configuración de la Base URL con los endpoints específicos en la misma clase de constantes.

SIEMPRE usar:
  - Enumeradores (`enum`) o clases estáticas (`public static final String`) para definir las rutas.
  - Variables de ruta estandarizadas (ej. `{username}`) para que Serenity Rest / Rest Assured las reemplace dinámicamente usando `pathParam()`.
  - Nomenclatura descriptiva basada en el dominio del negocio (ej. `CREATE_USER`, `GET_USER_BY_USERNAME`).

```

---

## 1. Arquitectura de Mapeo de Recursos

En Screenplay para APIs, los recursos actúan como el equivalente a los "Targets" en la automatización Web. No contienen lógica de ejecución, únicamente definen "dónde" se realizará la interacción.

```mermaid
graph TD
    Swagger[Swagger / API Docs] --> Mapper(API Resource Mapper)
    Mapper --> BaseURL[Base URL: Configurada en serenity.conf]
    Mapper --> Endpoints[Endpoints: Variables inmutables]
    Endpoints --> PathParams(Path Params: /{username})
    Endpoints --> TasksAPI(Tasks: Consumen los endpoints)
    TasksAPI --> SerenityRest(SerenityRest Interactions: Post.to, Get.resource)

```

---

## 2. Implementación Técnica (Caso PetStore)

### ❌ Código Anti-Patrón (URLs Hardcodeadas y Concatenación)

Viola el principio de responsabilidad única y acopla la ruta a la tarea de negocio.

```java
public class ConsultarUsuario implements Task {
    private final String username;
    
    // ❌ La URL está quemada y concatenada manualmente en la Tarea
    public <T extends Actor> void performAs(T actor) {
        SerenityRest.given()
            .get("https://petstore.swagger.io/v2/user/" + username); 
    }
}

```

### ✅ Enfoque Arquitectónico Screenplay (Resource Mapper)

Separa las rutas en un Enum o clase de constantes, permitiendo a Serenity inyectar los parámetros de forma limpia.

```java
// 1. MAPPER: Clases o Enums dedicados SOLO a definir recursos de la API
package uy.equipo6.petstore.utils.endpoints;

public enum PetStoreEndpoints {
    
    // Base de usuarios
    CREATE_USER("/user"),
    
    // Operaciones sobre un usuario específico (Consulta, Edición, Borrado)
    USER_OPERATIONS("/user/{username}");

    private final String path;

    PetStoreEndpoints(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

// 2. TASK: Consumo limpio usando path parameters
package uy.equipo6.petstore.tasks;

import static uy.equipo6.petstore.utils.endpoints.PetStoreEndpoints.USER_OPERATIONS;

public class ConsultarUsuario implements Task {
    private final String username;
    
    // ... constructor ...

    @Step("{0} consulta la información del usuario #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Get.resource(USER_OPERATIONS.path())
               .with(request -> request.pathParam("username", username))
        );
    }
}

```

---

## 3. Riesgos a Mitigar

1. **Gestión de Entornos Múltiples:** Evitar mapear dominios completos (ej. `https://dev.api...`) en las constantes. *Solución: El Mapeador solo debe contener las rutas relativas (URIs). La `restapi.baseurl` debe vivir en el archivo `serenity.conf`.*
2. **Caos de Versiones (v1, v2):** Si la API cambia de versión, modificar múltiples archivos es propenso a errores. *Solución: Agrupar los endpoints por versión en interfaces o incluir la versión en una variable base dentro del Mapper si no está a nivel de gateway.*

---

## Entregable: Estructura del Proyecto en el IDE

Alineado con nuestra estructura de dominios, el framework debe organizar los recursos así:

```text
├── src/main/java/uy/equipo6/petstore/
│   ├── tasks/                  # Tareas que consumen los endpoints (ej. CrearUsuario.java)
│   ├── models/                 # POJOs del Body
│   └── utils/
│       └── endpoints/          # 📍 AQUÍ VIVE EL MAPPER
│           └── PetStoreEndpoints.java 
└── src/test/resources/
    └── serenity.conf           # 📍 AQUÍ VIVE LA BASE URL (ej. restapi.baseurl = "https://petstore.swagger.io/v2")

```

---

## Proceso de Implementación

```
PASO 1 → Analizar el contrato Swagger y agrupar los endpoints por controlador (ej. User, Pet, Store).
PASO 2 → Identificar las rutas base (ej. `/user`) y las rutas dinámicas con parámetros (ej. `/user/{username}`).
PASO 3 → Crear el Enum `PetStoreEndpoints` centralizando estas URIs detectadas.
PASO 4 → Asegurar que la Base URL esté correctamente apuntada en `serenity.conf`.
PASO 5 → Importar el Enum estáticamente en las `Tasks` e inyectar variables con `.pathParam()`.

```

## Reporte

```
📍 API-RESOURCE-MAPPER [QA] — REPORTE DE ESTRUCTURA
══════════════════════════════════════════════════
Contratos (Swagger) analizados:  [ ] SÍ / [ ] NO
Mappers creados aisladamente:    X
Uso exclusivo de variables relativas: X
Parámetros inyectados (sin concatenar):✅
Base URL en serenity.conf:       ✅

Estado del Patrón: [IMPLEMENTADO | EN DESARROLLO]
══════════════════════════════════════════════════

```

---
