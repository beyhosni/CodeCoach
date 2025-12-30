/**
 * Exercise Page - Workspace complet avec Monaco, soumission, coach
 */

import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery, useMutation } from '@tanstack/react-query';
import { exercisesApi } from '../api/exercises';
import { submissionsApi } from '../api/submissions';
import { AppShell } from '../components/AppShell';
import { MonacoEditorPane } from '../components/MonacoEditorPane';
import { SubmissionResultPanel } from '../components/SubmissionResultPanel';
import { CoachPanel } from '../components/CoachPanel';
import { AutonomyScoreBadge } from '../components/AutonomyScoreBadge';
import { useLocalDraft } from '../hooks/useLocalDraft';
import { useSubmissionPolling } from '../hooks/useSubmissionPolling';
import { SubmissionResult } from '../types/dto';

export const ExercisePage: React.FC = () => {
  const { exerciseId } = useParams<{ exerciseId: string }>();
  const navigate = useNavigate();

  const [userApproach, setUserApproach] = useState('');
  const [submissionId, setSubmissionId] = useState<string | null>(null);
  const [lastResult, setLastResult] = useState<SubmissionResult | null>(null);

  if (!exerciseId) {
    return <div>Exercise ID manquant</div>;
  }

  // Charger l'exercice
  const { data: exercise, isLoading: exerciseLoading } = useQuery({
    queryKey: ['exercise', exerciseId],
    queryFn: () => exercisesApi.getById(exerciseId),
  });

  // Gestion du code local
  const { code, saveDraft, reset } = useLocalDraft(
    exerciseId,
    exercise?.starterCode || ''
  );

  // Mutation pour la soumission
  const submitMutation = useMutation({
    mutationFn: () =>
      submissionsApi.submit({
        exerciseId,
        code,
        language: exercise?.language || 'JAVA',
      }),
    onSuccess: (data) => {
      setSubmissionId(data.submissionId);
      setLastResult(null);
    },
  });

  // Polling des résultats
  const { isPolling } = useSubmissionPolling({
    submissionId,
    onResult: (result) => {
      setLastResult(result);
    },
    fetchResult: (id) => submissionsApi.getResult(id),
  });

  const handleSubmit = () => {
    if (!code.trim()) {
      alert('Veuillez écrire du code');
      return;
    }
    submitMutation.mutate();
  };

  if (exerciseLoading) {
    return (
      <AppShell>
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
        </div>
      </AppShell>
    );
  }

  return (
    <AppShell>
      <div className="max-w-7xl mx-auto px-4 py-8 h-[calc(100vh-200px)]">
        <button
          onClick={() => navigate(-1)}
          className="text-blue-500 hover:text-blue-600 mb-6 flex items-center gap-2 text-sm"
        >
          ← Retour
        </button>

        {/* Header */}
        <div className="mb-6 flex justify-between items-start">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">{exercise?.title}</h1>
            <p className="text-gray-600 mt-2">{exercise?.description}</p>
          </div>
          <AutonomyScoreBadge score={65} />
        </div>

        {/* Main layout: 2 columns */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 h-[calc(100%-120px)]">
          {/* Left column: Problem statement + My approach */}
          <div className="lg:col-span-1 space-y-6 overflow-y-auto">
            {/* Problem statement */}
            <div className="bg-white rounded-lg border border-gray-200 p-6">
              <h2 className="text-lg font-bold text-gray-900 mb-4">📌 Énoncé</h2>
              <div className="prose prose-sm max-w-none">
                <p className="text-gray-600 whitespace-pre-wrap">{exercise?.prompt}</p>
              </div>

              {exercise?.starterCode && (
                <div className="mt-4">
                  <p className="text-sm font-medium text-gray-700 mb-2">Code de départ:</p>
                  <pre className="bg-gray-100 p-3 rounded-lg text-xs overflow-x-auto text-gray-700">
                    {exercise.starterCode}
                  </pre>
                </div>
              )}
            </div>

            {/* User approach */}
            <div className="bg-white rounded-lg border border-gray-200 p-6">
              <h2 className="text-lg font-bold text-gray-900 mb-4">💭 Mon approche</h2>
              <textarea
                value={userApproach}
                onChange={(e) => setUserApproach(e.target.value)}
                placeholder="Décrivez comment vous envisagez de résoudre ce problème avant de demander un indice..."
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 resize-vertical min-h-32"
              />
              <p className="text-xs text-gray-500 mt-2">
                💡 Le coach prendra en compte votre approche pour vous donner des indices pertinents
              </p>
            </div>
          </div>

          {/* Right column: Editor + Results + Coach */}
          <div className="lg:col-span-2 space-y-6 flex flex-col">
            {/* Editor */}
            <div className="flex-1 min-h-0">
              <MonacoEditorPane
                code={code}
                onChange={saveDraft}
                language={exercise?.language || 'JAVA'}
              />
            </div>

            {/* Action buttons */}
            <div className="flex gap-2">
              <button
                onClick={handleSubmit}
                disabled={submitMutation.isPending || isPolling}
                className="flex-1 px-4 py-3 bg-green-500 hover:bg-green-600 disabled:bg-gray-400 text-white font-bold rounded-lg transition-colors"
              >
                {isPolling
                  ? '⏳ Exécution en cours...'
                  : submitMutation.isPending
                    ? 'Soumission...'
                    : '▶️ Run Tests'}
              </button>
              <button
                onClick={reset}
                className="px-4 py-3 bg-gray-300 hover:bg-gray-400 text-gray-900 font-bold rounded-lg transition-colors"
                title="Réinitialiser au code de départ"
              >
                🔄 Reset
              </button>
            </div>

            {/* Results panel */}
            <div className="flex-1 min-h-0">
              <SubmissionResultPanel
                result={lastResult}
                isLoading={isPolling}
                error={submitMutation.error instanceof Error ? submitMutation.error.message : undefined}
              />
            </div>
          </div>
        </div>

        {/* Coach panel: full width below */}
        <div className="mt-6 bg-white rounded-lg border border-gray-200 h-64">
          <CoachPanel
            exerciseId={exerciseId}
            submissionId={submissionId}
            userApproach={userApproach}
            code={code}
            lastResult={lastResult}
            isLoadingSubmission={submitMutation.isPending || isPolling}
          />
        </div>
      </div>
    </AppShell>
  );
};
