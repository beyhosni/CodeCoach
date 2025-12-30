/**
 * Submissions API endpoints
 */

import { apiClient } from './client';
import { SubmissionRequest, SubmissionResponse, SubmissionResult } from '../types/dto';
import { API_ENDPOINTS } from '../utils/constants';

export const submissionsApi = {
  submit: async (data: SubmissionRequest): Promise<SubmissionResponse> => {
    const response = await apiClient.post<SubmissionResponse>(
      API_ENDPOINTS.SUBMIT,
      data
    );
    return response.data;
  },

  getResult: async (submissionId: string): Promise<SubmissionResult> => {
    const response = await apiClient.get<SubmissionResult>(
      API_ENDPOINTS.SUBMISSION_RESULT(submissionId)
    );
    return response.data;
  },
};
