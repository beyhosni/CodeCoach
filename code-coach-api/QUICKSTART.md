# 🚀 QUICKSTART - Code Coach Backend

Démarrer le projet en **5 minutes**.

---

## 📋 Prérequis

- **Java 17** (ou +) : https://adoptium.net/
- **Maven 3.8+** : `mvn --version`
- **PostgreSQL 15+** : https://www.postgresql.org/download/
- **Git** : `git --version`

---

## 1️⃣ Configuration de la base de données

### Option A : PostgreSQL local

```bash
# Créer la DB
createdb -U postgres -W codecoach
# Entrer password: codecoach123

# Ou via psql
psql -U postgres -c "CREATE DATABASE codecoach;"
psql -U postgres -d codecoach -c "CREATE USER codecoach WITH PASSWORD 'codecoach123';"
psql -U postgres -d codecoach -c "GRANT ALL PRIVILEGES ON DATABASE codecoach TO codecoach;"
```

### Option B : Docker

```bash
docker run -d \
  --name codecoach-postgres \
  -e POSTGRES_DB=codecoach \
  -e POSTGRES_USER=codecoach \
  -e POSTGRES_PASSWORD=codecoach123 \
  -p 5432:5432 \
  postgres:15

# Vérifier
docker ps
docker logs codecoach-postgres
```

---

## 2️⃣ Configuration du projet

```bash
# 1. Naviguer au répertoire
cd code-coach-api

# 2. Vérifier application.yml
cat api-module/src/main/resources/application.yml

# Adapter si besoin (host DB, port, etc.)
```

**application.yml par défaut :**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/codecoach
    username: codecoach
    password: codecoach123
```

---

## 3️⃣ Build du projet

```bash
# Depuis le répertoire racine code-coach-api/
mvn clean package -DskipTests

# Output attendu:
# [INFO] Building code-coach-api 0.1.0-SNAPSHOT
# [INFO] Building shared-module
# [INFO] Building auth-module
# ...
# [INFO] BUILD SUCCESS
```

**Durée :** ~2-3 minutes (première fois, 30s après)

---

## 4️⃣ Exécution de l'application

```bash
# Option 1 : Via Maven
mvn spring-boot:run -pl api-module

# Option 2 : Via JAR
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar

# Output attendu:
# 2025-01-30 14:30:15.123 INFO ... CodeCoachApplication : Started in 3.45 seconds
# 2025-01-30 14:30:15.456 INFO ... Flyway : Successfully validated 8 migrations
```

**L'API est disponible sur :** `http://localhost:8080/api/v1`

---

## 5️⃣ Tester les endpoints

### 📝 Enregistrement

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "username": "alice",
    "password": "Password123!",
    "firstName": "Alice",
    "lastName": "Wonderland"
  }'

# Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsInJvbGUiOiJMRUFSTkVSIiwiaWF0IjoxNjc0OTM5NDE1LCJleHAiOjE2NzUwMjU4MTV9.XYZ...",
  "username": "alice",
  "role": "LEARNER",
  "userId": 1
}
```

### 🔐 Login

```bash
TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsInJvbGUiOiJMRUFSTkVSIiwiaWF0IjoxNjc0OTM5NDE1LCJleHAiOjE2NzUwMjU4MTV9.XYZ..."

curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "Password123!"
  }'
```

### 📚 Récupérer des exercices

```bash
TOKEN="..."

# Créer d'abord une track en DB ou via fixture
curl -X GET http://localhost:8080/api/v1/exercises/track/1 \
  -H "Authorization: Bearer $TOKEN"

# Response:
[
  {
    "id": 1,
    "trackId": 1,
    "title": "Écrire une méthode isEven",
    "description": "Implémenter isEven(int n)...",
    "starterCode": "public class Solution { ... }",
    "difficulty": 1,
    "createdAt": "2025-01-30T10:30:00Z"
  }
]
```

### 💾 Soumettre du code

```bash
curl -X POST http://localhost:8080/api/v1/submissions/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Solution { public static boolean isEven(int n) { return n % 2 == 0; } }"
  }'

# Response:
{
  "id": 100,
  "exerciseId": 1,
  "userId": 1,
  "code": "public class Solution { ... }",
  "status": "PENDING",
  "attemptNumber": 1,
  "createdAt": "2025-01-30T14:35:00Z"
}
```

---

## 📊 Vérifier la setup

### Logs d'application

```
Démarrage :
INFO ... Flyway: Validating migrations in locations: [classpath:db/migration]
INFO ... Flyway: Successfully validated 8 migrations
INFO ... Flyway: Current version of schema "public": 8
INFO ... JpaBaseConfiguration: HibernateJpaSessionFactoryBean initialized ...
INFO ... CodeCoachApplication: Started CodeCoachApplication in 3.45 seconds

Endpoints sains :
INFO ... DispatcherServlet: Completed initialization in 150 ms
INFO ... MappingJackson2HttpMessageConverter: Making HttpMessageConverter
```

### Santé de l'application

```bash
curl http://localhost:8080/api/v1/actuator/health

# Response:
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "redis": { "status": "UP" }
  }
}
```

### DB schema créé

```bash
psql -U codecoach -d codecoach -c "\dt"

# Affiche :
 public | cc_coach_conversation     | table | codecoach
 public | cc_coach_message          | table | codecoach
 public | cc_exercise               | table | codecoach
 public | cc_skill_progress         | table | codecoach
 public | cc_submission             | table | codecoach
 public | cc_submission_result      | table | codecoach
 public | cc_track                  | table | codecoach
 public | cc_user                   | table | codecoach
```

---

## 🐛 Troubleshooting

### ❌ "Connection refused: localhost:5432"

**Cause :** PostgreSQL ne démarre pas

**Solution :**
```bash
# Linux/Mac
pg_ctl -D /usr/local/var/postgres start

# Docker
docker start codecoach-postgres

# Windows (services)
services.msc → PostgreSQL 15 → Démarrer
```

### ❌ "password authentication failed"

**Cause :** Credentials invalides

**Solution :**
```bash
# Vérifier user/password en application.yml
# Ou réinitialiser la DB :
dropdb -U postgres codecoach
createdb -U postgres codecoach
```

### ❌ "Flyway migration failed: V1__init_user_table.sql"

**Cause :** SQL malformé ou DB non créée

**Solution :**
```bash
# Reset DB
psql -U postgres -c "DROP DATABASE IF EXISTS codecoach;"
psql -U postgres -c "CREATE DATABASE codecoach;"

# Rebuild
mvn clean package -DskipTests
```

### ❌ "Cannot find or load main class com.codecoach.CodeCoachApplication"

**Cause :** Build incomplet

**Solution :**
```bash
mvn clean package
java -jar api-module/target/api-module-0.1.0-SNAPSHOT.jar
```

---

## 📱 Postman Collection

Importer cette collection Postman pour tester facilement :

```json
{
  "info": {
    "name": "Code Coach API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/"
  },
  "item": [
    {
      "name": "Register",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/v1/auth/register",
        "body": {
          "mode": "raw",
          "raw": "{\"email\": \"alice@example.com\", \"username\": \"alice\", \"password\": \"Password123!\"}"
        }
      }
    },
    {
      "name": "Get Exercises",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/v1/exercises/track/1",
        "header": { "Authorization": "Bearer {{token}}" }
      }
    }
  ]
}
```

---

## 📖 Prochaines étapes

1. **Fixtures** : Charger des données d'exemple
   ```bash
   psql -U codecoach -d codecoach -f FIXTURES.sql
   ```

2. **STEP 2** : Implémenter le Runner Docker
   - Créer image Docker pour exécution sécurisée
   - Intégrer Kafka pour soumissions async

3. **STEP 3** : Coach IA socratique
   - Analyser les erreurs
   - Générer hints intelligents

4. **STEP 4** : Frontend & WebSocket
   - Editor de code
   - Feedback temps réel

---

## 🎯 Vérifier la structure

```
code-coach-api/
├── pom.xml                 ✅ Parent avec tous les modules
├── README.md               ✅ Documentation générale
├── ARCHITECTURE.md         ✅ Détails architecture
├── QUICKSTART.md           ✅ Ce fichier
├── FIXTURES.sql            ✅ Données d'exemple
│
├── shared-module/          ✅ DTOs, exceptions
├── auth-module/            ✅ JWT, Security
├── user-module/            ✅ User CRUD
├── content-module/         ✅ Tracks, exercices
├── submission-module/      ✅ Soumissions
├── runner-module/          ⏳ Docker runner (STEP 2)
├── coach-module/           ⏳ IA coaching (STEP 3)
├── progress-module/        ⏳ Progression (STEP 2)
└── api-module/             ✅ Application principale
```

---

## ✅ Checklist de vérification

- [ ] Java 17+ installé
- [ ] PostgreSQL 15+ accessible
- [ ] `mvn clean package` réussit
- [ ] Application démarre sans erreur
- [ ] Enregistrement crée un utilisateur
- [ ] JWT token valide est retourné
- [ ] Authentification works (Bearer token)
- [ ] Endpoints GET exercices retournent []

---

**Besoin d'aide ?** Lire `ARCHITECTURE.md` ou consultez les logs en mode DEBUG :

```yaml
logging:
  level:
    com.codecoach: DEBUG
```

---

**Version:** 0.1.0 | **Date:** 30 Jan 2025
