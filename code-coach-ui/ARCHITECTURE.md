# 🎯 Code Coach Frontend - Architecture & Flows

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Web Browser (http://localhost:3000)       │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │             React App (Vite)                         │   │
│  ├──────────────────────────────────────────────────────┤   │
│  │                                                       │   │
│  │  1️⃣ App.tsx (Router)                               │   │
│  │     └─ BrowserRouter                                │   │
│  │        ├─ /login → LoginPage                        │   │
│  │        ├─ /tracks → ProtectedRoute → TracksPage    │   │
│  │        ├─ /exercise/:id → ExercisePage             │   │
│  │                                                       │   │
│  │  2️⃣ Auth Context                                   │   │
│  │     └─ AuthProvider                                 │   │
│  │        ├─ user state (localStorage)                │   │
│  │        ├─ token (localStorage)                      │   │
│  │        └─ useAuth() hook                            │   │
│  │                                                       │   │
│  │  3️⃣ API Layer (src/api/)                          │   │
│  │     ├─ client.ts (Axios + JWT interceptor)         │   │
│  │     ├─ auth.ts (login)                             │   │
│  │     ├─ exercises.ts (get exercises)                │   │
│  │     ├─ submissions.ts (submit code, poll result)   │   │
│  │     └─ coach.ts (request hints)                    │   │
│  │                                                       │   │
│  │  4️⃣ Custom Hooks                                  │   │
│  │     ├─ useLocalDraft (localStorage mgmt)           │   │
│  │     └─ useSubmissionPolling (poll results)         │   │
│  │                                                       │   │
│  │  5️⃣ Components                                     │   │
│  │     ├─ MonacoEditorPane                            │   │
│  │     ├─ SubmissionResultPanel                       │   │
│  │     ├─ CoachPanel                                  │   │
│  │     └─ ...                                         │   │
│  │                                                       │   │
│  └──────────────────────────────────────────────────────┘   │
│                          ↕ Axios (HTTP)                      │
│                                                               │
└─────────────────────────────────────────────────────────────┘
         │
         ↓ http://localhost:8080/api
┌──────────────────────────────────────────┐
│   Spring Boot Backend (Java)             │
├──────────────────────────────────────────┤
│ - JWT validation                         │
│ - Exercises, Tests                       │
│ - Code Runner (Docker)                   │
│ - Coach IA (hints)                       │
│ - PostgreSQL DB                          │
└──────────────────────────────────────────┘
```

---

## User Flows

### 🔐 Authentication Flow

```
User opens /login
      ↓
   [LoginPage]
      ↓
User fills email + password
      ↓
POST /auth/login {email, password}
      ↓
Backend validates, returns {accessToken, userId, username, role}
      ↓
Frontend:
  • Stores token in localStorage
  • Stores user in localStorage
  • Updates AuthContext
      ↓
Redirects to /tracks
```

### 🎯 Exercise Resolution Flow

```
User on /exercise/:exerciseId
      ↓
GET /exercises/{id}
      ↓
[ExercisePage renders]
  • Left: Problem + "My approach" textarea
  • Right top: Monaco Editor (auto-load from draft localStorage)
  • Right bottom: Results panel (empty)
  • Below: Coach panel (disabled until approach filled)
      ↓
User writes code + fills "My approach"
      ↓
Code auto-saved to localStorage (codecoach_draft_<id>)
      ↓
User clicks "Run Tests"
      ↓
POST /submissions {exerciseId, code, language}
      ↓
Backend returns {submissionId, status: "QUEUED"}
      ↓
Frontend starts polling...
      ↓
GET /submissions/{id}/result (every 1s)
      ↓
Status loop:
  • QUEUED → polling
  • RUNNING → polling
  • PASSED/FAILED/ERROR → stop polling, show results
      ↓
[Results Panel]
  • Status badge (✅ PASSED / ❌ FAILED)
  • Tests: 3/5 passed
  • Console logs (if any)
  • Compilation error (if any)
      ↓
User requests hint from Coach:
  • Clicks "Niveau 1" button
      ↓
POST /coach/hint {
  exerciseId,
  submissionId,
  hintLevel: 1,
  userApproach: "I think I need to...",
  code: "...",
  lastError: "... if any"
}
      ↓
Backend returns socratic hint
      ↓
[Coach Panel]
  • Adds user message: "Demande d'indice niveau 1"
  • Adds coach message: "Coach response..."
  • Chat history preserved
      ↓
User can request higher levels or refactor code
      ↓
Loop: Code → Run → Result → Hint → Code...
```

---

## Key Components & Responsibilities

### 1. `App.tsx`
**Role**: Route definitions + Auth wrapper

```typescript
<BrowserRouter>
  <AuthProvider>
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/tracks" element={
        <ProtectedRoute>
          <TracksPage />
        </ProtectedRoute>
      } />
      ...
    </Routes>
  </AuthProvider>
</BrowserRouter>
```

### 2. `AuthContext + useAuth`
**Role**: Global auth state (user, token, login/logout functions)

```typescript
const { user, token, isAuthenticated, login, logout } = useAuth();
```

Persists to localStorage automatically.

### 3. `api/client.ts`
**Role**: Axios instance with JWT interceptor

```typescript
// Request: adds Authorization header
client.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Response: handle 401 → logout
client.interceptors.response.use(..., (error) => {
  if (error.response?.status === 401) {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }
});
```

### 4. `MonacoEditorPane`
**Role**: Code editor widget

```typescript
<MonacoEditorPane
  code={code}
  onChange={saveDraft}  // Auto-saves to localStorage
  language={exercise.language}
/>
```

### 5. `useLocalDraft` Hook
**Role**: Auto-save/restore code from localStorage

```typescript
const { code, saveDraft, reset } = useLocalDraft(exerciseId, initialCode);

// Auto-loads from localStorage if exists
// saveDraft(newCode) updates both state + storage
// reset() clears storage and resets to initialCode
```

### 6. `SubmissionResultPanel`
**Role**: Display test results

```typescript
<SubmissionResultPanel
  result={lastResult}  // null | SubmissionResult
  isLoading={isPolling}  // Show spinner while polling
  error={errorMsg}  // API error message
/>

// Shows: status badge, tests passed/total, logs, errors
```

### 7. `useSubmissionPolling` Hook
**Role**: Poll `/submissions/{id}/result` until done

```typescript
useSubmissionPolling({
  submissionId: "sub-123",
  fetchResult: (id) => submissionsApi.getResult(id),
  onResult: (result) => setLastResult(result)
});

// Auto-polls every 1s, stops when status ≠ RUNNING
// Max 30 attempts (30s timeout)
```

### 8. `CoachPanel`
**Role**: Chat interface for hints

```typescript
<CoachPanel
  exerciseId="ex-1"
  submissionId="sub-123"  // Optional
  userApproach="My approach..."
  code="full code string"
  lastResult={submissionResult}  // For error context
/>

// Shows: message history, approach textarea, hint buttons
// Buttons disabled until approach filled
// Buttons only let you progress (L1 first, then L2, etc.)
```

---

## State Management Strategy

### Local State (useState)
- `code` - Code being edited
- `userApproach` - User's approach textarea
- `submissionId` - Current submission ID
- `lastResult` - Last test result
- `messages` - Coach chat history

### Global State (AuthContext)
- `user` - Logged-in user
- `token` - JWT token
- `isAuthenticated()` - Check if auth

### Browser Storage (localStorage)
- `codecoach_auth_token` - JWT
- `codecoach_user` - User JSON
- `codecoach_draft_<exerciseId>` - Code draft

### Server State (React Query)
- Caches GET requests (tracks, exercises)
- Auto-revalidation on window focus
- Stale-while-revalidate strategy

---

## Data Flow Example: User Submits Code

```
User clicks "Run Tests"
  ↓
[ExercisePage.tsx]
const submitMutation = useMutation({
  mutationFn: () => submissionsApi.submit({...code...}),
  onSuccess: (data) => setSubmissionId(data.submissionId)
})
submitMutation.mutate()  // Trigger
  ↓
[api/submissions.ts]
export const submissionsApi = {
  submit: async (data) => {
    const response = await apiClient.post('/submissions', data)
    // apiClient automatically adds Authorization header
    return response.data  // {submissionId, status: "QUEUED"}
  }
}
  ↓
[api/client.ts]
client.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${token}`  // From localStorage
  return config
})
  ↓
Network: POST http://localhost:8080/api/submissions
  Header: Authorization: Bearer eyJhbGc...
  Body: {exerciseId, code, language}
  ↓
[Backend Spring Boot]
POST /api/v1/submissions
  • Validate JWT
  • Create Submission entity
  • Queue to Kafka (async execution)
  • Return {submissionId, status: "QUEUED"}
  ↓
Frontend receives response
  ↓
[ExercisePage.tsx]
setSubmissionId("sub-123")
  ↓
[useSubmissionPolling hook]
Starts polling GET /submissions/sub-123/result every 1s
  ↓
[Loop: Backend processing]
/result returns {status: "RUNNING", ...}
  ↓
Poll again after 1s...
  ↓
/result returns {status: "PASSED", testsPassed: 3, testsTotal: 5, ...}
  ↓
[useSubmissionPolling]
Calls onResult(result)
  ↓
[ExercisePage.tsx]
setLastResult(result)
  ↓
[SubmissionResultPanel]
Re-renders with result data
  Shows: ✅ PASSED, 3/5 tests, logs, etc.
```

---

## Error Handling Strategy

### API Errors
```typescript
// Try-catch in async functions
catch (error) {
  if (error.response?.status === 401) {
    // Unauthorized → logout
    logout();
    navigate('/login');
  } else {
    // Show error to user
    setError(error.message);
  }
}
```

### Validation Errors
- Form validation: none (MVP uses basic HTML5)
- Ready for: Zod + React Hook Form (in dependencies)

### Networking Errors
- Axios auto-retries: not configured (future improvement)
- Manual retry: could add to polling hook
- User sees: "Error fetching results" message

### Timeout
- Polling timeout: 30s (max 30 attempts)
- Monaco timeout: none (local)
- Coach timeout: depends on backend

---

## Performance Optimizations (MVP)

✅ Already in place:
- React Query caching (GET requests)
- Lazy Monaco Editor load
- Tailwind CSS purge (build)
- localStorage for drafts (no network hit)

🔜 Future:
- Debounce code saves (currently every keystroke)
- Virtualize coach message history
- Service Worker (PWA)
- Code splitting per route

---

## Security Considerations

### JWT Token
- ✅ Stored in localStorage
- ✅ Sent in Authorization header
- ✅ Refreshed: not implemented (MVP)
- ⚠️ XSS vulnerability: localStorage is accessible to JS
  - Use: httpOnly cookie (backend change) for production
  - Or: In-memory token (lose on refresh)

### Code Submission
- ✅ Code sent with JWT (authenticated)
- ✅ Server validates exercise ownership
- ✅ testCode never exposed to client

### CORS
- ✅ Backend handles CORS (Spring Security)
- ✅ Frontend sends credentials if needed

---

## Testing Strategy

### Unit Tests
- Components: render, user interactions
- Hooks: state changes, localStorage
- API: request/response structure

### Integration Tests
- Auth flow: login → token → protected route
- Exercise flow: load → edit → submit → result

### E2E Tests (future)
- Cypress: full user journey

### Mock Data
- `/src/__mocks__/fixtures.ts` - Sample data
- MSW (Mock Service Worker) - API mocking (ready)

---

## Debugging Tips

### React DevTools
- Install: React DevTools Chrome extension
- Inspect component tree, state, props

### Redux DevTools
- Not used (Context API instead)
- Check Context in React DevTools

### Network Tab
- POST /submissions → check body + response
- GET /submissions/{id}/result → check polling requests
- Header: Authorization: Bearer...

### Console
```javascript
// Check token
localStorage.getItem('codecoach_auth_token')

// Check draft
localStorage.getItem('codecoach_draft_ex-1')

// Check React Query cache
queryClient.getQueryData(['exercises', 'ex-1'])

// Manual API call
fetch('http://localhost:8080/api/tracks', {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('codecoach_auth_token')}`
  }
}).then(r => r.json()).then(console.log)
```

---

**Last Updated**: 30 Dec 2025  
**Version**: 0.1.0-MVP

