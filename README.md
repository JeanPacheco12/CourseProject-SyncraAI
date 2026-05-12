# SyncraEstate AI 

Plataforma integral (Web & Mobile) con integración de IA diseñada para optimizar el flujo de trabajo de los agentes inmobiliarios. 

## Fase 1: Proposal
En esta etapa inicial, se definieron los objetivos, alcances y el problema a resolver. Se conceptualizó **Syncra Estate AI** como un ecosistema dual (Web para gerentes y Mobile para agentes en campo) enfocado en la gestión de clientes (CRM) y propiedades. Se estableció como meta principal la implementación de Inteligencia Artificial para facilitar el acercamiento de ventas.

## Fase 2: Mock-up (UI/UX Design)
Fase dedicada íntegramente al diseño de experiencia y de interfaz de usuario (sin código).
* **Herramientas:** Figma.
* **Entregables:** Creación de prototipos de alta fidelidad, flujos de navegación, definición de paleta de colores y componentes visuales.
* **Enfoque:** Diseño orientado a la usabilidad de agentes inmobiliarios en movimiento (Mobile) y visualización de datos en oficina (Web).

## Fase 3: Static Version (UI Mockups)
En esta fase del proyecto, hemos desarrollado las interfaces estáticas y responsivas para ambas plataformas, basándonos fielmente en nuestros diseños de Figma. No se han integrado datos dinámicos ni backend en esta etapa.

### Plataforma Móvil (Android)
* **Tecnologías:** Kotlin, Jetpack Compose, Material Design 3.
* **Arquitectura:** Estructura inicial basada en Clean Architecture.
* **Vistas Implementadas:** Login, Dashboard de Propiedades y Detalles de Propiedad.
* **Características UI:** Íconos vectoriales personalizados, scroll dinámico, adaptabilidad de pantalla y validaciones visuales de permisos de usuario.

### Plataforma Web
* **Tecnologías:** Next.js, React, Tailwind CSS / UI Framework.
* **Arquitectura:** Component-based architecture.
* **Vistas Implementadas:** Pantallas de Login y Management/Dashboard.

## Fase 4: Functional Integration
Transición de la aplicación de un prototipo estático a un ecosistema conectado y dinámico. Se integró la lógica de negocios, conexión a la nube y el procesamiento de lenguaje natural.

* **Autenticación (Firebase Auth):** Sistema de login seguro y unificado para Web y Mobile.
* **Base de Datos (Cloud Firestore):** Almacenamiento NoSQL para la sincronización en tiempo real de perfiles de clientes (Leads) y detalles de propiedades.
* **Integración de IA (Gemini API):** Implementación del modelo **Gemini 2.5 Flash Lite** para la funcionalidad estrella *"Smart Pitch"*, capaz de generar mensajes de venta persuasivos leyendo los datos del prospecto desde la base de datos.
* **Nuevas Funciones Operacionales:** Búsqueda multimodal (voz y texto), galería de imágenes dinámica, edición de propiedades (CRUD), traducción automática y configuración de parámetros para la IA (tono y longitud).

## Fase 5: Progress Report (Dynamic Refinement)
Fase centrada en la optimización de la persistencia de datos y el pulido de la experiencia de usuario (UX) basada en retroalimentación técnica.

* **Optimización de Consultas:** Refactorización de llamadas a Firestore para mejorar la velocidad de carga de listas masivas de propiedades y clientes.
* **Dashboard Dinámico:** En Web, se implementó la lógica de métricas reales (Ventas del mes, Tasa de conversión) consumiendo datos directamente de la base de datos en lugar de valores estáticos.
* **Refactorización de UI:** Ajustes en los temas de Material Design 3 (Mobile) y Glassmorphism (Web) para mejorar el contraste y la legibilidad.

## Fase 6: Pre-delivery (Beta)
Versión estable del producto con funcionalidad superior al 75%. El objetivo fue asegurar la robustez antes de la entrega final.

* **IA Fully Operational:** Estabilización de la integración con **Gemini 2.5 Flash Lite**, mejorando el manejo de errores y la velocidad de respuesta del "Smart Pitch".
* **Consistencia Cross-Platform:** Sincronización bidireccional perfecta. Los cambios realizados en la Web (CRUD de propiedades) impactan instantáneamente en la aplicación móvil.
* **State Management:** Implementación avanzada de estados en Kotlin (**StateFlow**) y React (**Context/State**) para manejar cargas, errores y estados vacíos.
* **Beta Testing:** Pruebas de flujo completo de usuario: Login -> Gestión de Propiedad -> Generación de Estrategia de IA -> Seguimiento de Lead.

## Fase 7: Final Project (Production Ready)
Entrega definitiva del ecosistema **SyncraEstate AI**, cumpliendo con el 100% de los requisitos y funcionalidades adicionales.

* **Ecosistema Completo:** Integración total de Login seguro, Base de datos NoSQL, Cloud Storage y motor de IA.
* **5 Funcionalidades Adicionales Implementadas:**
    1. **CRUD Completo:** Gestión administrativa total de inventario y leads.
    2. **Búsqueda Multimodal:** Filtrado dinámico de propiedades mediante texto y reconocimiento de voz (**Speech-to-Text**).
    3. **Galería Dinámica & Cloud Storage:** Carga y visualización de imágenes de alta resolución desde URLs dinámicas sincronizadas.
    4. **Traducción y Localización:** Capacidad de la IA para generar contenido en múltiples idiomas según el perfil del cliente.
    5. **Dashboard de Analítica:** Visualización de rendimiento de ventas y leads con gráficos dinámicos sincronizados.
* **Calidad de Software:** Código limpio, documentado y estructurado bajo arquitectura **MVVM** (Mobile) y **Component-Based** (Web), garantizando escalabilidad.

### Stack Tecnológico Final

| Módulo | Tecnología / Herramienta | Propósito en el Proyecto |
| :--- | :--- | :--- |
| **Frontend Web** | Next.js 14 & Tailwind CSS | Panel administrativo, métricas y dashboard responsivo. |
| **Mobile App** | Kotlin & Jetpack Compose | Aplicación nativa con UI moderna y reactiva para agentes. |
| **Base de Datos** | Cloud Firestore (NoSQL) | Sincronización de propiedades y clientes en tiempo real. |
| **Autenticación** | Firebase Auth | Sistema de acceso seguro y unificado para ambas plataformas. |
| **IA Engine** | Google Gemini 2.5 Flash Lite | Generación multimodal de estrategias de venta (Smart Pitch). |
| **Arquitectura** | MVVM & Component-Based | Separación de lógica y vista para facilitar el mantenimiento. |
| **Voz a Texto** | Google Speech Recognition | Implementación de búsqueda por voz para manos libres. |

---

## Estructura del Repositorio
* `/mobile`: Código fuente de la aplicación nativa en Android.
* `/web`: Código fuente de la aplicación web en Next.js.
* `/docs`: Documentación en PDF, diagramas de arquitectura y flujo correspondientes a cada fase.

## Equipo
* Jean Pacheco - 23005759
* Rodrigo Gálvez - 24001330

## Links de videos explicativos
*(Nota: Según los requerimientos del curso, las Fases 1 y 2 fueron de conceptualización y diseño, por lo que las demostraciones en video inician a partir de la Fase 3, donde se involucra el desarrollo de código).*

### 📹 Fase 3 (Static UI)
* **Web:** https://youtu.be/ZG8bMa_ULys
* **Mobile:** https://youtu.be/Uef9xrkUuDI

### 📹 Fase 4 (Functional Integration)
* **Web:** https://youtu.be/7NwZa-huAxE?si=Ywem2w2ZM7Ppknng
* **Mobile:** https://youtu.be/kPmU4j2I95Q

### 📹 Fase 5 (Progress Report)
* **Web:** https://youtu.be/woswRWjAKlE?si=ZMD_yC2lLPiYPbzo
* **Mobile:** https://youtu.be/YRLINZJYda8

### 📹 Fase 6 (Pre-delivery (Beta))
* **Web:** 
* **Mobile:** https://youtu.be/TvjWvp1g7uQ

### 📹 Fase 7 (Final Project)
* **Web:** 
* **Mobile:** https://youtu.be/5r4LPNWrPEg
