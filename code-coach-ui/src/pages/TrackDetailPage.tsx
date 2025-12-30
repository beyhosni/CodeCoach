/**
 * Track Detail Page - Liste les exercices d'une piste
 */

import React from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { exercisesApi } from '../api/exercises';
import { tracksApi } from '../api/tracks';
import { AppShell } from '../components/AppShell';

export const TrackDetailPage: React.FC = () => {
  const { trackId } = useParams<{ trackId: string }>();
  const navigate = useNavigate();

  if (!trackId) {
    return <div>Track ID manquant</div>;
  }

  const { data: track, isLoading: trackLoading } = useQuery({
    queryKey: ['track', trackId],
    queryFn: () => tracksApi.getById(trackId),
  });

  const { data: exercises = [], isLoading: exercisesLoading } = useQuery({
    queryKey: ['exercises', trackId],
    queryFn: () => exercisesApi.getByTrackId(trackId),
  });

  return (
    <AppShell>
      <div className="max-w-7xl mx-auto px-4 py-12">
        <button
          onClick={() => navigate('/tracks')}
          className="text-blue-500 hover:text-blue-600 mb-6 flex items-center gap-2"
        >
          ← Retour aux pistes
        </button>

        {trackLoading ? (
          <div className="text-center py-12">
            <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
          </div>
        ) : (
          <>
            <div className="mb-8">
              <h1 className="text-4xl font-bold text-gray-900 mb-2">{track?.title}</h1>
              <p className="text-gray-600">{track?.description}</p>
            </div>

            <h2 className="text-2xl font-bold text-gray-900 mb-6">Exercices</h2>

            {exercisesLoading ? (
              <div className="text-center py-12">
                <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
              </div>
            ) : exercises.length === 0 ? (
              <div className="bg-yellow-50 border border-yellow-200 text-yellow-700 px-6 py-4 rounded-lg">
                Aucun exercice disponible dans cette piste
              </div>
            ) : (
              <div className="space-y-4">
                {exercises.map((exercise, index) => (
                  <Link
                    key={exercise.id}
                    to={`/exercise/${exercise.id}`}
                    className="block bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6"
                  >
                    <div className="flex justify-between items-start">
                      <div className="flex-1">
                        <div className="flex items-center gap-3 mb-2">
                          <span className="bg-blue-100 text-blue-700 text-xs font-bold px-2 py-1 rounded">
                            #{index + 1}
                          </span>
                          <h3 className="text-lg font-bold text-gray-900">{exercise.title}</h3>
                        </div>
                        <p className="text-gray-600 text-sm">{exercise.description}</p>
                      </div>
                      <span className="text-2xl ml-4">💻</span>
                    </div>
                  </Link>
                ))}
              </div>
            )}
          </>
        )}
      </div>
    </AppShell>
  );
};
