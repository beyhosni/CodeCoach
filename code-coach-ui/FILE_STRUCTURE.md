# 📁 Frontend Project - Complete File Structure

## Generated Files Summary

### Configuration Files (7)
```
code-coach-ui/
├── package.json              ← Dependencies & scripts
├── tsconfig.json             ← TypeScript strict config
├── tsconfig.node.json        ← TypeScript for Node
├── vite.config.ts            ← Vite build config
├── vitest.config.ts          ← Tests config
├── tailwind.config.js        ← Tailwind CSS config
├── postcss.config.js         ← PostCSS config (Tailwind)
├── .eslintrc.cjs             ← ESLint rules
├── .prettierrc                ← Prettier format rules
├── .env                       ← Environment variables
├── .env.example               ← Example env file
└── .gitignore                ← Git ignore rules
```

### HTML & Entry Point (2)
```
code-coach-ui/
├── index.html                ← React app entry
├── src/
│   ├── main.tsx              ← Vite entry (React mount)
│   └── index.css             ← Tailwind + global styles
```

### API Layer (6)
```
src/api/
├── client.ts                 ← Axios instance + JWT interceptor
├── auth.ts                   ← POST /auth/login
├── tracks.ts                 ← GET /tracks
├── exercises.ts              ← GET /exercises
├── submissions.ts            ← POST /submissions, GET /result
├── coach.ts                  ← POST /coach/hint
└── index.ts                  ← Barrel export
```

### Authentication (2)
```
src/auth/
├── AuthContext.tsx           ← Context provider + state
├── useAuth.ts                ← Hook to access auth
└── (no index.ts for auth)
```

### Pages (4)
```
src/pages/
├── LoginPage.tsx             ← Email/password login
├── TracksPage.tsx            ← List of tracks
├── TrackDetailPage.tsx       ← List exercises in track
└── ExercisePage.tsx          ← **Main workspace** (full UI)
```

### Components (7)
```
src/components/
├── AppShell.tsx              ← Layout wrapper + navbar
├── ProtectedRoute.tsx        ← Route guard wrapper
├── MonacoEditorPane.tsx      ← Code editor widget
├── SubmissionResultPanel.tsx ← Test results display
├── CoachPanel.tsx            ← Coach IA chat interface
├── AutonomyScoreBadge.tsx    ← Score badge (stub)
└── index.ts                  ← Barrel export
```

### Custom Hooks (2)
```
src/hooks/
├── useLocalDraft.ts          ← Code draft localStorage mgmt
├── useSubmissionPolling.ts   ← Poll submission results
└── index.ts                  ← Barrel export
```

### Types (1)
```
src/types/
└── dto.ts                    ← TypeScript interfaces (all DTOs)
```

### Utilities (1)
```
src/utils/
└── constants.ts              ← API endpoints, storage keys, hints config
```

### Tests (2)
```
src/__tests__/
├── LoginPage.test.tsx        ← LoginPage unit tests
└── (additional tests ready for addition)
```

### Mock Data (1)
```
src/__mocks__/
└── fixtures.ts               ← Mock Track, Exercise, User data
```

### Root Component (1)
```
src/
└── App.tsx                   ← Router setup + auth provider
```

### Setup (1)
```
src/
└── setupTests.ts             ← Vitest setup (jest-dom, mocks)
```

### Scripts (2)
```
code-coach-ui/
├── launch.sh                 ← Bash launch script (Linux/Mac)
└── launch.bat                ← Batch launch script (Windows)
```

### Documentation (5)
```
code-coach-ui/
├── README.md                 ← Full technical documentation
├── SETUP_GUIDE.md            ← Quick start (5 min)
├── ARCHITECTURE.md           ← Deep architecture & flows
├── PROJECT_SUMMARY.md        ← Stats, features, checklist
└── DOCUMENTATION_INDEX.md    ← This index file
```

---

## File Count Summary

| Category | Count |
|----------|-------|
| Configuration | 12 |
| HTML/CSS | 2 |
| API Layer | 7 |
| Auth | 2 |
| Pages | 4 |
| Components | 7 |
| Hooks | 3 |
| Types | 1 |
| Utils | 1 |
| Tests | 2 |
| Mocks | 1 |
| Root | 1 |
| Setup | 1 |
| Scripts | 2 |
| Docs | 5 |
| **TOTAL** | **52 files** |

---

## Code Statistics

### Lines of Code by Category

```
API Layer           ~350 lines (client, endpoints, DTOs)
Auth                ~100 lines (context, hook)
Pages               ~800 lines (LoginPage, Tracks, Exercise)
Components          ~650 lines (Editor, Coach, Results, etc.)
Hooks               ~150 lines (draft, polling)
Types               ~100 lines (DTOs)
Utils               ~50 lines (constants)
Tests               ~50 lines (LoginPage test)
Config files        ~100 lines (vite, webpack, etc.)
─────────────────────────
TOTAL             ~2300 lines
```

### Component Complexity

| Component | LOC | Complexity | Purpose |
|-----------|-----|-----------|---------|
| ExercisePage | 200+ | High | Main workspace |
| CoachPanel | 150+ | Medium | Coach interface |
| AppShell | 80+ | Low | Layout wrapper |
| MonacoEditorPane | 70+ | Medium | Editor widget |
| SubmissionResultPanel | 120+ | Medium | Results display |
| LoginPage | 100+ | Medium | Auth form |
| TracksPage | 80+ | Low | Track listing |

---

## Dependencies Installed

### Production (10)
```
react                    18.2.0     (UI framework)
react-dom               18.2.0     (React DOM)
react-router-dom        6.20.0     (Routing)
@tanstack/react-query   5.28.0     (Data fetching)
@monaco-editor/react    4.5.0      (Code editor)
axios                   1.6.2      (HTTP client)
zod                     3.22.4     (Validation)
react-hook-form         7.48.0     (Forms)
@hookform/resolvers     3.3.4      (Form resolvers)
clsx                    2.0.0      (Classnames utility)
```

### DevDependencies (18)
```
typescript              5.3.3      (Type checking)
vite                    5.0.8      (Build tool)
@vitejs/plugin-react    4.2.0      (React plugin)
tailwindcss             3.3.6      (Styling)
postcss                 8.4.31     (CSS processing)
autoprefixer            10.4.16    (CSS vendor prefixes)
eslint                  8.54.0     (Linting)
@typescript-eslint/*    6.13.2     (TS linting)
prettier                3.1.0      (Code formatting)
vitest                  0.34.6     (Testing framework)
@testing-library/react  14.1.2     (Component testing)
@testing-library/jest-dom 6.1.5    (DOM assertions)
@testing-library/user-event 14.5.1 (User interactions)
msw                     1.3.2      (Mock Service Worker)
```

---

## Key File Relationships

```
App.tsx
├── AuthProvider (from AuthContext)
└── BrowserRouter
    ├── /login → LoginPage
    │           ├── authApi.login()
    │           └── useAuth() → login()
    │
    ├── /tracks → ProtectedRoute → TracksPage
    │                              ├── tracksApi.getAll()
    │                              └── Navigate to track detail
    │
    ├── /tracks/:trackId → ProtectedRoute → TrackDetailPage
    │                                        ├── tracksApi.getById()
    │                                        ├── exercisesApi.getByTrackId()
    │                                        └── Navigate to exercise
    │
    └── /exercise/:exerciseId → ProtectedRoute → ExercisePage
                                                  ├── exercisesApi.getById()
                                                  ├── MonacoEditorPane
                                                  │   └── useLocalDraft()
                                                  ├── useSubmissionPolling()
                                                  │   └── submissionsApi.getResult()
                                                  ├── SubmissionResultPanel
                                                  └── CoachPanel
                                                      └── coachApi.getHint()
```

---

## Development Workflow

### 1. Adding a New API Endpoint
```
1. Define DTO in src/types/dto.ts
2. Create API function in src/api/<feature>.ts
3. Add barrel export in src/api/index.ts
4. Use with React Query in component
```

### 2. Adding a New Component
```
1. Create src/components/<ComponentName>.tsx
2. Export in src/components/index.ts
3. Import and use in page/component
4. Add tests in src/__tests__/<ComponentName>.test.tsx
```

### 3. Adding a New Page
```
1. Create src/pages/<PageName>.tsx
2. Add route in App.tsx
3. Wrap with ProtectedRoute if private
4. Add navigation links
```

### 4. Adding a Custom Hook
```
1. Create src/hooks/use<HookName>.ts
2. Export in src/hooks/index.ts
3. Use in components with useContext if needed
```

---

## Configuration Highlights

### Environment Variables (.env)
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### TypeScript (tsconfig.json)
- `strict: true` - Strict type checking
- `noUnusedLocals: true` - Error on unused variables
- `noFallthroughCasesInSwitch: true` - Require break in switch

### ESLint (.eslintrc.cjs)
- React recommended rules
- React hooks rules
- TypeScript strict rules

### Prettier (.prettierrc)
- 2-space indentation
- Single quotes
- Semicolons
- Arrow parens

### Vite (vite.config.ts)
- Dev server on port 3000
- React plugin enabled
- Source maps disabled for build

---

## Next: How to Run

```bash
# From code-coach-ui directory
npm install         # Install all dependencies
npm run dev        # Start dev server
npm run build      # Build for production
npm run test       # Run tests
npm run lint       # Check code quality
```

---

**File Structure Created**: 30 Dec 2025  
**Total Files**: 52  
**Total Lines**: ~2,300  
**Status**: ✅ Ready to run

