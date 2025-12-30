/**
 * App Component - Routing principal
 */

import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './auth/AuthContext';
import { ProtectedRoute } from './components/ProtectedRoute';
import { LoginPage } from './pages/LoginPage';
import { TracksPage } from './pages/TracksPage';
import { TrackDetailPage } from './pages/TrackDetailPage';
import { ExercisePage } from './pages/ExercisePage';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<LoginPage />} />

          {/* Protected routes */}
          <Route
            path="/tracks"
            element={
              <ProtectedRoute>
                <TracksPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/tracks/:trackId"
            element={
              <ProtectedRoute>
                <TrackDetailPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/exercise/:exerciseId"
            element={
              <ProtectedRoute>
                <ExercisePage />
              </ProtectedRoute>
            }
          />

          {/* Redirects */}
          <Route path="/" element={<Navigate to="/tracks" replace />} />
          <Route path="*" element={<Navigate to="/tracks" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
