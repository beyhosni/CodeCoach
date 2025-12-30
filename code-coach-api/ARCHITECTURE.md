# 🏗️ ARCHITECTURE - Code Coach Backend

## Vue d'ensemble

Code Coach est une **plateforme pédagogique** construite comme un **Modular Monolith** permettant une extraction facile en microservices.

**Objectif principal :** Apprendre à coder en forçant la compréhension, pas seulement produire du code.

---

## 1. Stratégie modulaire

### 1.1 Modules et responsabilités

```
CodeCoach (Monolith Spring Boot)
│
├── shared-module
│   └── DTOs, Exceptions, Constants, Utilitaires transversaux
│   └── **Pas d'entités**, **Pas de logique métier**
│
├── auth-module
│   ├── JWT (génération, validation)
│   ├── Spring Security Configuration
│   ├── Authentification (login, register)
│   └── **Pas d'accès direct aux données User**
│
├── user-module
│   ├── Entity User
│   ├── UserRepository (JPA)
│   ├── UserService (CRUD + validation)
│   └── **Responsable password hashing + validation**
│
├── content-module
│   ├── Entity Track
│   ├── Entity Exercise
│   ├── Repository (JPA)
│   ├── ExerciseService (lecture curriculum)
│   └── **Expose JAMAIS le test_code à l'apprenant**
│
├── submission-module
│   ├── Entity Submission
│   ├── Entity SubmissionResult
│   ├── SubmissionRepository (JPA)
│   ├── SubmissionService
│   └── **Statut: PENDING → Envoyer vers Runner**
│
├── runner-module
│   ├── RunnerService (interface abstraite)
│   ├── ExecutionConfig (timeout, mémoire)
│   └── **Placeholder pour Docker execution (STEP 2)**
│
├── coach-module
│   ├── Entity CoachConversation
│   ├── Entity CoachMessage
│   ├── CoachService (IA socratique)
│   └── **Placeholder pour NLP (STEP 3)**
│
├── progress-module
│   ├── Entity SkillProgress
│   ├── ProgressService (gamification)
│   └── **Redis cache pour stats temps réel**
│
└── api-module
    ├── CodeCoachApplication (classe main)
    ├── Controllers (orchestration)
    ├── Flyway migrations
    ├── application.yml
    └── **Point d'entrée unique**
```

### 1.2 Isolation des modules

**Règle :** Un module ne peut dépendre d'un autre que via `shared-module`.

```
✅ AUTORISÉ :
- submission-module peut utiliser ExerciseDto (via shared-module)
- auth-module peut utiliser AuthResponseDto (via shared-module)

❌ INTERDIT :
- submission-module NE dépend PAS de content-module directement
  → Passer par l'API REST au lieu de dépendre du service
- runner-module NE a pas de référence à UserService
  → Les dépendances vont vers les couches "intérieures"
```

**Motivation :** Faciliter l'extraction en microservices.

---

## 2. Flux de données

### 2.1 Authentification

```
Client
  ↓
POST /auth/register (UserRegisterDto)
  ↓
AuthController
  ↓
UserService.registerUser()
  ├─→ Valider email/username (UserRepository)
  ├─→ Hasher password (BCrypt)
  └─→ Sauver User
  ↓
JwtTokenProvider.generateToken()
  ↓
AuthResponseDto (token + user info)
  ↓
Client stocke le token
```

### 2.2 Soumission de code

```
Client (avec JWT)
  ↓
POST /submissions/{exerciseId}
  ├─ Header: Authorization: Bearer <token>
  ├─ Body: { code: "..." }
  ↓
SubmissionController
  ├─→ Extraire userId du JWT
  ├─→ Valider exerciseId existe
  ↓
SubmissionService.submitCode()
  ├─→ Compter tentatives précédentes
  ├─→ Créer Submission(status=PENDING)
  ├─→ Persister en DB
  ↓
RunnerService.executeAsync() [TODO STEP 2]
  ├─→ Envoyer vers Kafka Queue
  ├─→ Docker container l'exécute
  ├─→ Créer SubmissionResult
  ↓
CoachService.analyzeAndRespond() [TODO STEP 3]
  ├─→ Parser erreurs (compilation/runtime/tests)
  ├─→ Générer question socratique
  ├─→ Créer CoachConversation
  ↓
WebSocket message au client [TODO STEP 4]
  └─→ Feedback temps réel (résultat + hint)
```

### 2.3 Progression utilisateur

```
SubmissionResult (success)
  ↓
ProgressService.recordSuccess()
  ├─→ Incrémenter SkillProgress.successfulAttempts
  ├─→ Check if mastered (2 succès consécutifs)
  ├─→ Mettre en cache Redis
  ↓
ProgressController.getStats()
  ├─→ Récupérer du cache Redis
  ├─→ Calculer pourcentage d'exercices maîtrisés
  ↓
Gamification (badges, streaks) [TODO]
```

---

## 3. Architecture de sécurité

### 3.1 Authentification (JWT)

```
Registration
  1. Email + Password reçus (HTTPS obligatoire en prod)
  2. Validation: email unique, password >= 8 chars
  3. BCrypt hash (12 rounds) → stocké en DB
  4. JWT généré (HMAC-SHA256)
  5. Token retourné au client

Requête authentifiée
  1. Header: Authorization: Bearer <token>
  2. JwtAuthenticationFilter extrait le token
  3. Valide signature + expiration
  4. Extrait username + role
  5. Ajoute au SecurityContext
  6. Contrôleur accès par rôle (@PreAuthorize("hasRole('LEARNER')"))
```

**Configuration de sécurité :**
- Sessions désactivées (stateless)
- CSRF disabled (JWT proof)
- Endpoints publics : /auth/register, /auth/login, /actuator/health
- Endpoints LEARNER : /exercises/*, /submissions/*, /progress/*
- Endpoints INSTRUCTOR : /tracks/* [TODO]
- Endpoints ADMIN : /admin/* [TODO]

### 3.2 Isolation d'exécution (STEP 2)

```
Submission reçue
  ↓
Runner Docker
  ├─→ Container éphémère (créé à la volée)
  ├─→ Java 17 installé
  ├─→ User non-root
  ├─→ Network désactivé
  ├─→ CPU limited (cgroups)
  ├─→ Memory limited (256 MB défaut)
  ├─→ Timeout strict (5 sec défaut)
  ├─→ Compilation du code
  ├─→ Exécution des tests
  ├─→ Capture output + erreurs
  └─→ Destruction du container
  ↓
SubmissionResult saved
```

---

## 4. Schéma de données

### 4.1 Entités principales

```sql
cc_user
├── id (PK)
├── email (UNIQUE)
├── username (UNIQUE)
├── password_hash (BCrypt)
├── role (ENUM: LEARNER | INSTRUCTOR | ADMIN)
└── timestamps (created_at, updated_at)

cc_track
├── id (PK)
├── title
├── description
├── programming_language (java, python, etc.)
├── difficulty (1-5)
├── created_by_user_id (FK → cc_user)
└── timestamps

cc_exercise
├── id (PK)
├── track_id (FK → cc_track)
├── title
├── description
├── starter_code (nullable)
├── test_code ← **JAMAIS exposé à l'apprenant**
├── order_in_track
├── difficulty (1-5)
├── timeout_millis (défaut 5000)
├── memory_limit_mb (défaut 256)
└── timestamps

cc_submission
├── id (PK)
├── exercise_id (FK → cc_exercise)
├── user_id (FK → cc_user)
├── code (le code soumis)
├── status (PENDING | SUCCESS | FAILED | ERROR)
├── attempt_number (1, 2, 3, ...)
└── created_at

cc_submission_result
├── id (PK)
├── submission_id (FK → cc_submission, UNIQUE)
├── compilation_error (nullable)
├── runtime_error (nullable)
├── tests_passed
├── tests_failed
├── failure_details (JSON)
├── execution_time_ms
├── memory_used_mb
└── created_at

cc_coach_conversation
├── id (PK)
├── submission_id (FK → cc_submission, UNIQUE)
├── hint_level (1-4)
├── context (JSON: erreurs, questions posées, etc.)
└── timestamps

cc_coach_message
├── id (PK)
├── conversation_id (FK → cc_coach_conversation)
├── type (QUESTION | HINT | PSEUDO_CODE | EXPLANATION | FEEDBACK)
├── content
├── sequence_number
└── created_at

cc_skill_progress
├── id (PK)
├── user_id (FK → cc_user)
├── exercise_id (FK → cc_exercise)
├── successful_attempts
├── total_attempts
├── is_mastered
├── last_attempt_at
├── mastered_at
└── created_at
```

### 4.2 Index critiques

```sql
-- Performance de login
CREATE INDEX idx_user_email ON cc_user(email);

-- Récupérer les exercices d'une track
CREATE INDEX idx_exercise_track ON cc_exercise(track_id);

-- Soumettre du code pour même exercice/user
CREATE UNIQUE INDEX idx_skill_progress_user_exercise 
  ON cc_skill_progress(user_id, exercise_id);

-- Trouver soumissions à traiter
CREATE INDEX idx_submission_status ON cc_submission(status);
```

---

## 5. API REST - Contrats

### 5.1 Authentification

```
POST /api/v1/auth/register
├─ Input:  UserRegisterDto { email, username, password, firstName?, lastName? }
├─ Output: AuthResponseDto { token, username, role, userId }
└─ Status: 201 Created

POST /api/v1/auth/login
├─ Input:  AuthRequestDto { email, password }
├─ Output: AuthResponseDto { token, username, role, userId }
└─ Status: 200 OK
```

### 5.2 Exercices (lecture)

```
GET /api/v1/exercises/track/{trackId}
├─ Auth:   Bearer token
├─ Output: ExerciseDto[] { id, title, description, starterCode, difficulty }
└─ Status: 200 OK

GET /api/v1/exercises/{exerciseId}/details
├─ Auth:   Bearer token
├─ Output: ExerciseDto (même format)
└─ Status: 200 OK
```

**ATTENTION :** `test_code` NE doit JAMAIS être en réponse !

### 5.3 Soumissions

```
POST /api/v1/submissions/{exerciseId}
├─ Auth:   Bearer token
├─ Input:  { code: "..." }
├─ Output: SubmissionDto { id, status: "PENDING", attemptNumber, ... }
├─ Status: 201 Created
└─ Side:   Enqueue vers Runner (async)

GET /api/v1/submissions
├─ Auth:   Bearer token
├─ Output: SubmissionDto[]
└─ Status: 200 OK

GET /api/v1/submissions/{submissionId}
├─ Auth:   Bearer token
├─ Output: SubmissionDto avec SubmissionResultDto associé (lazy load)
└─ Status: 200 OK
```

### 5.4 Progression (STEP 2)

```
GET /api/v1/progress/summary
├─ Auth:   Bearer token
├─ Output: { exercisesMastered: 5, totalExercises: 20, percentage: 25 }
└─ Status: 200 OK

GET /api/v1/progress/skills
├─ Auth:   Bearer token
├─ Output: SkillProgress[] { exerciseId, success%, isMastered, lastAttempt }
└─ Status: 200 OK
```

---

## 6. Patterns et décisions

### 6.1 Exception handling

```java
// Centralisé via @ControllerAdvice
GlobalExceptionHandler
├─ EntityNotFoundException (404)
├─ InvalidCredentialsException (401)
├─ ValidationException (400)
└─ ServerException (500)
```

### 6.2 Mappage entité ↔ DTO

```java
// Via MapStruct (compilé)
ExerciseMapper.toDto(entity) → ExerciseDto
ExerciseMapper.toEntity(dto) → Exercise

// Jamais d'entités en réponse JSON !
```

### 6.3 Transactions

```java
@Transactional                    // Lecture + écriture
public SubmissionDto submitCode()

@Transactional(readOnly = true)   // Lecture seule
public List<ExerciseDto> getAll()
```

### 6.4 Logging

```java
@Slf4j
class MyService {
    log.info("User registered: {}", userId);
    log.warn("Failed login attempt for: {}", email);
    log.error("Compilation failed: {}", exception);
    log.debug("Detailed debug info"); // Activé en DEV seulement
}
```

---

## 7. Évolution vers microservices (futur)

Chaque module peut être extrait en service indépendant :

```
code-coach-api (actuellement)
  │
  ├─→ code-coach-auth-service
  │   ├─ 8081
  │   └─ Responsable JWT + Security
  │
  ├─→ code-coach-user-service
  │   ├─ 8082
  │   └─ Responsable User CRUD
  │
  ├─→ code-coach-content-service
  │   ├─ 8083
  │   └─ Responsable Tracks + Exercices
  │
  ├─→ code-coach-submission-service
  │   ├─ 8084
  │   └─ Responsable Soumissions
  │
  ├─→ code-coach-runner-service
  │   ├─ 8085
  │   └─ Responsable exécution Docker
  │
  ├─→ code-coach-coach-service
  │   ├─ 8086
  │   └─ Responsable IA socratique
  │
  └─→ code-coach-progress-service
      ├─ 8087
      └─ Responsable progression + gamification
```

**Communication :** REST + Kafka (événements async)

---

## 8. Infrastructure & Déploiement

### 8.1 Stack technique

| Composant | Tecno | Raison |
|-----------|-------|--------|
| **Framework** | Spring Boot 3 | Productif, écosystème riche |
| **Language** | Java 17 | LTS, performance, sécurité |
| **Database** | PostgreSQL 15 | ACID, JSON support, index avancés |
| **Migration** | Flyway | Versioning DB immutable |
| **Cache** | Redis 6 | In-memory, rate-limit, sessions |
| **Build** | Maven 3.8+ | Multi-modules facile |
| **Security** | Spring Security 6 + JWT | Standard industrie |
| **API Doc** | Springdoc OpenAPI [TODO] | Auto-generated Swagger |
| **Monitoring** | Micrometer [TODO] | Metrics pour prod |

### 8.2 Déploiement local

```bash
# 1. DB
docker run -d --name codecoach-postgres \
  -e POSTGRES_DB=codecoach \
  -e POSTGRES_PASSWORD=codecoach123 \
  -p 5432:5432 postgres:15

# 2. Cache (optionnel pour MVP)
docker run -d --name codecoach-redis \
  -p 6379:6379 redis:7

# 3. Build & démarrage
mvn clean package
java -jar api-module/target/api-module-*.jar
```

**Application :** http://localhost:8080/api/v1

---

## 9. Principes pédagogiques (CORE)

### ❌ JAMAIS

1. Générer une solution complète
2. Faire un "autocomplete déguisé"
3. Donner directement la réponse

### ✅ TOUJOURS

1. Poser des questions (Socratique)
2. Hints graduels (Question → Concept → Pseudo-code → Explication)
3. Expliquer les erreurs sans donner la solution
4. Tracer la progression pour motiver

### Coach IA - Levels de hints

```
Level 1: QUESTION
"Qu'est-ce qu'une variable de type entier ?"

Level 2: HINT
"Réfléchis au type de données qui peut stocker des nombres entiers..."

Level 3: PSEUDO_CODE
"if (nombre % 2 == 0) {
  // le nombre est pair
} else {
  // le nombre est impair
}"

Level 4: EXPLANATION
"L'erreur vient de l'opérateur modulo. 
Le % retourne le reste de la division.
Si le reste est 0, le nombre est pair."
```

---

## 10. Testing (MVP minimal)

```java
// UserServiceTest
@SpringBootTest
class UserServiceTest {
    
    @Test
    void testRegisterUser_Success() { ... }
    
    @Test
    void testRegisterUser_DuplicateEmail() { ... }
    
    @Test
    void testValidateCredentials() { ... }
}

// SubmissionControllerTest
@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {
    
    @Test
    void testSubmitCode_Success() { ... }
    
    @Test
    void testSubmitCode_Unauthorized() { ... }
}
```

---

## Résumé : Principes architecturaux clés

| Principe | Implémentation |
|----------|-----------------|
| **Modular Monolith** | Modules isolés, dépendances via shared |
| **Pas d'entités en API** | Toujours DTOs |
| **Stateless Auth** | JWT, pas de sessions |
| **Security-first** | BCrypt + isolation exécution |
| **Test_code secret** | Jamais exposé au client |
| **Coaching socratique** | Hints graduels, pas de solutions |
| **Évolutif** | Extraction facile en microservices |

---

**Architecture Version:** 0.1.0 | **Last updated:** 30 Jan 2025
