# 📅 Sistema de Asignación de Horarios con OptaPlanner

## 📋 Descripción

Este proyecto es un **back-end** desarrollado en **Java Spring Boot** que implementa un sistema inteligente de asignación de horarios académicos utilizando **OptaPlanner**. El sistema optimiza automáticamente la distribución de cursos, docentes, aulas y bloques horarios considerando múltiples restricciones y preferencias.

## 🚀 Características Principales

- **Optimización Inteligente**: Utiliza OptaPlanner para resolver el problema de asignación de horarios de manera óptima
- **Restricciones Flexibles**: Maneja restricciones duras (obligatorias) y blandas (preferibles)
- **API RESTful**: Endpoints para generar, consultar y gestionar horarios
- **Persistencia de Datos**: Integración con SQL Server para almacenar información
- **Arquitectura Limpia**: Implementa principios de Clean Architecture
- **Manejo de Excepciones**: Sistema robusto de manejo de errores

## 🏗️ Arquitectura del Sistema

### Entidades Principales

- **🏫 Aula**: Salones con capacidad y tipo (laboratorio/teórico)
- **👨‍🏫 Docente**: Profesores con unidades académicas y horas contratadas
- **📚 Curso**: Materias con tipo y unidad académica
- **📝 CursoSeccion**: Secciones específicas de cursos
- **⏰ BloqueHorario**: Franjas horarias con día y horas
- **📊 AsignacionHorario**: Entidad principal que conecta todo (Planning Entity)

### Restricciones Implementadas

#### Restricciones Duras (Hard Constraints)
- Un aula no puede estar ocupada en el mismo bloque horario
- Un docente no puede estar en dos lugares al mismo tiempo
- Compatibilidad entre tipo de aula y tipo de curso
- Disponibilidad de docentes según sus restricciones

#### Restricciones Blandas (Soft Constraints)
- Balanceo de horas asignadas vs. horas contratadas del docente
- Optimización del uso de recursos disponibles

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4.4**
- **OptaPlanner 9.44.0** - Motor de optimización
- **Spring Data JPA** - Persistencia de datos
- **SQL Server** - Base de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias

## 📁 Estructura del Proyecto

```
src/
├── main/java/elp/edu/pe/horario/
│   ├── application/
│   │   ├── dto/              # DTOs para transferencia de datos
│   │   ├── mapper/           # Mappers para conversión de entidades
│   │   └── usecase/          # Casos de uso de la aplicación
│   ├── domain/
│   │   ├── enums/            # Enumeraciones del dominio
│   │   ├── model/            # Entidades del dominio
│   │   ├── repository/       # Interfaces de repositorio
│   │   └── solver/           # Configuración de OptaPlanner
│   ├── infrastructure/
│   │   └── persistence/      # Implementación de persistencia
│   └── shared/
│       ├── config/           # Configuraciones
│       └── exception/        # Manejo de excepciones
```

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- SQL Server
- IDE compatible con Java (IntelliJ IDEA, Eclipse, VS Code)

### Pasos de Instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/devbryan02/asignacion-de-horario.git
   cd asignacion-de-horario
   ```

2. **Configura la base de datos**
   ```properties
   # En application.properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=horarios_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_password
   ```

3. **Instala las dependencias**
   ```bash
   mvn clean install
   ```

4. **Ejecuta la aplicación**
   ```bash
   mvn spring-boot:run
   ```

## 📊 Uso del Sistema

### Generar Horarios Optimizados

El sistema utiliza OptaPlanner para generar horarios optimizados automáticamente:

```java
// El solver procesa las entidades y genera la mejor solución posible
HorarioSolucion solucion = horarioSolucionBuilder.construir(periodo);
GeneracionHorarioResponse response = horarioSolverUseCase.ejecutar(solucion);
```

### Principales Endpoints

- `POST /api/horarios/generar` - Genera un nuevo horario optimizado
- `GET /api/horarios/{periodoId}` - Obtiene horarios por período
- `GET /api/horarios/analisis` - Analiza la calidad de la solución

## ⚙️ Configuración de OptaPlanner

El sistema está configurado con:

- **Tiempo límite**: 30 segundos de optimización
- **Fases de construcción**: Construcción heurística inicial
- **Búsqueda local**: Optimización iterativa
- **Puntuación**: Sistema Hard/Soft Score

```java
@Bean
public SolverConfig solverConfig(){
    return new SolverConfig()
            .withSolutionClass(HorarioSolucion.class)
            .withEntityClasses(AsignacionHorario.class)
            .withConstraintProviderClass(HorarioConstraintProvider.class)
            .withTerminationSpentLimit(Duration.ofSeconds(30));
}
```

## 🧪 Testing

Ejecuta las pruebas unitarias:

```bash
mvn test
```

## 📈 Métricas y Análisis

El sistema proporciona métricas detalladas sobre la calidad de la solución:

- Cantidad de asignaciones realizadas
- Número de aulas utilizadas
- Bloques horarios ocupados
- Docentes asignados
- Puntuación Hard/Soft Score

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añade nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Bryan** - [@devbryan02](https://github.com/devbryan02)

## 🔗 Enlaces

- [Repositorio del Proyecto](https://github.com/devbryan02/asignacion-de-horario)
- [OptaPlanner Documentation](https://docs.optaplanner.org/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

⭐ ¡Si te ha gustado este proyecto, no olvides darle una estrella!