# Code Coach - Frontend MVP (STEP 4)

🎓 **Frontend React TypeScript pour la plateforme d'apprentissage Code Coach**

---

## 📋 Aperçu

Frontend MVP pour Code Coach incluant :
- ✅ Authentification JWT
- ✅ Liste des tracks et exercices
- ✅ Éditeur de code Monaco
- ✅ Soumission de code et polling résultats
- ✅ Chat Coach IA socratique avec hints 1-4
- ✅ Stockage local du brouillon de code
- ✅ UI responsive Tailwind CSS

---

## 🚀 Démarrage rapide

### Prérequis

- Node.js 18+
- npm ou yarn
- Backend Code Coach en cours d'exécution (http://localhost:8080/api)

### Installation

```bash
# 1. Installer les dépendances
npm install

# 2. Configurer l'API (optionnel si localhost:8080)
# Éditer .env si le backend est ailleurs
VITE_API_BASE_URL=http://localhost:8080/api

# 3. Lancer le dev server
npm run dev

# L'app s'ouvre à http://localhost:3000
```

### Build pour production

```bash
npm run build
npm run preview
```

---

## 🌳 Structure du projet

```
src/
├── api/                 # Clients API
│   ├── client.ts       # Axios instance + interceptors JWT
│   ├── auth.ts         # POST login
│   ├── tracks.ts       # GET tracks
│   ├── exercises.ts    # GET exercises
│   ├── submissions.ts  # POST submit, GET result
│   └── coach.ts        # POST hint
│
├── auth/               # Authentification
│   ├── AuthContext.tsx # Context state + localStorage
│   └── useAuth.ts      # Hook pour accéder auth
│
├── pages/              # Pages principales
│   ├── LoginPage.tsx   # Formulaire login
│   ├── TracksPage.tsx  # Liste tracks
│   ├── TrackDetailPage.tsx  # Exercices d'une track
│   └── ExercisePage.tsx     # Workspace complet
│
├── components/         # Composants réutilisables
│   ├── AppShell.tsx    # Navbar + layout
│   ├── ProtectedRoute.tsx   # Route protégée
│   ├── MonacoEditorPane.tsx # Éditeur de code
│   ├── SubmissionResultPanel.tsx # Résultats tests
│   ├── CoachPanel.tsx  # Chat coach IA
│   └── AutonomyScoreBadge.tsx # Score autonomie (stub)
│
├── hooks/              # Hooks personnalisés
│   ├── useLocalDraft.ts     # Gestion draft localStorage
│   └── useSubmissionPolling.ts # Polling résultats
│
├── types/              # TypeScript DTOs
│   └── dto.ts          # Interfaces API
│
├── utils/              # Utilitaires
│   └── constants.ts    # Configs, endpoints, storage keys
│
├── App.tsx             # Routing principal
├── main.tsx            # Point d'entrée
└── index.css           # Tailwind globals
```

---

## 🔐 Authentification

### Flow

1. Utilisateur remplit formulaire login (email + password)
2. Frontend POST `/auth/login` → reçoit `accessToken`
3. Token stocké en localStorage + React Context
4. Axios interceptor ajoute `Authorization: Bearer <token>` sur toutes requêtes
5. Redirect vers `/tracks`

### Configuration

```typescript
// .env
VITE_API_BASE_URL=http://localhost:8080/api

// Les tokens sont gérés automatiquement par:
// - localStorage (persistence)
// - AuthContext (state global)
// - Axios interceptor (ajout header)
```

### Logout

- Bouton logout dans navbar
- Supprime token + user de localStorage
- Redirect vers `/login`

---

## 📱 Pages principales

### /login
Formulaire d'authentification avec gestion erreurs et loading.

```bash
# Test:
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"pass"}'
```

### /tracks
Liste des pistes d'apprentissage. Clic → `/tracks/:trackId`

### /tracks/:trackId
Liste des exercices d'une piste. Clic → `/exercise/:exerciseId`

### /exercise/:exerciseId
**Workspace complet** (complexité maximale) :

#### 📝 Colonne gauche
- **Énoncé** du problème
- **Code de départ** (optionnel)
- **"Mon approche"** textarea (stockée pendant la session)

#### 💻 Colonne droite (haut)
- **Monaco Editor**
  - Thème: `vs-dark`
  - Language: Java / Python / JavaScript / C++
  - Minimap désactivée
  - Auto-save en localStorage
  - Code de départ accessible via "Reset"

#### 📊 Colonne droite (bas)
- **Run Tests** button
  - Lance POST `/submissions` avec code
  - Polling automatique GET `/result` (1s interval, 30s max)
- **Résultats**
  - Status badge (PASSED / FAILED / RUNNING)
  - Tests passed/total
  - Compilation errors (si applicable)
  - Console logs repliable (stdout/stderr)
  - Temps d'exécution

#### 🤖 Coach Panel (pleine largeur bas)
- **Chat history** (messages user + coach)
- **"Mon approche"** field (lecture seule, rempli de gauche)
- **4 boutons d'hints**
  - Niveau 1: Question orientante ❓
  - Niveau 2: Indice conceptuel 💡
  - Niveau 3: Pseudo-code 📝
  - Niveau 4: Explication erreur 🔍
  - Disabled si pas d'approche remplie
- **Messages du coach**
  - Type: QUESTION / HINT / PSEUDOCODE / ERROR_EXPLANATION
  - Réponses socratiques (ne donne jamais la solution)

---

## 🌐 API Configuration

Base URL par défaut: `http://localhost:8080/api`

Modifiable via `.env`:
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Endpoints utilisés

| Endpoint | Méthode | Rôle |
|----------|---------|------|
| `/auth/login` | POST | Authentification |
| `/tracks` | GET | Liste pistes |
| `/exercises?trackId=...` | GET | Exercices piste |
| `/exercises/{id}` | GET | Détail exercice |
| `/submissions` | POST | Soumettre code |
| `/submissions/{id}/result` | GET | Résultats tests |
| `/coach/hint` | POST | Demander indice |

### Exemple request avec token

```bash
TOKEN="eyJhbGc..."
curl -X GET http://localhost:8080/api/exercises/track/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 💾 Données locales

### localStorage keys

| Clé | Contenu |
|-----|---------|
| `codecoach_auth_token` | JWT token |
| `codecoach_user` | User object JSON |
| `codecoach_draft_<exerciseId>` | Code draft par exercice |

### Auto-save

- Code édité → sauvegardé dans localStorage (debounce implicite par React)
- Chargement exercice → restaure draft s'il existe
- "Reset" → supprime draft, remet code initial

---

## 🧪 Tests

### Lancer les tests

```bash
npm run test          # Vitest watch mode
npm run test:ui       # UI Vitest
```

### Structure

```
src/__tests__/
├── LoginPage.test.tsx
├── ExercisePage.test.tsx
└── api/
    └── client.test.ts
```

### Coverage

- LoginPage: formulaire, login success/error, redirection
- ExercisePage: rendu, Monaco, soumission, polling, coach hints
- API client: JWT interceptor, token refresh (TODO)

---

## 🎨 UI/UX

### Design système

- **Colors**: Tailwind defaults + custom `coach-primary`, `coach-dark`, `coach-light`
- **Fonts**: Sans-serif système + `'Fira Code'` pour code
- **Layout**: Responsive grid, mobile-first

### Accessibilité

- Formulaires: `<label>` + `htmlFor`
- Aria: `aria-label` sur boutons sans texte
- Focus states: `:focus-ring-2 :focus-ring-blue-500`
- Dark mode: Ready (pas de toggle MVP, mais structure prête)

### Icônes

Émojis utilisés (pas de librarie d'icons pour MVP) :
- 🎓 Logo
- 📚 Tracks
- 💻 Exercices
- 🤖 Coach
- 🎯 Autonomy score
- ❓ Question hint
- 💡 Concept hint
- 📝 Pseudocode hint
- 🔍 Error hint

---

## 🔧 Stack technique

| Composant | Version | Rôle |
|-----------|---------|------|
| React | 18.2 | Framework UI |
| TypeScript | 5.3 | Typage statique |
| Vite | 5.0 | Build tool |
| Tailwind CSS | 3.3 | Styling |
| React Router | 6.20 | Routing |
| Axios | 1.6 | HTTP client |
| TanStack Query | 5.28 | Data fetching |
| Monaco Editor | 4.5 | Code editor |
| Zod | 3.22 | Validation (ready) |
| React Hook Form | 7.48 | Forms (ready) |
| Vitest | 0.34 | Testing |
| ESLint + Prettier | Latest | Lint + format |

---

## 📋 TODO / Futurs améliorations (STEP 5+)

- [ ] WebSocket pour feedback live (au lieu de polling)
- [ ] Refresh tokens + session expiration handling
- [ ] Tests de couverture complète (50%+ MVP)
- [ ] PWA: offline mode, install app
- [ ] Dark mode toggle
- [ ] Gamification UI: badges, streaks, leaderboard
- [ ] Submit format (prettier integration)
- [ ] Historique des soumissions
- [ ] Partage de code (collaboration future)
- [ ] Keyboard shortcuts (Ctrl+Enter = Run)
- [ ] Terminal pour compilation locale (native runner)

---

## 🐛 Debugging

### Logs

Activer debug logs (fetch results, auth, etc.):
```typescript
// src/utils/constants.ts
export const DEBUG = true;
```

### Console

```javascript
// Vérifier token
localStorage.getItem('codecoach_auth_token')

// Vérifier user
JSON.parse(localStorage.getItem('codecoach_user'))

// Vérifier draft
localStorage.getItem('codecoach_draft_ex-123')
```

### Network tab

- Vérifier Authorization header sur requêtes protégées
- Checker responses pour endpoint responses
- Polling interval visible dans timeline

---

## 📝 Notes de développement

### API Mismatch

Si l'API backend diffère de la spec, la couche `/src/api/` permet adaptation facile sans toucher composants.

### Polling Strategy

- Interval: 1s (configurable `SUBMISSION_POLLING_INTERVAL`)
- Max attempts: 30 (30s timeout)
- Backoff: None (constant interval MVP, amélioration future)

### Error Handling

- API errors → alert() simple (MVP)
- Future: toast notifications (react-hot-toast)
- Network errors → re-poll automatiquement

### Performance

- React Query caching (GET requests cached)
- Monaco lazy-loaded via @monaco-editor/react
- Tailwind: purged via content glob (prod build ~50KB gzipped)

---

## 👤 Auteur

Frontend Engineer (React + TypeScript)
- Architecture: Composants modulaires + hooks
- State: Context + React Query
- Routing: React Router v6
- Styling: Tailwind CSS utility-first

---

**Last updated:** 30 Dec 2025 | **Version:** 0.1.0-MVP

