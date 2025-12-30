/**
 * Exercises API endpoints
 */

import { apiClient } from './client';
import { Exercise } from '../types/dto';
import { API_ENDPOINTS } from '../utils/constants';

export const exercisesApi = {
  getByTrackId: async (trackId: string): Promise<Exercise[]> => {
    const response = await apiClient.get<Exercise[]>(API_ENDPOINTS.EXERCISES, {
      params: { trackId },
    });
    return response.data;
  },

  getById: async (id: string): Promise<Exercise> => {
    const response = await apiClient.get<Exercise>(API_ENDPOINTS.EXERCISE_DETAIL(id));
    return response.data;
  },
};
