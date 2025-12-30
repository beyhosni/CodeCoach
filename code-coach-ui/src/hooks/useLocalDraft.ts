/**
 * Hook pour la gestion du draft local (localStorage)
 */

import { useCallback, useEffect, useState } from 'react';
import { STORAGE_KEYS } from '../utils/constants';

export const useLocalDraft = (exerciseId: string, initialCode: string) => {
  const [code, setCode] = useState(initialCode);

  const storageKey = `${STORAGE_KEYS.DRAFT_PREFIX}${exerciseId}`;

  // Charger le draft depuis localStorage au montage
  useEffect(() => {
    const savedDraft = localStorage.getItem(storageKey);
    if (savedDraft) {
      setCode(savedDraft);
    } else {
      setCode(initialCode);
    }
  }, [exerciseId, initialCode, storageKey]);

  // Sauvegarder le draft
  const saveDraft = useCallback(
    (newCode: string) => {
      setCode(newCode);
      localStorage.setItem(storageKey, newCode);
    },
    [storageKey]
  );

  // Réinitialiser au code initial
  const reset = useCallback(() => {
    localStorage.removeItem(storageKey);
    setCode(initialCode);
  }, [initialCode, storageKey]);

  return { code, saveDraft, reset };
};
