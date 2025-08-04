# UalaChallenge

Aplicación desarrollada como parte del challenge técnico para la posición Android en Ualá.  
Permite explorar, buscar y marcar como favoritas diferentes ciudades del mundo utilizando una API remota y persistencia local.

---

## 🧩 Tecnologías y herramientas

- **Kotlin**
- **Jetpack Compose**
- **Architecture Components** (ViewModel, StateFlow)
- **Room** (persistencia local)
- **Hilt** (inyección de dependencias)
- **Paging 3** (paginación eficiente)
- **Google Maps Compose**
- **Retrofit** (API REST)
- **Coroutines / Flow**
- **JUnit + MockK** (tests unitarios)
- **Compose UI Testing** (tests de interfaz)

---

## 🧠 Arquitectura

El proyecto sigue una arquitectura limpia basada en MVVM:

UI (Compose)
│
├── ViewModel (maneja estado y lógica de UI)
│
├── UseCases (orquestan casos de uso)
│
├── Repository (coordina DataSources)
│
├── LocalDataSource (Room)
└── RemoteDataSource (Retrofit)

---

🛠️ Decisiones técnicas
•	🧩 Single Activity Architecture:
Toda la navegación está implementada en una sola Activity utilizando Jetpack Navigation Compose.
•	🧱 100% Jetpack Compose:
La interfaz de usuario está construida completamente con Jetpack Compose, incluyendo el layout, navegación, top bars, inputs y previews.
•	🧠 State management con StateFlow y Paging:
Se utiliza StateFlow para manejar estado de UI, y Paging con LazyPagingItems para listas eficientes y reactivas.
•	✅ UI adaptativa y desacoplada:
La UI se organiza en componentes reutilizables, testeables y desacoplados del estado (por ejemplo, CityList, CityItem, CityMap).

---

## ✨ Funcionalidades implementadas

- ✅ Descarga inicial de ciudades desde API remota
- ✅ Almacenamiento local de datos en Room
- ✅ Pantalla principal con lista paginada de ciudades
- ✅ Búsqueda interactiva por nombre
- ✅ Posibilidad de marcar/desmarcar ciudades como favoritas
- ✅ Mapa con ubicación de la ciudad seleccionada
- ✅ Manejo de errores de red y base local
- ✅ Diseño adaptativo: lista + mapa en landscape
- ✅ Tests unitarios de ViewModel y UseCases
- ✅ Tests de UI con Compose Testing

---

## 📁 Organización del código

•	data/
    ├── local/
    │   ├── model/            → Entidades Room (CityEntity)
    │   ├── CityDao.kt
    ├   └── CityLocalDataSource.kt
    ├── remote/
    │   ├── model/            → CityDTO
    │   ├── CityApi.kt
    │   └── CityRemoteDataSource.kt
•	domain/
    ├── model/                → Modelos de negocio (City)
    ├── repository/           → Interfaces (ej: CityRepository)
    ├── usecase/              → Casos de uso (GetCitiesUseCase, etc.)
    ├── util/                 → Result
•	ui/
    ├── splash/               → SplashScreen, SplashViewModel
    ├── list/                 → ListScreen, CityList, CityItem
    └── map/                  → MapScreen, MapViewModel
•	di/                       → Módulos de Hilt
