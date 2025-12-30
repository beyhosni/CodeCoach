/**
 * API Configuration & Constants
 */

export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export const API_ENDPOINTS = {
  // Auth
  AUTH_LOGIN: '/auth/login',
  AUTH_LOGOUT: '/auth/logout',
  AUTH_REGISTER: '/auth/register',

  // Tracks
  TRACKS: '/tracks',
  TRACK_DETAIL: (id: string) => `/tracks/${id}`,

  // Exercises
  EXERCISES: '/exercises',
  EXERCISE_DETAIL: (id: string) => `/exercises/${id}`,

  // Submissions
  SUBMIT: '/submissions',
  SUBMISSION_RESULT: (id: string) => `/submissions/${id}/result`,

  // Coach
  COACH_HINT: '/coach/hint',
};

// Polling config
export const SUBMISSION_POLLING_INTERVAL = 1000; // 1s
export const SUBMISSION_POLLING_MAX_ATTEMPTS = 30; // 30s total

// Local storage keys
export const STORAGE_KEYS = {
  AUTH_TOKEN: 'codecoach_auth_token',
  USER: 'codecoach_user',
  DRAFT_PREFIX: 'codecoach_draft_',
};

// Coach hint levels
export const HINT_LEVELS = [
  { level: 1, label: 'Question orientante', emoji: '❓' },
  { level: 2, label: 'Indice conceptuel', emoji: '💡' },
  { level: 3, label: 'Pseudo-code partiel', emoji: '📝' },
  { level: 4, label: 'Explication erreur', emoji: '🔍' },
] as const;
