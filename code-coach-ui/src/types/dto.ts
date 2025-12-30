/**
 * API Data Transfer Objects (DTO)
 */

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  userId: string;
  username: string;
  role: 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
}

export interface Track {
  id: string;
  title: string;
  description: string;
  language: string;
  difficulty: 1 | 2 | 3 | 4 | 5;
  isPublished: boolean;
  exerciseCount?: number;
}

export interface Exercise {
  id: string;
  trackId: string;
  title: string;
  description: string;
  prompt: string;
  language: 'JAVA' | 'PYTHON' | 'JAVASCRIPT' | 'CPP';
  starterCode: string;
  difficulty: 1 | 2 | 3 | 4 | 5;
  createdAt: string;
}

export interface SubmissionRequest {
  exerciseId: string;
  code: string;
  language: string;
}

export interface SubmissionResponse {
  submissionId: string;
  status: 'QUEUED' | 'PENDING' | 'RUNNING' | 'PASSED' | 'FAILED' | 'ERROR';
}

export interface SubmissionResult {
  status: 'PASSED' | 'FAILED' | 'RUNNING' | 'ERROR';
  testsPassed: number;
  testsTotal: number;
  stdout?: string;
  stderr?: string;
  compileError?: string;
  executionTimeMs?: number;
}

export interface CoachHintRequest {
  exerciseId: string;
  submissionId?: string;
  hintLevel: 1 | 2 | 3 | 4;
  userApproach: string;
  code: string;
  lastError?: string;
}

export interface CoachHintResponse {
  message: string;
  type: 'QUESTION' | 'HINT' | 'PSEUDOCODE' | 'ERROR_EXPLANATION';
  hintLevel: 1 | 2 | 3 | 4;
}

export interface CoachMessage {
  id: string;
  type: 'USER' | 'COACH';
  hintLevel?: 1 | 2 | 3 | 4;
  content: string;
  timestamp: string;
}

export interface User {
  id: string;
  email: string;
  username: string;
  firstName?: string;
  lastName?: string;
  role: 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
}
