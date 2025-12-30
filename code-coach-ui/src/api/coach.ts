/**
 * Coach IA API endpoints
 */

import { apiClient } from './client';
import { CoachHintRequest, CoachHintResponse } from '../types/dto';
import { API_ENDPOINTS } from '../utils/constants';

export const coachApi = {
  getHint: async (data: CoachHintRequest): Promise<CoachHintResponse> => {
    const response = await apiClient.post<CoachHintResponse>(
      API_ENDPOINTS.COACH_HINT,
      data
    );
    return response.data;
  },
};
