README — Syncra Estate AI (Web)
Descripción

Aplicación web desarrollada con Next.js + TypeScript + Tailwind CSS, conectada a Firebase (Auth + Firestore) para la gestión de propiedades y clientes.

Permite:

  CRUD de propiedades
  CRUD de clientes
  Relación cliente ↔ propiedad
  Dashboard con métricas en tiempo real

Tecnologías
  Next.js
  TypeScript (TSX)
  Tailwind CSS
  Firebase Auth
  Cloud Firestore

Arquitectura

Usuario
   |
   v
Web App (Next.js)
   |-------- Firebase Auth
   |
   |-------- Firestore
                 |
        ---------------------
        |        |          |
   Propiedades  Clientes  Relaciones

Autenticación
  Usuario --- login/register ---> Web
  Web --- request ---> Firebase Auth
  Firebase --- token ---> Web
  Web --- acceso ---> Dashboard

Módulo Propiedades

Funciones:

  Crear
  Editar
  Eliminar
  Listar

Estructura:

Property
 ├─ id
 ├─ title
 ├─ type
 ├─ location
 ├─ price
 ├─ status
 └─ imageGalleryUrlList[]
 
Módulo Clientes

Funciones:
  
  Crear
  Editar
  Eliminar
  Asociar propiedad

Estructura:

Client
 ├─ id
 ├─ nombre
 ├─ telefono
 ├─ email
 ├─ interest
 └─ propertyId
 
Relación Cliente ↔ Propiedad
  Cliente --- selecciona ---> Propiedad
  Cliente --- guarda ---> propertyId
  Firestore --- actualiza ---> datos
  Dashboard --- muestra ---> interesados

Dashboard

Muestra:

  Total propiedades
  Ventas del mes
  Total interesados
  Estado de propiedades (Disponible / Vendido / Reservado)
  
Propiedades --- estado (status) ---> Firestore
Firestore --- datos ---> Web
Web --- agrupa por estado ---> métricas
Web --- render ---> Dashboard

El estado de cada propiedad impacta directamente las gráficas y conteos del dashboard.

Flujo General
Usuario
  |
  v
Login
  |
  v
Dashboard
  |--------- Propiedades (CRUD)
  |                |
  |                v
  |        Guardar estado (status)
  |
  |--------- Clientes (CRUD)
                     |
                     v
              Asignar propiedad
                     |
                     v
                 Firestore
                     |
                     v
                 Dashboard (métricas actualizadas)
                 
Estructura de Páginas
  Imports
  Tipos de datos
  Lógica
  Render (UI)
  
Decisiones Técnicas
  No se usa Firebase Storage → imágenes como URLs
  Firestore para datos en tiempo real
  Tailwind para desarrollo rápido
  TypeScript para control de errores
  
Estado Actual
  UI completa
  CRUD funcional
  Integración con Firebase
  Dashboard dinámico basado en datos reales
