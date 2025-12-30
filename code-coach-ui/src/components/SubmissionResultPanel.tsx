/**
 * Submission Result Panel - Affiche les résultats des tests
 */

import React, { useState } from 'react';
import { SubmissionResult } from '../types/dto';

interface SubmissionResultPanelProps {
  result: SubmissionResult | null;
  isLoading: boolean;
  error?: string;
}

export const SubmissionResultPanel: React.FC<SubmissionResultPanelProps> = ({
  result,
  isLoading,
  error,
}) => {
  const [showConsole, setShowConsole] = useState(true);

  if (isLoading) {
    return (
      <div className="bg-white rounded-lg border border-gray-200 p-6 text-center">
        <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
        <p className="text-gray-600 mt-2">Exécution en cours...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
          {error}
        </div>
      </div>
    );
  }

  if (!result) {
    return (
      <div className="bg-white rounded-lg border border-gray-200 p-6 text-center text-gray-500">
        Soumettez du code pour voir les résultats
      </div>
    );
  }

  const isPassed = result.status === 'PASSED';
  const statusColor = isPassed ? 'green' : result.status === 'FAILED' ? 'red' : 'yellow';
  const statusIcon = isPassed ? '✅' : result.status === 'FAILED' ? '❌' : '⏳';

  return (
    <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
      <div className="bg-gray-100 border-b border-gray-200 px-4 py-3">
        <h3 className="font-bold text-gray-900 flex items-center gap-2">
          {statusIcon}
          <span>Résultats des tests</span>
        </h3>
      </div>

      <div className="p-6">
        {/* Status Badge */}
        <div className="mb-6">
          <span
            className={`inline-block px-3 py-1 rounded-full text-sm font-bold text-white bg-${statusColor}-500`}
          >
            {result.status === 'PASSED'
              ? 'SUCCÈS'
              : result.status === 'FAILED'
                ? 'ÉCHOUÉ'
                : 'EN COURS'}
          </span>
        </div>

        {/* Tests stats */}
        <div className="bg-gray-50 rounded-lg p-4 mb-6">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <p className="text-gray-600 text-sm">Tests réussis</p>
              <p className="text-2xl font-bold text-green-600">{result.testsPassed}</p>
            </div>
            <div>
              <p className="text-gray-600 text-sm">Total</p>
              <p className="text-2xl font-bold text-gray-900">{result.testsTotal}</p>
            </div>
          </div>
        </div>

        {/* Compilation error */}
        {result.compileError && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-4">
            <p className="text-red-700 text-sm font-bold mb-2">Erreur de compilation:</p>
            <pre className="text-red-600 text-xs whitespace-pre-wrap font-mono">
              {result.compileError}
            </pre>
          </div>
        )}

        {/* Console logs */}
        {(result.stdout || result.stderr) && (
          <div>
            <button
              onClick={() => setShowConsole(!showConsole)}
              className="text-blue-500 hover:text-blue-600 text-sm font-medium mb-2 flex items-center gap-1"
            >
              {showConsole ? '▼' : '▶'} Console
            </button>

            {showConsole && (
              <div className="bg-gray-900 text-gray-100 p-4 rounded-lg font-mono text-xs max-h-48 overflow-auto">
                {result.stdout && (
                  <>
                    <p className="text-gray-400">STDOUT:</p>
                    <p>{result.stdout}</p>
                  </>
                )}
                {result.stderr && (
                  <>
                    <p className="text-red-400">STDERR:</p>
                    <p className="text-red-200">{result.stderr}</p>
                  </>
                )}
              </div>
            )}
          </div>
        )}

        {/* Execution time */}
        {result.executionTimeMs && (
          <div className="mt-4 text-xs text-gray-500">
            Temps d'exécution: {result.executionTimeMs}ms
          </div>
        )}
      </div>
    </div>
  );
};
