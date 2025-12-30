# 🎯 Code Coach - START HERE

**Welcome to Code Coach Backend!**

Cette plateforme enseigne à coder en forçant la **compréhension**, pas juste la production de code.

---

## 📍 Vous êtes ici

```
CodeCoach/
└── code-coach-api/  ← Vous êtes ici ✓
```

---

## 🚀 3 chemins pour commencer

### Option 1️⃣ : Démarrer rapidement (5 min)
👉 **Lire : [code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md)**

```bash
cd code-coach-api
mvn clean package -DskipTests
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar
# API → http://localhost:8080/api/v1
```

---

### Option 2️⃣ : Comprendre l'architecture
👉 **Lire : [code-coach-api/ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)**

Deep dive dans :
- Architecture modulaire
- Flux de données
- Schéma de base de données
- Patterns & décisions

---

### Option 3️⃣ : Docs techniques complètes
👉 **Lire : [code-coach-api/README.md](code-coach-api/README.md)**

Contient :
- Stack technique
- API endpoints
- Authentification JWT
- Configuration
- Déploiement

---

## 📂 Fichiers importants

| Fichier | Pourquoi | Lire si... |
|---------|---------|-----------|
| [QUICKSTART.md](code-coach-api/QUICKSTART.md) | Démarrage 5 min | Vous voulez lancer rapidement |
| [README.md](code-coach-api/README.md) | Docs techniques | Vous cherchez les détails API |
| [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) | Deep dive | Vous voulez comprendre le design |
| [VISUAL_SUMMARY.md](code-coach-api/VISUAL_SUMMARY.md) | Vue d'ensemble | Vous préférez les diagrammes |
| [STEP1_COMPLETION.md](code-coach-api/STEP1_COMPLETION.md) | Checklist | Vous vérifiez ce qui est complété |
| [PROJECT_STRUCTURE.txt](code-coach-api/PROJECT_STRUCTURE.txt) | Arborescence fichiers | Vous explorez la structure |
| [FIXTURES.sql](code-coach-api/FIXTURES.sql) | Données d'exemple | Vous testez avec des données |

---

## 🎯 Statut du projet

```
✅ STEP 1 : Architecture & modules (COMPLETE)
   ├─ Modular Monolith (9 modules)
   ├─ JWT authentification
   ├─ CRUD utilisateurs & exercices
   ├─ Soumission code (stub)
   └─ Documentation complète

⏳ STEP 2 : Runner Docker (NEXT)
   ├─ Exécution sécurisée Java
   ├─ Isolation + timeout
   ├─ Queue Kafka async
   └─ Progress tracking

⏳ STEP 3 : Coach IA

⏳ STEP 4 : Frontend React
```

---

## 💻 Prérequis

- **Java 17+** → https://adoptium.net/
- **PostgreSQL 15+** → https://www.postgresql.org/download/
- **Maven 3.8+** → `mvn --version`
- **Git** → `git --version`

---

## 🔐 Authentification en 30 secondes

```bash
# 1. Enregistrement
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "username": "alice",
    "password": "Password123!"
  }'

# Response: { "token": "eyJhbGc...", "userId": 1, ... }

# 2. Utiliser le token
TOKEN="eyJhbGc..."
curl -X GET http://localhost:8080/api/v1/exercises/track/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📚 API Endpoints

### Authentification
- `POST /auth/register` - Enregistrement
- `POST /auth/login` - Connexion

### Exercices (lectures)
- `GET /exercises/track/{id}` - Liste exercices
- `GET /exercises/{id}/details` - Détails

### Soumissions
- `POST /submissions/{id}` - Soumettre code
- `GET /submissions` - Historique
- `GET /submissions/{id}` - Détails

Plus de détails → [README.md](code-coach-api/README.md)

---

## 🏗️ Architecture en 1 image

```
code-coach-api/
├── shared-module        DTOs, exceptions
├── auth-module          JWT, Security
├── user-module          User CRUD
├── content-module       Tracks, Exercices
├── submission-module    Soumissions code
├── runner-module        Exécution [TODO]
├── coach-module         IA [TODO]
├── progress-module      Progression [TODO]
└── api-module           Spring Boot app

Chaque module = potentiel microservice futur
```

Détails → [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)

---

## 🎓 Principe pédagogique

**Code Coach NE FAIT PAS :**
- ❌ Générer une solution
- ❌ Faire autocomplete
- ❌ Donner la réponse

**Code Coach FAIT :**
- ✅ Questions socratiques
- ✅ Hints graduels
- ✅ Feedback d'erreurs
- ✅ Progression tracée

---

## 📖 Navigation recommandée

1. **Premiers pas** (5 min)
   → [QUICKSTART.md](code-coach-api/QUICKSTART.md)

2. **Comprendre l'API** (15 min)
   → [README.md](code-coach-api/README.md#api-endpoints)

3. **Plonger dans l'architecture** (30 min)
   → [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)

4. **Tester les données** (5 min)
   → [FIXTURES.sql](code-coach-api/FIXTURES.sql)

5. **Vérifier la complétude** (10 min)
   → [STEP1_COMPLETION.md](code-coach-api/STEP1_COMPLETION.md)

---

## 🚀 Commandes essentielles

```bash
cd code-coach-api

# Build
mvn clean package -DskipTests

# Run
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar

# Check health
curl http://localhost:8080/api/v1/actuator/health

# View logs
tail -f api-module/target/*.log
```

---

## 🆘 Besoin d'aide ?

| Question | Réponse |
|----------|--------|
| Comment démarrer ? | [QUICKSTART.md](code-coach-api/QUICKSTART.md) |
| Comment ça marche ? | [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) |
| Quels endpoints ? | [README.md](code-coach-api/README.md#api-endpoints) |
| Structure du projet ? | [PROJECT_STRUCTURE.txt](code-coach-api/PROJECT_STRUCTURE.txt) |
| Erreur au démarrage ? | [QUICKSTART.md#troubleshooting](code-coach-api/QUICKSTART.md#-troubleshooting) |

---

## ✨ Prochaines étapes

**Après STEP 1 (architecture) :**

1. Tester localement → 5 min
2. Lire ARCHITECTURE.md → 30 min
3. Explorer le code → 1h
4. **Préparation STEP 2** → Runner Docker

---

## 📞 Contact

- **Architecture questions** → ARCHITECTURE.md
- **Setup issues** → QUICKSTART.md
- **API details** → README.md
- **Code explorer** → PROJECT_STRUCTURE.txt

---

**Version :** 0.1.0-SNAPSHOT  
**Status :** ✅ STEP 1 Complete  
**Date :** 30 January 2025

👉 **Commencez par :** [code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md)
