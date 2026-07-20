# Cloud Library Manager

Application de gestion d'une bibliothèque universitaire (livres, étudiants, emprunts) développée avec **Spring Boot** et **Spring Data JPA**, conteneurisée avec **Docker** et déployée sur le **Cloud**.

## Technologies utilisées
- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation)
- Hibernate / JPA
- H2 (base en mémoire, profil `dev`) et PostgreSQL (profil `docker`)
- springdoc-openapi (Swagger UI)
- Lombok
- Docker / Docker Compose
- Maven

## Architecture du projet
```
src/main/java/com/university/library/
 ├── entity/         # Entités JPA : Book, Student, Loan
 ├── repository/     # Interfaces Spring Data JPA (requêtes dérivées + JPQL)
 ├── service/        # Logique métier (BookService, StudentService, LoanService)
 ├── controller/      # Contrôleurs REST (API /api/books, /api/students, /api/loans)
 ├── exception/       # ResourceNotFoundException, BadRequestException, GlobalExceptionHandler
 └── config/          # OpenApiConfig, DataInitializer (données de démo)
```

Architecture en couches classique : **Controller → Service → Repository → Entity**, avec gestion centralisée des exceptions via `@RestControllerAdvice`.

## Modèle de données
- Un **Student** possède plusieurs **Loan** (`@OneToMany`)
- Un **Book** peut être lié à plusieurs **Loan** au cours de son cycle de vie (`@OneToMany`)
- Un **Loan** concerne un seul **Book** et un seul **Student** (`@ManyToOne`)

Voir [docs/uml_diagram.md](docs/uml_diagram.md) pour le diagramme UML complet et [docs/schema.sql](docs/schema.sql) pour le script SQL.

## Installation et exécution

### Option 1 — En local (sans Docker), profil H2
Prérequis : Java 17+, Maven.
```bash
mvn clean package -DskipTests
mvn spring-boot:run
```
L'application démarre sur `http://localhost:8080` avec une base H2 en mémoire pré-remplie de données de démonstration.

### Option 2 — Avec Docker Compose (PostgreSQL)
Prérequis : Docker.
```bash
docker compose up --build
```
L'application est accessible sur `http://localhost:8080`, connectée à une base PostgreSQL.

### Documentation Swagger / OpenAPI
Une fois l'application démarrée :
- Swagger UI : `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON : `http://localhost:8080/v3/api-docs`

### Console H2 (profil dev uniquement)
`http://localhost:8080/h2-console` (JDBC URL : `jdbc:h2:mem:librarydb`, user : `sa`, pas de mot de passe)

## Endpoints principaux de l'API

| Ressource | Méthode | URL | Description |
|---|---|---|---|
| Books | GET | `/api/books` | Liste tous les livres |
| Books | GET | `/api/books/{id}` | Détail d'un livre |
| Books | POST | `/api/books` | Ajouter un livre |
| Books | PUT | `/api/books/{id}` | Modifier un livre |
| Books | DELETE | `/api/books/{id}` | Supprimer un livre |
| Books | GET | `/api/books/search/title?title=...` | Recherche par titre |
| Books | GET | `/api/books/search/author?author=...` | Recherche par auteur |
| Books | GET | `/api/books/available` | Livres disponibles |
| Students | GET/POST/PUT/DELETE | `/api/students`, `/api/students/{id}` | CRUD étudiants |
| Loans | POST | `/api/loans` | Enregistrer un emprunt (`{"bookId":1,"studentId":1}`) |
| Loans | PUT | `/api/loans/{id}/return` | Retourner un livre |
| Loans | DELETE | `/api/loans/{id}` | Supprimer un emprunt |
| Loans | GET | `/api/loans/current` | Emprunts en cours |
| Loans | GET | `/api/loans/student/{studentId}` | Historique d'un étudiant |
| Loans | GET | `/api/loans/count/active` | Nombre de livres actuellement empruntés |
| Loans | GET | `/api/loans/count/total` | Nombre total d'emprunts |

Une collection Postman complète est disponible dans [docs/postman_collection.json](docs/postman_collection.json).

## Déploiement Cloud
- **URL publique de l'application** : https://cloud-library-manager.onrender.com
- **URL Swagger** : https://cloud-library-manager.onrender.com/swagger-ui.html
- **URL OpenAPI (JSON)** : https://cloud-library-manager.onrender.com/v3/api-docs

Déploiement réalisé sur [Render.com](https://render.com) via le `Dockerfile` fourni (build automatique à chaque push sur GitHub).

⚠️ L'application est hébergée sur le plan gratuit de Render : elle se met en veille après une période d'inactivité. Le premier appel peut prendre 30 à 50 secondes le temps que le service redémarre.

## Gestion des erreurs et validation
- Validation des données avec les annotations `jakarta.validation` (`@NotBlank`, `@NotNull`, `@Email`, `@Positive`) sur les entités.
- Gestion centralisée des exceptions via `GlobalExceptionHandler` (`@RestControllerAdvice`) : 404 pour ressource introuvable, 400 pour requête invalide ou erreurs de validation, 500 pour erreurs inattendues.

## Auteur
Projet individuel — Adam, EFREI Paris.
