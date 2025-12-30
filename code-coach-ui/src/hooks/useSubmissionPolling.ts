/**
 * Hook pour le polling des résultats de soumission
 */

import { useEffect, useState, useRef } from 'react';
import { SubmissionResult } from '../types/dto';
import { SUBMISSION_POLLING_INTERVAL, SUBMISSION_POLLING_MAX_ATTEMPTS } from '../utils/constants';

interface UseSubmissionPollingProps {
  submissionId: string | null;
  onResult: (result: SubmissionResult) => void;
  fetchResult: (id: string) => Promise<SubmissionResult>;
}

export const useSubmissionPolling = ({
  submissionId,
  onResult,
  fetchResult,
}: UseSubmissionPollingProps) => {
  const [isPolling, setIsPolling] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const attemptsRef = useRef(0);

  useEffect(() => {
    if (!submissionId) {
      setIsPolling(false);
      return;
    }

    setIsPolling(true);
    attemptsRef.current = 0;
    setError(null);

    const pollInterval = setInterval(async () => {
      try {
        attemptsRef.current += 1;

        const result = await fetchResult(submissionId);

        if (result.status === 'RUNNING' || result.status === 'QUEUED') {
          // Continuer le polling
          if (attemptsRef.current >= SUBMISSION_POLLING_MAX_ATTEMPTS) {
            setError('Timeout: soumission en cours depuis trop longtemps');
            setIsPolling(false);
            clearInterval(pollInterval);
          }
        } else {
          // Résultat obtenu
          onResult(result);
          setIsPolling(false);
          clearInterval(pollInterval);
        }
      } catch (err) {
        console.error('Polling error:', err);
        // Continuer le polling en cas d'erreur temporaire
        if (attemptsRef.current >= SUBMISSION_POLLING_MAX_ATTEMPTS) {
          setError('Erreur lors du polling des résultats');
          setIsPolling(false);
          clearInterval(pollInterval);
        }
      }
    }, SUBMISSION_POLLING_INTERVAL);

    return () => clearInterval(pollInterval);
  }, [submissionId, onResult, fetchResult]);

  return { isPolling, error };
};
