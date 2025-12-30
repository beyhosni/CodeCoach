/**
 * Coach Panel - Chat avec le coach IA
 */

import React, { useState, useRef, useEffect } from 'react';
import { useMutation } from '@tanstack/react-query';
import { coachApi } from '../api/coach';
import { CoachMessage as CoachMessageType, SubmissionResult } from '../types/dto';
import { HINT_LEVELS } from '../utils/constants';

interface CoachPanelProps {
  exerciseId: string;
  submissionId?: string;
  userApproach: string;
  code: string;
  lastResult?: SubmissionResult;
  isLoadingSubmission?: boolean;
}

export const CoachPanel: React.FC<CoachPanelProps> = ({
  exerciseId,
  submissionId,
  userApproach,
  code,
  lastResult,
  isLoadingSubmission = false,
}) => {
  const [messages, setMessages] = useState<CoachMessageType[]>([]);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const getHintMutation = useMutation({
    mutationFn: async (hintLevel: number) => {
      return coachApi.getHint({
        exerciseId,
        submissionId,
        hintLevel: hintLevel as 1 | 2 | 3 | 4,
        userApproach,
        code,
        lastError: lastResult?.stderr || lastResult?.compileError,
      });
    },
    onSuccess: (data) => {
      setMessages((prev) => [
        ...prev,
        {
          id: `coach-${Date.now()}`,
          type: 'COACH',
          content: data.message,
          hintLevel: data.hintLevel,
          timestamp: new Date().toISOString(),
        },
      ]);
    },
    onError: () => {
      alert('Erreur lors de la récupération de l\'indice');
    },
  });

  const handleRequestHint = (level: 1 | 2 | 3 | 4) => {
    // Ajouter le message utilisateur
    setMessages((prev) => [
      ...prev,
      {
        id: `user-${Date.now()}`,
        type: 'USER',
        content: `Demande d'indice niveau ${level}`,
        timestamp: new Date().toISOString(),
      },
    ]);

    // Envoyer la requête
    getHintMutation.mutate(level);
  };

  const canRequestHint = userApproach.trim().length > 0 && !isLoadingSubmission;

  return (
    <div className="bg-white rounded-lg border border-gray-200 h-full flex flex-col">
      <div className="bg-gray-100 border-b border-gray-200 px-4 py-3">
        <h3 className="font-bold text-gray-900 flex items-center gap-2">
          🤖 Coach IA Socratique
        </h3>
      </div>

      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {messages.length === 0 ? (
          <div className="text-center text-gray-500 py-8">
            <p className="mb-2">👋 Bienvenue!</p>
            <p className="text-sm">Décrivez votre approche ci-dessous et demandez un indice</p>
          </div>
        ) : (
          messages.map((msg) => (
            <div
              key={msg.id}
              className={`flex ${msg.type === 'USER' ? 'justify-end' : 'justify-start'}`}
            >
              <div
                className={`max-w-xs rounded-lg px-4 py-2 text-sm ${
                  msg.type === 'USER'
                    ? 'bg-blue-500 text-white'
                    : 'bg-gray-100 text-gray-900 border border-gray-200'
                }`}
              >
                {msg.hintLevel && msg.type === 'COACH' && (
                  <p className="text-xs font-bold mb-1 opacity-75">
                    {HINT_LEVELS[msg.hintLevel - 1]?.label}
                  </p>
                )}
                <p>{msg.content}</p>
              </div>
            </div>
          ))
        )}
        <div ref={messagesEndRef} />
      </div>

      {/* User approach input */}
      <div className="border-t border-gray-200 p-4 space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            📝 Mon approche (avant de demander un indice)
          </label>
          <textarea
            readOnly
            value={userApproach}
            className="w-full px-3 py-2 border border-gray-300 rounded-lg bg-gray-50 text-sm text-gray-600 h-20 resize-none"
            placeholder="Décrivez votre approche pour résoudre ce problème..."
          />
          <p className="text-xs text-gray-500 mt-1">
            Remplissez le formulaire sur la gauche
          </p>
        </div>

        {/* Hint level buttons */}
        <div className="space-y-2">
          <p className="text-sm font-medium text-gray-700">Demander un indice:</p>
          <div className="grid grid-cols-2 gap-2">
            {HINT_LEVELS.map((hint) => (
              <button
                key={hint.level}
                onClick={() => handleRequestHint(hint.level as 1 | 2 | 3 | 4)}
                disabled={
                  !canRequestHint ||
                  getHintMutation.isPending ||
                  (messages.some((m) => m.hintLevel === hint.level && m.type === 'COACH') &&
                    hint.level < 4)
                }
                className="px-3 py-2 text-xs font-medium rounded-lg bg-blue-50 text-blue-700 border border-blue-200 hover:bg-blue-100 disabled:opacity-50 disabled:cursor-not-allowed disabled:bg-gray-100 disabled:text-gray-500 disabled:border-gray-200 transition-colors"
              >
                {hint.emoji} Niveau {hint.level}
              </button>
            ))}
          </div>
        </div>

        {!canRequestHint && (
          <p className="text-xs text-yellow-600 bg-yellow-50 px-3 py-2 rounded-lg border border-yellow-200">
            ⚠️ Décrivez votre approche pour obtenir un indice
          </p>
        )}
      </div>
    </div>
  );
};
