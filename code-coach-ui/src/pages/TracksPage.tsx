/**
 * Tracks Page - Liste les pistes d'apprentissage
 */

import React from 'react';
import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { tracksApi } from '../api/tracks';
import { AppShell } from '../components/AppShell';

export const TracksPage: React.FC = () => {
  const { data: tracks = [], isLoading, error } = useQuery({
    queryKey: ['tracks'],
    queryFn: () => tracksApi.getAll(),
  });

  return (
    <AppShell>
      <div className="max-w-7xl mx-auto px-4 py-12">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">Pistes d'apprentissage</h1>
          <p className="text-gray-600">Choisissez une piste pour commencer</p>
        </div>

        {isLoading && (
          <div className="text-center py-12">
            <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
          </div>
        )}

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-6 py-4 rounded-lg">
            Erreur lors du chargement des pistes
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {tracks.map((track) => (
            <Link
              key={track.id}
              to={`/tracks/${track.id}`}
              className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow overflow-hidden"
            >
              <div className="p-6">
                <div className="flex justify-between items-start mb-2">
                  <h2 className="text-xl font-bold text-gray-900">{track.title}</h2>
                  <span className="text-2xl">📚</span>
                </div>
                <p className="text-gray-600 text-sm mb-4">{track.description}</p>
                <div className="flex justify-between items-center">
                  <span className="text-xs font-medium text-gray-500 uppercase">
                    {track.language}
                  </span>
                  <span className="text-sm text-blue-500 font-medium">
                    {track.exerciseCount || 0} exercices →
                  </span>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </AppShell>
  );
};
