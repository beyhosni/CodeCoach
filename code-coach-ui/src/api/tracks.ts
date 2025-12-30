/**
 * Tracks API endpoints
 */

import { apiClient } from './client';
import { Track } from '../types/dto';
import { API_ENDPOINTS } from '../utils/constants';

export const tracksApi = {
  getAll: async (): Promise<Track[]> => {
    const response = await apiClient.get<Track[]>(API_ENDPOINTS.TRACKS);
    return response.data;
  },

  getById: async (id: string): Promise<Track> => {
    const response = await apiClient.get<Track>(API_ENDPOINTS.TRACK_DETAIL(id));
    return response.data;
  },
};
