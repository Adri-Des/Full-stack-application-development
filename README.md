# Full-stack Application Development

Ce projet est une application web complète comprenant un backend Java Spring Boot et un frontend Angular.  

---

## Structure du projet

- **/back** : Backend Java (Spring Boot)
- **/front** : Frontend Angular

---

## 1. Backend (Spring Boot)

### Prérequis

- Java 11+
- Maven

### Installation

```bash
cd back
mvn install
```

### Lancement

```bash
mvn spring-boot:run
```

Le backend sera accessible par défaut sur [http://localhost:8080](http://localhost:8080).

### Documentation
- Configuration base de données dans `back/src/main/resources/application.properties`
- Génération de la documentation Swagger :  
  Accédez à [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) pour interagir avec l'API.
- Accès au Javadoc dans `back/doc`

---

## 2. Frontend (Angular)

### Prérequis

- Node.js
- npm

### Installation

```bash
cd front
npm install
```

### Lancement en développement

```bash
ng serve
```

L'application sera accessible sur [http://localhost:4200](http://localhost:4200).

### Build

```bash
ng build
```
