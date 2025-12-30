# Code Coach Platform

🎓 **Plateforme d'apprentissage du code par compréhension (Socratic Coaching)**

---

## 📂 Structure du projet

```
CodeCoach/
├── code-coach-api/          ← Backend Spring Boot 3 + Java 17 [ACTIVE]
│   ├── README.md            Docs techniques complètes
│   ├── QUICKSTART.md        Démarrer en 5 minutes
│   ├── ARCHITECTURE.md      Architecture détaillée
│   ├── FIXTURES.sql         Données d'exemple
│   ├── pom.xml              Maven parent multi-modules
│   │
│   ├── shared-module/       DTOs, exceptions, configs
│   ├── auth-module/         JWT, authentification
│   ├── user-module/         Gestion utilisateurs
│   ├── content-module/      Tracks, exercices
│   ├── submission-module/   Soumission code
│   ├── runner-module/       Orchestration exécution [TODO STEP 2]
│   ├── coach-module/        IA socratique [TODO STEP 3]
│   ├── progress-module/     Progression, gamification [TODO STEP 2]
│   └── api-module/          Application Spring Boot principale
│
└── code-coach-ui/          ← Frontend React [TODO STEP 4]
```

---

## 🚀 Démarrage rapide

### Lire d'abord

1. [code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md) - 5 min pour démarrer
2. [code-coach-api/README.md](code-coach-api/README.md) - Docs complètes
3. [code-coach-api/ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) - Deep dive architecture

### Prérequis

- Java 17+
- PostgreSQL 15+
- Maven 3.8+

### 3 commandes pour démarrer

```bash
cd code-coach-api

# 1. Build
mvn clean package -DskipTests

# 2. Lancer l'app
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar

# 3. Tester
curl http://localhost:8080/api/v1/actuator/health
```

**API disponible à :** http://localhost:8080/api/v1

---

## 📊 Statut MVP

**STEP 1 ✅** - Architecture & modules fondamentaux
- [x] Modular monolith Spring Boot 3
- [x] Authentification JWT
- [x] CRUD utilisateurs & exercices
- [x] Soumission de code (stub)
- [x] Entités JPA + migrations Flyway
- [x] Services & contrôleurs REST

**STEP 2 ⏳** - Runner Docker (exécution sécurisée)
- [ ] Image Docker pour isolation
- [ ] Queue Kafka async
- [ ] Timeout + limites ressources

**STEP 3 ⏳** - Coach IA socratique
- [ ] Analyse d'erreurs
- [ ] Hints intelligents
- [ ] NLP pour classification

**STEP 4 ⏳** - Frontend & WebSocket
- [ ] Editor de code (Monaco)
- [ ] WebSocket live feedback
- [ ] Tableau de bord progression

---

## 🎯 Principes fondamentaux

### ❌ Code Coach NE FAIT PAS

1. Générer une solution complète
2. Faire autocomplete déguisé
3. Donner directement la réponse

### ✅ Code Coach FAIT

1. **Questions socratiques** - Orienter la réflexion
2. **Hints graduels** - Question → Concept → Pseudo-code → Explication
3. **Feedback d'erreurs** - Expliquer sans donner la solution
4. **Progression tracée** - Motiver sans illusionner

---

## 🏗️ Architecture

### Modules

Chaque module est **indépendant** et **prêt à être extrait** en microservice :

```
api-module (Spring Boot principale)
├── auth-module         - JWT, authentification, sécurité
├── user-module         - CRUD utilisateurs, hachage password
├── content-module      - Tracks, exercices (curriculum)
├── submission-module   - Soumissions code, versioning
├── runner-module       - Orchestration exécution (Docker)
├── coach-module        - IA socratique, hints, questions
├── progress-module     - Tracking, gamification, stats
└── shared-module       - DTOs, exceptions, configs communes
```

**Dépendances :** Tous les modules → shared-module uniquement

---

## 🔐 Authentification

### JWT

```bash
# Enregistrement
POST /api/v1/auth/register
{
  "email": "alice@example.com",
  "username": "alice",
  "password": "Password123!"
}

# Réponse
{
  "token": "eyJhbGc...",
  "username": "alice",
  "role": "LEARNER",
  "userId": 1
}

# Ensuite : Authorization: Bearer <token>
```

---

## 📚 API Endpoints

### Authentification
- `POST /auth/register` - Créer un compte
- `POST /auth/login` - Connexion

### Exercices
- `GET /exercises/track/{trackId}` - Liste des exercices
- `GET /exercises/{exerciseId}/details` - Détails d'un exercice

### Soumissions
- `POST /submissions/{exerciseId}` - Soumettre du code
- `GET /submissions` - Historique utilisateur
- `GET /submissions/{submissionId}` - Détails d'une soumission

Voir [code-coach-api/README.md](code-coach-api/README.md) pour tous les détails.

---

## 💻 Stack Technique

### 🏗️ Backend & Framework

```
┌─────────────────────────────────────────────────┐
│  🚀 Spring Boot 3.2.1                          │
│  ☕ Java 17 LTS                                │
│  📦 Maven 3.8+ (Multi-modules)                 │
│  🔒 Spring Security 6.x                        │
└─────────────────────────────────────────────────┘
```

### 🗄️ Données & Persistance

| Composant | Tech | Version | Rôle |
|-----------|------|---------|------|
| **Database** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white) | 15+ | ACID, JSON, Audit trail |
| **Migration** | ![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=flat-square&logo=flyway&logoColor=white) | 9.x | Versioning DB schema |
| **ORM** | ![JPA/Hibernate](https://img.shields.io/badge/JPA%2FHibernate-59666C?style=flat-square) | 6.x | Object-Relational Mapping |

### 🔐 Sécurité & Authentification

| Composant | Tech | Détails |
|-----------|------|---------|
| **Auth** | 🔑 **JWT** | HMAC-SHA256, 24h expiration |
| **Password** | 🛡️ **BCrypt** | 12 rounds, resistant GPU attacks |
| **Authorization** | 👤 **Spring Security** | @PreAuthorize par role (LEARNER/INSTRUCTOR/ADMIN) |

### 🚄 Cache & Async

```
┌──────────────────────┬──────────────────────┐
│ 💾 Redis 6+          │ 📨 Kafka (Ready)     │
│ Caching Layer        │ Event Streaming      │
│ Rate Limiting        │ Async Processing     │
└──────────────────────┴──────────────────────┘
```

### 🛠️ Outils & Librairies

| Catégorie | Outils |
|-----------|--------|
| **DTO Mapping** | 🗺️ MapStruct (auto-mapping) |
| **Annotations** | 🧊 Lombok (reduce boilerplate) |
| **Validation** | ✅ Jakarta Validation (@Valid) |
| **Logging** | 📝 SLF4J + Logback |
| **Testing** | 🧪 JUnit 5 (prepared) |

---

## 📊 Architecture Générale

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (React)                          │
│              (Monaco Editor, WebSocket)                      │
└────────┬────────────────────────────────────────────┬────────┘
         │                                            │
    HTTP/HTTPS                               WebSocket/JSON
         │                                            │
┌────────▼────────────────────────────────────────────▼────────┐
│                  API Module (Spring Boot 3)                   │
│                  (8 REST Endpoints)                           │
└────────┬────────────────────────────────────────────┬────────┘
         │                                            │
    ┌────▼─────┬──────────┬───────┬──────┬──────┐    │
    │           │          │       │      │      │    │
  Auth      User       Content  Submit  Coach Progress │
  Module    Module     Module   Module  Module Module  │
    │           │          │       │      │      │    │
    └───────────┴──────────┴───────┴──────┴──────┴────┘
         (Tous dépendent de shared-module)
         │
┌────────▼────────────────────────────────┐
│      PostgreSQL 15 + Flyway              │
│      (8 Tables, Audit trail)             │
└─────────────────────────────────────────┘
         │
         ▼
    Redis 6+ (Cache/Rate-limit)
    Kafka (Event streaming)
```

---

## ✨ Highlights Technologiques

| Feature | Implémentation |
|---------|----------------|
| 🔐 **Sécurité JWT** | HMAC-SHA256, Token 24h, Stateless |
| 🛡️ **Password Hashing** | BCrypt 12-rounds (0.5-1s per hash) |
| 📦 **Modular Design** | 9 modules indépendants, prêts microservices |
| 🗄️ **Versioned Migrations** | Flyway V1-V8 (reproducible schema) |
| 🔍 **Audit Trail** | created_at, updated_at sur toutes entities |
| ⚡ **Async Ready** | @EnableAsync, Kafka (prepared) |
| 🚀 **Spring Boot 3** | Latest stable, GraalVM ready |
| 📊 **Caching** | Redis intégré (optional MVP) |

---

## 📋 Documentation

| Document | Description |
|----------|------------|
| [code-coach-api/README.md](code-coach-api/README.md) | Docs techniques complètes |
| [code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md) | Démarrage en 5 minutes |
| [code-coach-api/ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) | Architecture détaillée |
| [code-coach-api/FIXTURES.sql](code-coach-api/FIXTURES.sql) | Données d'exemple |

---

## 🧪 Tester rapidement

```bash
cd code-coach-api

# Enregistrer
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","username":"user","password":"Pass123!"}'

# Stocker le token
TOKEN="eyJhbGc..."

# Tester exercices (après avoir créé une track en DB)
curl -X GET http://localhost:8080/api/v1/exercises/track/1 \
  -H "Authorization: Bearer $TOKEN"

# Soumettre du code
curl -X POST http://localhost:8080/api/v1/submissions/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"code":"public class Solution { ... }"}'
```

---

## 🤝 Contribution

Chaque changement respecte :
- ✅ Pas de solution brute pour apprenants
- ✅ Tests unitaires minimum
- ✅ Logging adéquat pour debugging
- ✅ Validation input stricte
- ✅ Documentation des décisions

---

## 📝 License

TBD

---

## 👥 Lead

**Senior Software Architect & Lead Backend Engineer**
- Spécialisé en : Java 17, Spring Boot 3, SaaS Architecture, Sécurité d'exécution
- Architecture : Modular Monolith → Microservices
- Pédagogie : Socratic Coaching (pas d'autocomplete)

---

**Créé :** 30 Jan 2025 | **Version MVP:** 0.1.0
