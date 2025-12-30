# 📚 Code Coach Frontend - Documentation Index

## 🎯 Start Here

**New to the project?** → [SETUP_GUIDE.md](./SETUP_GUIDE.md) (5 min)

---

## 📖 Documentation Files

### 📋 Overview & Setup
- **[SETUP_GUIDE.md](./SETUP_GUIDE.md)** - Quick start (npm install + npm run dev)
- **[README.md](./README.md)** - Full documentation (pages, API, stack, development)
- **[PROJECT_SUMMARY.md](./PROJECT_SUMMARY.md)** - Stats, checklist, feature overview

### 🏗️ Architecture & Design
- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Deep dive (flows, components, state management)

### 📁 Project Structure
```
code-coach-ui/
├── src/
│   ├── api/              API clients (Axios + endpoints)
│   ├── auth/             Auth context + hook
│   ├── pages/            Pages (Login, Tracks, Exercise)
│   ├── components/       Reusable components (Editor, Coach, etc.)
│   ├── hooks/            Custom hooks (draft, polling)
│   ├── types/            TypeScript DTOs
│   ├── utils/            Constants, helpers
│   ├── __tests__/        Tests
│   ├── __mocks__/        Mock data
│   ├── App.tsx           Router
│   ├── main.tsx          Entry point
│   └── index.css         Tailwind globals
├── index.html            HTML entry
├── package.json          Dependencies
├── tsconfig.json         TypeScript config
├── vite.config.ts        Vite config
├── vitest.config.ts      Tests config
└── README.md             Quick reference
```

---

## 🚀 Quick Commands

```bash
# Install & start
npm install
npm run dev

# Build for production
npm run build

# Tests
npm run test

# Lint & format
npm run lint
npm run lint:fix
```

---

## 📱 Pages Map

| Path | Component | Purpose |
|------|-----------|---------|
| `/login` | LoginPage | JWT authentication |
| `/tracks` | TracksPage | List learning paths |
| `/tracks/:trackId` | TrackDetailPage | List exercises |
| `/exercise/:exerciseId` | ExercisePage | **Main workspace** |

---

## 🎯 Key Features

### ✅ Core Features Implemented
- JWT authentication (token storage + localStorage)
- Protected routes (ProtectedRoute wrapper)
- Code editor (Monaco Editor integration)
- Local draft persistence (localStorage)
- Submission polling (every 1s, 30s max)
- Test results display (pass/fail, logs, errors)
- Coach IA with 4 hint levels
- Responsive design (Tailwind CSS)

### 🟢 Ready to Use
- API client layer (easy backend changes)
- Type-safe DTOs (TypeScript)
- Custom hooks (reusable logic)
- Error handling
- Loading states

---

## 🔗 Important Integrations

### Frontend → Backend
- **Base URL**: `http://localhost:8080/api` (configurable in `.env`)
- **Auth**: JWT tokens in Authorization header
- **Polling**: Every 1s for submission results

### Frontend Dependencies
- React 18, TypeScript 5
- Vite 5 (build)
- Tailwind CSS (styling)
- Monaco Editor (code editor)
- React Query (data caching)
- Axios (HTTP)

---

## 🧪 Testing

```bash
# Run tests in watch mode
npm run test

# UI dashboard
npm run test:ui

# Run specific test
npm run test -- LoginPage.test.tsx
```

**Test files**: `src/__tests__/`
**Fixtures**: `src/__mocks__/fixtures.ts`

---

## 🎨 UI Components

### Main Components (`src/components/`)
- **AppShell** - Layout wrapper (navbar + footer)
- **ProtectedRoute** - Route guard with auth check
- **MonacoEditorPane** - Code editor widget
- **SubmissionResultPanel** - Test results display
- **CoachPanel** - Coach IA chat interface
- **AutonomyScoreBadge** - Score display (stub)

### Pages (`src/pages/`)
- **LoginPage** - Email/password form
- **TracksPage** - Grid of learning paths
- **TrackDetailPage** - List of exercises
- **ExercisePage** - **Complete workspace**

---

## 🔐 Authentication Flow

```
1. User fills login form
2. POST /auth/login {email, password}
3. Backend returns {token, user}
4. Frontend stores in localStorage
5. Updates AuthContext
6. Redirects to /tracks
7. All future requests include: Authorization: Bearer <token>
```

**Auto-logout**: If 401 response, clears storage + redirects to /login

---

## 💾 Data Persistence

| Data | Storage | Lifetime |
|------|---------|----------|
| JWT Token | localStorage | Until logout |
| User info | localStorage | Until logout |
| Code draft | localStorage | Until reset |
| Session data | RAM (Context) | Until refresh |

---

## 🌐 API Endpoints Used

```
POST   /auth/login
GET    /tracks
GET    /exercises?trackId=...
GET    /exercises/{id}
POST   /submissions {code}
GET    /submissions/{id}/result
POST   /coach/hint {approach, code, hintLevel}
```

All require `Authorization: Bearer <token>` header (except login).

---

## 🐛 Debugging

### Check Token
```javascript
// DevTools Console
localStorage.getItem('codecoach_auth_token')
```

### Check Draft
```javascript
// DevTools Console
localStorage.getItem('codecoach_draft_ex-1')
```

### View Network Requests
- DevTools → Network tab
- See requests to http://localhost:8080/api

### Component State
- Install React DevTools extension
- Inspect component tree and props

---

## 📊 Project Stats

| Metric | Value |
|--------|-------|
| Pages | 4 |
| Components | 6 |
| Custom Hooks | 2 |
| API Endpoints | 7 |
| Lines of Code | ~3000+ |
| Bundle Size | ~500KB (~150KB gzipped) |
| Test Files | 1+ |

---

## 🎯 Features by Page

### `/login`
- Email + password form
- Error display
- Loading state
- Success redirect to /tracks

### `/tracks`
- Grid of track cards
- Track title + description
- Language badge
- Exercise count
- Click to track detail

### `/tracks/:trackId`
- Track header
- List of exercises
- Exercise number (#1, #2, etc.)
- Title + description
- Click to exercise workspace

### `/exercise/:exerciseId` ⭐ (Main)
- **Left panel (30%)**:
  - Problem statement
  - Starter code
  - "My approach" textarea
  
- **Right panel (70%)**:
  - Monaco Editor with code
  - Run Tests button + Reset button
  - Results panel (status, tests, logs)
  
- **Bottom**:
  - Coach IA panel (chat + hints)

---

## 🚀 Getting Started

### 1. Install
```bash
cd code-coach-ui
npm install
```

### 2. Configure (if needed)
```bash
# Edit .env if backend is not at localhost:8080
VITE_API_BASE_URL=http://your-backend:8080/api
```

### 3. Run
```bash
npm run dev
```

### 4. Open
```
http://localhost:3000/login
```

### 5. Login
Use test credentials from backend

---

## 📚 Recommended Reading Order

1. **[SETUP_GUIDE.md](./SETUP_GUIDE.md)** - Get it running (5 min)
2. **[README.md](./README.md)** - Understand the pages (15 min)
3. **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Deep dive (30 min)
4. **[PROJECT_SUMMARY.md](./PROJECT_SUMMARY.md)** - Review stats (10 min)

---

## 🤝 Contributing

- Keep types strict (TypeScript strict mode)
- Follow ESLint rules (run `npm run lint`)
- Test new components
- Update docs

---

## 🔄 Version History

| Version | Date | Status |
|---------|------|--------|
| 0.1.0-MVP | 30 Dec 2025 | ✅ Complete |

---

## 📞 Quick Help

**Can't login?**
- Backend running at http://localhost:8080? ✅
- Try credentials from backend FIXTURES.sql

**Monaco Editor not showing?**
- Check browser console for errors
- Ensure `@monaco-editor/react` installed

**Code not saving?**
- Check localStorage in DevTools
- Browser storage might be disabled

**Submission polling stuck?**
- Check backend is executing submissions
- Network tab should show GET requests every 1s

---

**Last Updated**: 30 Dec 2025  
**Status**: ✅ MVP Ready  
**Version**: 0.1.0

