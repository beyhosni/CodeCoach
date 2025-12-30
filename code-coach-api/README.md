# Code Coach - Backend API

🎓 **Plateforme d'apprentissage du code par compréhension (Socratic Coaching)**

## 📊 Statut du Projet

**MVP - STEP 1** : Architecture & modules fondamentaux ✅

- [x] Structure Maven multi-modules
- [x] Entités JPA et migrations Flyway
- [x] Authentification JWT
- [x] CRUD utilisateurs
- [x] CRUD exercices (read-only pour apprenants)
- [x] Soumission de code (stub sans exécution réelle)
- [x] Services & contrôleurs de base
- [ ] Runner Docker (STEP 2)
- [ ] Coach IA socratique (STEP 3)
- [ ] WebSocket live feedback (STEP 4)

---

## 🏗️ Architecture

### Modules

```
code-coach-api/
├── shared-module/          DTOs, exceptions, configs communes
├── auth-module/            JWT, authentification, security
├── user-module/            Gestion utilisateurs, CRUD
├── content-module/         Tracks, exercices, curriculum
├── submission-module/      Soumission code, versioning
├── runner-module/          Orchestration exécution sécurisée
├── coach-module/           IA socratique, hints, questions
├── progress-module/        Tracking progression, statistiques
└── api-module/             Application Spring Boot principale
```

### Dépendances entre modules

```
api-module
├── auth-module
├── user-module
├── content-module
├── submission-module
├── runner-module
├── coach-module
└── progress-module

Chaque module dépend de : shared-module
```

### Relation de données

```
User (1)
├── Submissions (N)
│   ├── SubmissionResult (1)
│   └── CoachConversation (1)
│       └── CoachMessages (N)
├── SkillProgress (N)

Track (1)
└── Exercise (N)
```

---

## 🔐 Authentification

### JWT Token

**Header:**
```
Authorization: Bearer <token>
```

**Claims:**
- `sub` : username
- `role` : LEARNER | INSTRUCTOR | ADMIN
- `iat` : issued at
- `exp` : expiration (24h par défaut)

### Algorithme
- HMAC-SHA256
- Clé : 256+ bits (configurable)
- Rotation : TODO (refresh tokens)

### Endpoints d'authentification

```bash
# Enregistrement
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "username": "john_doe",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}

Response:
{
  "token": "eyJhbGc...",
  "username": "john_doe",
  "role": "LEARNER",
  "userId": 1
}
```

```bash
# Connexion
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}

Response:
{
  "token": "eyJhbGc...",
  "username": "john_doe",
  "role": "LEARNER",
  "userId": 1
}
```

---

## 📚 API Endpoints

### Exercices (GET - lectures seules pour apprenants)

```bash
# Lister les exercices d'une track
GET /api/v1/exercises/track/{trackId}
Authorization: Bearer <token>

Response:
[
  {
    "id": 1,
    "trackId": 10,
    "title": "Écrire une méthode isEven",
    "description": "Implémenter isEven(int n) qui retourne true si n est pair",
    "starterCode": "public class Solution { ... }",
    "difficulty": 1,
    "createdAt": "2025-01-15T10:30:00Z"
  }
]

# Récupérer les détails d'un exercice
GET /api/v1/exercises/{exerciseId}/details
Authorization: Bearer <token>

Response: (même format que ci-dessus)
```

**Note importante :** Le `test_code` n'est **jamais** exposé à l'apprenant !

### Soumissions

```bash
# Soumettre du code
POST /api/v1/submissions/{exerciseId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "code": "public class Solution { ... }"
}

Response:
{
  "id": 100,
  "exerciseId": 1,
  "userId": 1,
  "code": "public class Solution { ... }",
  "status": "PENDING",
  "attemptNumber": 1,
  "createdAt": "2025-01-15T10:35:00Z"
}

# Récupérer l'historique
GET /api/v1/submissions
Authorization: Bearer <token>

Response: [ { ... }, { ... } ]

# Détails d'une soumission
GET /api/v1/submissions/{submissionId}
Authorization: Bearer <token>

Response: (même format que ci-dessus)
```

---

## 📦 Stack technique

| Composant | Technologie | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.2.1 |
| **Language** | Java | 17 |
| **Database** | PostgreSQL | 15+ |
| **Migration** | Flyway | 9.x |
| **Security** | Spring Security + JWT | - |
| **Cache** | Redis | 6+ |
| **Build** | Maven | 3.8+ |
| **Async** | Kafka (stub) | - |
| **Monitoring** | Micrometer | - |

---

## 🚀 Configuration & Démarrage

### Prérequis

- Java 17+
- Maven 3.8+
- PostgreSQL 15+
- Redis 6+ (optional pour MVP)

### Configuration

**application.yml** (à personnaliser)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/codecoach
    username: codecoach
    password: codecoach123
  
  jpa:
    hibernate:
      ddl-auto: validate
  
  redis:
    host: localhost
    port: 6379

server:
  port: 8080
  servlet:
    context-path: /api/v1

jwt:
  secret: "your-256-bit-secret-key"
  expiration: 86400000 # 24h
```

### Build & Exécution

```bash
# Build du projet parent
cd code-coach-api
mvn clean package

# Exécution
cd api-module
mvn spring-boot:run

# Ou JAR directement
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar
```

**API disponible à :** `http://localhost:8080/api/v1`

---

## 🧪 Tests

### Exemple avec curl

```bash
# 1. Enregistrement
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "username": "alice",
    "password": "Password123!",
    "firstName": "Alice",
    "lastName": "Wonderland"
  }'

# Réponse
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "alice",
  "role": "LEARNER",
  "userId": 1
}

# 2. Récupérer des exercices
TOKEN="eyJhbGciOiJIUzI1NiJ9..."
curl -X GET http://localhost:8080/api/v1/exercises/track/1 \
  -H "Authorization: Bearer $TOKEN"

# 3. Soumettre du code
curl -X POST http://localhost:8080/api/v1/submissions/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Solution { public static boolean isEven(int n) { return n % 2 == 0; } }"
  }'
```

---

## 🎓 Principes pédagogiques

### ❌ JAMAIS

- Générer une solution complète
- Donner directement la réponse
- Faire de l'autocomplete déguisé

### ✅ TOUJOURS

- Poser des questions socratiques
- Donner des hints graduels (conceptuel → pseudo-code)
- Expliquer les erreurs sans donner la solution
- Tracker la progression pour motiver sans illusionner

### Coach IA (Levels)

1. **QUESTION** : "Qu'est-ce qu'une variable ?"
2. **HINT** : "Pense au type de données entier..."
3. **PSEUDO_CODE** : "if (condition) { // faire quelque chose }"
4. **EXPLANATION** : "L'erreur vient de la logique de condition..."

---

## 📋 TODO - STEP 2 (Runner Docker)

- [ ] Créer image Docker pour exécution sécurisée Java
- [ ] Implémenter RunnerService pour orchestration
- [ ] Queue Kafka pour submissions async
- [ ] Tests d'exécution isolés (timeout, mémoire, network disabled)
- [ ] Persister SubmissionResult après exécution
- [ ] WebSocket pour feedback live

---

## 📋 TODO - STEP 3 (Coach IA)

- [ ] Implémenter CoachService (analyse d'erreurs)
- [ ] Templates de questions socratiques
- [ ] NLP pour classification d'erreurs
- [ ] Conversation stateful avec Redis
- [ ] ChatGPT/Claude integration (optionnel)

---

## 📋 TODO - STEP 4 (Frontend & Intégration)

- [ ] Editor de code (Monaco Editor / VS Code Embed)
- [ ] WebSocket pour feedback temps réel
- [ ] Tableau de bord progression
- [ ] Gamification (badges, streaks)
- [ ] Responsive design

---

## 🔒 Sécurité (MVP)

| Aspect | Implémentation | Status |
|--------|---------------|---------| 
| **Authentification** | JWT HMAC-SHA256 | ✅ |
| **Hachage mots de passe** | BCrypt 12 rounds | ✅ |
| **Validation input** | @Valid Jakarta | ✅ |
| **Isolation exécution** | Docker container (STEP 2) | ⏳ |
| **Rate limiting** | Redis (TODO) | ⏳ |
| **CORS** | À configurer selon frontend | ⏳ |
| **HTTPS** | À activer en production | ⏳ |
| **Secrets** | Environment variables | ⏳ |

---

## 📊 Schéma DB

**8 tables principales :**

1. `cc_user` - Utilisateurs
2. `cc_track` - Pistes d'apprentissage
3. `cc_exercise` - Exercices
4. `cc_submission` - Soumissions code
5. `cc_submission_result` - Résultats exécution
6. `cc_coach_conversation` - Conversations coaching
7. `cc_coach_message` - Messages individuels
8. `cc_skill_progress` - Progression par skill

Voir fichiers migration Flyway : `db/migration/V*.sql`

---

## 🎯 Décisions architecturales

### Modular Monolith vs Microservices

**Choix :** Commencer par **Modular Monolith**, facilement extractible en microservices.

**Raison :** 
- Simplicité du MVP
- Déploiement unique
- Facile à refactoriser après
- Chaque module = future service

### JWT sans session

**Choix :** JWT stateless uniquement (pas de RefreshToken pour MVP).

**Raison :**
- Scalabilité horizontale
- Pas de session store
- Token expiration à 24h

**Future :** Ajouter refresh tokens avec Redis.

### Tests d'acceptation dans DB

**Choix :** Stocker `test_code` dans Exercise, jamais exposer à l'apprenant.

**Raison :**
- Immuabilité
- Audit trail
- Runner peut accéder facilement

---

## 📝 Conventions de code

- **Packages :** `com.codecoach.{module}.{layer}` (entity, service, controller, etc.)
- **Naming :** camelCase java, snake_case SQL
- **DTOs :** Suffix `Dto`, jamais d'entités en API
- **Logging :** SLF4J + Lombok `@Slf4j`
- **Transactions :** `@Transactional` sur services, `readOnly = true` si possible

---

## 🤝 Contribution

Chaque modification doit respecter :

1. ✅ Pas de solution brute pour apprenants
2. ✅ Tests unitaires minimum
3. ✅ Documentation des choix
4. ✅ Logging adéquat pour debug
5. ✅ Validation input stricte

---

## 📄 License

TBD

---

## 📞 Contact

Architecture : Senior Software Architect & Lead Backend Engineer
Tech Stack : Java 17, Spring Boot 3, PostgreSQL, Redis, Kafka

---

**Last updated:** 30 Jan 2025 | **Version:** 0.1.0-SNAPSHOT
