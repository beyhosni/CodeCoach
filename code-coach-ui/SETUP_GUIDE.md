# 🚀 Code Coach Frontend - SETUP GUIDE

## Quick Start (5 minutes)

### 1️⃣ Prerequisites
- Node.js 18+ ✅
- Backend running at http://localhost:8080 ✅
- npm or yarn

### 2️⃣ Install & Launch

```bash
# Inside code-coach-ui/
npm install
npm run dev
```

✅ Opens at http://localhost:3000

---

## 📋 What You Get

### Pages
- ✅ `/login` - Login form with JWT
- ✅ `/tracks` - List of learning paths
- ✅ `/tracks/:trackId` - List exercises
- ✅ `/exercise/:exerciseId` - **Full workspace** with:
  - Monaco Editor (Java, Python, JS, C++)
  - Problem statement + your approach textarea
  - Submit tests button
  - Polling results (tests, logs, errors)
  - Coach IA panel with 4 hint levels

### Features
- ✅ JWT Authentication (token + localStorage)
- ✅ Auto-save code to localStorage
- ✅ Real-time test result polling (1s interval)
- ✅ Socratic Coach with hints:
  1. **Question** ❓ - Orienting question
  2. **Hint** 💡 - Conceptual clue
  3. **Pseudocode** 📝 - Partial code structure
  4. **Error Explanation** 🔍 - Debug help
- ✅ Responsive UI (Tailwind CSS)

---

## 🔧 Configuration

### Backend URL

Default: `http://localhost:8080/api`

If different, edit `.env`:
```env
VITE_API_BASE_URL=http://your-backend:8080/api
```

### Test Credentials

Use any user from your backend fixtures:
- Email: `alice@example.com`
- Password: `password`

(Or check `FIXTURES.sql` in backend)

---

## 📁 Project Structure

```
src/
├── api/           → API client (Axios + interceptors)
├── auth/          → Auth context + hooks
├── pages/         → Login, Tracks, Exercise pages
├── components/    → UI components (Editor, Coach, Results)
├── hooks/         → Custom hooks (draft, polling)
├── types/         → TypeScript DTOs
├── utils/         → Constants, endpoints
├── App.tsx        → Router setup
└── main.tsx       → Entry point
```

See **README.md** for detailed structure.

---

## 🧪 Development Commands

```bash
# Dev server (with HMR)
npm run dev

# Build for production
npm run build

# Preview build
npm run preview

# Run tests
npm run test

# Lint & format
npm run lint
npm run lint:fix

# Type check
npm run type-check
```

---

## ⚠️ Common Issues

### ❌ "Cannot POST /auth/login"
→ Backend not running. Start backend at http://localhost:8080

### ❌ "Token expired" / "401 Unauthorized"
→ Token invalid or session expired. Login again.

### ❌ Monaco Editor not loading
→ Ensure `@monaco-editor/react` installed: `npm install`

### ❌ Code not saving locally
→ Check browser DevTools → Application → localStorage
→ Should see `codecoach_draft_<exerciseId>` keys

---

## 🌐 API Endpoints Used

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/auth/login` | POST | Authenticate |
| `/tracks` | GET | List learning paths |
| `/exercises?trackId=X` | GET | List exercises |
| `/exercises/{id}` | GET | Exercise detail |
| `/submissions` | POST | Submit code |
| `/submissions/{id}/result` | GET | Get test results |
| `/coach/hint` | POST | Request hint |

All requests automatically include `Authorization: Bearer <token>` header.

---

## 📝 Key Features Explained

### Monaco Editor
- Language: Auto-detected from exercise (JAVA, PYTHON, etc.)
- Theme: Dark (`vs-dark`)
- Auto-save to localStorage every keystroke
- Reset button restores starter code

### Submission Flow
1. User clicks "Run Tests"
2. Frontend POST code to `/submissions`
3. Get `submissionId` back
4. Poll `/submissions/{id}/result` every 1 second
5. Wait for status ≠ RUNNING (or 30s timeout)
6. Display results: pass/fail, test count, logs, errors

### Coach Panel
- Reads user's "My approach" textarea
- Shows history of hints requested
- Buttons only enabled if approach is filled
- Coach receives: code + approach + last error
- Returns socratic hint (no full solution)

### Local Draft
- Code auto-saved to browser localStorage
- Key: `codecoach_draft_<exerciseId>`
- Survives page refresh
- Reset clears draft

---

## 🎯 Next Steps

### When Backend Ready
1. Verify backend `/auth/login` works with curl
2. Launch frontend: `npm run dev`
3. Login with test credentials
4. Navigate exercises
5. Submit code and check results
6. Request hints from Coach IA

### To Extend
- Add WebSocket for live feedback (instead of polling)
- Implement refresh tokens
- Add gamification UI (badges, streaks)
- Dark mode toggle
- Keyboard shortcuts (Ctrl+Enter = Run)

---

## 📖 Useful Docs

- [README.md](./README.md) - Full documentation
- [Vite Docs](https://vitejs.dev)
- [React Router](https://reactrouter.com)
- [TanStack Query](https://tanstack.com/query)
- [Monaco Editor](https://github.com/suren-atoyan/monaco-editor-react)
- [Tailwind CSS](https://tailwindcss.com)

---

## 🐛 Debug Mode

### See API calls
Open DevTools → Network tab while using the app

### Check localStorage
```javascript
// In DevTools console:
localStorage.getItem('codecoach_auth_token')
localStorage.getItem('codecoach_draft_ex-1')
```

### Enable verbose logging
Edit `src/utils/constants.ts`:
```typescript
export const DEBUG = true;
```

---

## 💡 Architecture Highlights

- **Modular**: Easy to add pages/components
- **Type-safe**: Full TypeScript coverage
- **State management**: React Context + React Query
- **API abstraction**: `/src/api/*` layer allows easy backend changes
- **Responsive**: Mobile-first Tailwind CSS
- **Tested**: Vitest + Testing Library ready

---

**Version**: 0.1.0-MVP  
**Last Updated**: 30 Dec 2025

