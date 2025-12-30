# 📚 Documentation Index

**Code Coach - Guide de navigation**

---

## 🎯 Démarrer ici

**Vous êtes nouveau ?** → [START_HERE.md](START_HERE.md)

C'est le point d'entrée. Lire les 3 chemins pour commencer.

---

## 📖 Documentation par besoin

### 1️⃣ Je veux lancer l'app en 5 min

👉 **[code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md)**

Contient :
- Prérequis (Java, PostgreSQL, Maven)
- Configuration DB (local ou Docker)
- Build & démarrage
- Tests API (curl examples)
- Troubleshooting

**Temps estimé :** 5 minutes

---

### 2️⃣ Je veux comprendre l'architecture

👉 **[code-coach-api/ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)**

Contient :
- Vue d'ensemble modulaire
- Dépendances entre modules
- Flux de données (auth, submission, coaching)
- Schéma de base de données
- Patterns & décisions clés
- Sécurité implémentée
- Évolution vers microservices

**Temps estimé :** 30 minutes

---

### 3️⃣ Je cherche les endpoints API

👉 **[code-coach-api/README.md](code-coach-api/README.md)**

Contient :
- Vue d'ensemble du projet
- Stack technique
- Authentification JWT (requests/responses)
- Tous les endpoints REST
- Exemples curl
- Configuration Spring Boot
- Prochaines étapes (STEP 2-4)

**Temps estimé :** 15 minutes

---

### 4️⃣ Je veux voir la structure des fichiers

👉 **[code-coach-api/PROJECT_STRUCTURE.txt](code-coach-api/PROJECT_STRUCTURE.txt)**

Contient :
- Arborescence complète du projet
- Description de chaque dossier/fichier
- Architecture overview
- Database schema
- Technology stack
- Statistics

**Temps estimé :** 5 minutes

---

### 5️⃣ Je veux tester avec des données d'exemple

👉 **[code-coach-api/FIXTURES.sql](code-coach-api/FIXTURES.sql)**

Contient :
- Données d'exemple SQL
  - 3 utilisateurs (2 learners, 1 instructor)
  - 3 tracks
  - 4 exercises
  - 2 submissions avec résultats
  - Progress tracking

**Utilisation :**
```bash
psql -U codecoach -d codecoach -f code-coach-api/FIXTURES.sql
```

**Temps estimé :** 2 minutes

---

### 6️⃣ Je veux vérifier ce qui est complété

👉 **[code-coach-api/STEP1_COMPLETION.md](code-coach-api/STEP1_COMPLETION.md)**

Contient :
- ✅ Checklist complète STEP 1
- Livrables détaillés
- Couverture code
- Décisions documentées
- Roadmap STEP 2-4
- Statistics

**Temps estimé :** 10 minutes

---

### 7️⃣ Je veux voir un résumé visuel

👉 **[code-coach-api/VISUAL_SUMMARY.md](code-coach-api/VISUAL_SUMMARY.md)**

Contient :
- Diagrammes ASCII art
- Vue d'ensemble tech stack
- Modules et dépendances
- Flux authentification
- Schema entities
- API endpoints
- Roadmap visuelle

**Temps estimé :** 5 minutes

---

## 📍 Localisation des documents

```
CodeCoach/
├── START_HERE.md                    ← LIRE D'ABORD !
├── README_NEW.md                    (Nouveau README workspace)
├── DOCUMENTATION_INDEX.md           ← Ce fichier
│
└── code-coach-api/
    ├── README.md                    (Docs API + configuration)
    ├── QUICKSTART.md                (Démarrage 5 min)
    ├── ARCHITECTURE.md              (Deep dive architecture)
    ├── STEP1_COMPLETION.md          (Checklist complétion)
    ├── VISUAL_SUMMARY.md            (Résumé visual)
    ├── PROJECT_STRUCTURE.txt        (Arborescence fichiers)
    ├── FIXTURES.sql                 (Données d'exemple)
    ├── .gitignore
    ├── pom.xml                      (Maven parent)
    │
    └── [8 modules Maven]
```

---

## 🗺️ Navigation par cas d'usage

### Cas : "Je débute sur ce projet"
1. Lire [START_HERE.md](START_HERE.md) (5 min)
2. Lire [QUICKSTART.md](code-coach-api/QUICKSTART.md) (10 min)
3. Lancer l'app (5 min)
4. Tester les endpoints (5 min)

**Total :** 25 minutes

---

### Cas : "Je comprends le projet, je veux contribuer"
1. Lire [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) (30 min)
2. Explorer [PROJECT_STRUCTURE.txt](code-coach-api/PROJECT_STRUCTURE.txt) (5 min)
3. Lire le code des modules (30 min)

**Total :** 1 heure 5 minutes

---

### Cas : "Je dois debugger une erreur"
1. Lire [QUICKSTART.md#troubleshooting](code-coach-api/QUICKSTART.md#troubleshooting)
2. Vérifier les logs de l'app
3. Consulter [README.md](code-coach-api/README.md) pour détails API

**Total :** 15 minutes

---

### Cas : "Je dois implémenter STEP 2 (Runner Docker)"
1. Lire [ARCHITECTURE.md#7-evolution-vers-microservices](code-coach-api/ARCHITECTURE.md)
2. Lire [STEP1_COMPLETION.md#prochaines-etapes-step-2](code-coach-api/STEP1_COMPLETION.md)
3. Comprendre le workflow submission (ARCHITECTURE.md)

**Total :** 45 minutes

---

## 📊 Matrice documentation vs besoin

| Besoin | QUICKSTART | README | ARCHITECTURE | PROJECT_STRUCT | FIXTURES | COMPLETION |
|--------|-----------|--------|--------------|-----------------|----------|------------|
| Démarrer app | ✅ | - | - | - | - | - |
| Endpoints API | ✅ | ✅ | - | - | - | - |
| Database schema | - | - | ✅ | ✅ | - | - |
| Comprendre design | - | - | ✅ | - | - | - |
| Tester avec données | - | - | - | - | ✅ | - |
| Vérifier complétion | - | - | - | - | - | ✅ |
| Débugger erreur | ✅ | ✅ | - | - | - | - |
| Implémenter STEP 2 | - | - | ✅ | - | - | ✅ |

---

## 🎯 Chemins d'apprentissage recommandés

### Path 1: Quick Start (Total 1h)
```
START_HERE.md → QUICKSTART.md → Launch app → Test API
```

---

### Path 2: Deep Dive (Total 2h)
```
START_HERE.md → QUICKSTART.md → ARCHITECTURE.md 
→ PROJECT_STRUCTURE.txt → Explore code
```

---

### Path 3: Full Mastery (Total 4h)
```
All docs above + reading source code
+ STEP1_COMPLETION.md + VISUAL_SUMMARY.md
```

---

### Path 4: Contribution Ready (Total 3h)
```
START_HERE.md → QUICKSTART.md → ARCHITECTURE.md
→ STEP1_COMPLETION.md → Explore modules → Code review
```

---

## 🔍 Index des topics

### Authentification
- [QUICKSTART.md#5-tester-les-endpoints](code-coach-api/QUICKSTART.md) - Test auth
- [README.md#authentification](code-coach-api/README.md) - Auth details
- [ARCHITECTURE.md#31-authentification-jwt](code-coach-api/ARCHITECTURE.md) - Auth architecture

### API Endpoints
- [README.md#api-endpoints](code-coach-api/README.md) - All endpoints
- [QUICKSTART.md#5-tester-les-endpoints](code-coach-api/QUICKSTART.md) - Curl examples
- [VISUAL_SUMMARY.md#-api-endpoints](code-coach-api/VISUAL_SUMMARY.md) - Visual

### Database
- [ARCHITECTURE.md#4-schéma-de-données](code-coach-api/ARCHITECTURE.md) - Full schema
- [PROJECT_STRUCTURE.txt](#database-schema) - Schema details
- [FIXTURES.sql](code-coach-api/FIXTURES.sql) - Example data

### Architecture
- [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) - Full architecture
- [PROJECT_STRUCTURE.txt](#architecture-overview) - Architecture overview
- [VISUAL_SUMMARY.md](code-coach-api/VISUAL_SUMMARY.md) - Visual architecture

### Modules
- [PROJECT_STRUCTURE.txt](#project-structure) - File structure
- [ARCHITECTURE.md#1-stratégie-modulaire](code-coach-api/ARCHITECTURE.md) - Module strategy

### Security
- [README.md#sécurité-mvp](code-coach-api/README.md) - Security details
- [ARCHITECTURE.md#3-architecture-de-sécurité](code-coach-api/ARCHITECTURE.md) - Security deep dive

### Setup & Deployment
- [QUICKSTART.md](code-coach-api/QUICKSTART.md) - Full setup guide
- [README.md#configuration--démarrage](code-coach-api/README.md) - Configuration details

### Roadmap
- [STEP1_COMPLETION.md#prochaines-étapes-step-2](code-coach-api/STEP1_COMPLETION.md) - Next steps
- [README.md#todo](code-coach-api/README.md) - Feature TODOs

---

## 💡 Tips de lecture

1. **Débutant ?** 
   - Commencer par [START_HERE.md](START_HERE.md)
   - Puis [QUICKSTART.md](code-coach-api/QUICKSTART.md)

2. **Impatient ?**
   - Lire [VISUAL_SUMMARY.md](code-coach-api/VISUAL_SUMMARY.md) (5 min)
   - Puis lancer l'app

3. **Architecte ?**
   - Directement [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)
   - Puis explorer le code

4. **Contributeur ?**
   - [QUICKSTART.md](code-coach-api/QUICKSTART.md) (setup)
   - [ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md) (design)
   - [STEP1_COMPLETION.md](code-coach-api/STEP1_COMPLETION.md) (context)

---

## ✅ Checklist lecture

- [ ] Lire START_HERE.md (5 min)
- [ ] Choisir votre besoin et lire le doc correspondant (10-30 min)
- [ ] Lancer l'app si démarrage rapide (5 min)
- [ ] Tester les endpoints (5 min)
- [ ] Lire ARCHITECTURE.md pour compréhension complète (30 min)

**Total estimé :** 1-2 heures pour compréhension complète

---

## 📞 Questions fréquentes

**Q: Par où je commence ?**
A: [START_HERE.md](START_HERE.md)

**Q: Comment je lance l'app ?**
A: [QUICKSTART.md](code-coach-api/QUICKSTART.md)

**Q: Quels endpoints existent ?**
A: [README.md#api-endpoints](code-coach-api/README.md#api-endpoints)

**Q: Comment fonctionne l'authentification ?**
A: [ARCHITECTURE.md#31-authentification-jwt](code-coach-api/ARCHITECTURE.md)

**Q: Où est le code de X ?**
A: [PROJECT_STRUCTURE.txt](code-coach-api/PROJECT_STRUCTURE.txt)

**Q: Qu'est-ce qui est complété ?**
A: [STEP1_COMPLETION.md](code-coach-api/STEP1_COMPLETION.md)

---

## 🎉 Vous êtes prêt !

Commencez par :
👉 **[START_HERE.md](START_HERE.md)**

Puis :
👉 **[code-coach-api/QUICKSTART.md](code-coach-api/QUICKSTART.md)**

Enfin :
👉 **[code-coach-api/ARCHITECTURE.md](code-coach-api/ARCHITECTURE.md)**

---

**Créé :** 30 January 2025  
**Version :** 0.1.0-SNAPSHOT  
**Status :** ✅ STEP 1 Complete
