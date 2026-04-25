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
* **Web:** [Inserta el link de YouTube aquí]
* **Mobile:** https://youtu.be/kPmU4j2I95Q
