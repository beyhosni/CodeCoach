/**
 * Auth API endpoints
 */

import { apiClient } from './client';
import { LoginRequest, AuthResponse } from '../types/dto';
import { API_ENDPOINTS } from '../utils/constants';

export const authApi = {
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>(API_ENDPOINTS.AUTH_LOGIN, data);
    return response.data;
  },

  logout: async (): Promise<void> => {
    await apiClient.post(API_ENDPOINTS.AUTH_LOGOUT);
  },

  register: async (data: {
    email: string;
    password: string;
    username: string;
  }): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>(
      API_ENDPOINTS.AUTH_REGISTER,
      data
    );
    return response.data;
  },
};
