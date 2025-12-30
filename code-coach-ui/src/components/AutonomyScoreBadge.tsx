/**
 * Autonomy Score Badge (Stub)
 */

import React from 'react';

interface AutonomyScoreBadgeProps {
  score?: number;
}

export const AutonomyScoreBadge: React.FC<AutonomyScoreBadgeProps> = ({ score = 0 }) => {
  return (
    <div className="inline-flex items-center gap-2 px-3 py-2 bg-purple-50 border border-purple-200 rounded-lg">
      <span className="text-2xl">🎯</span>
      <div>
        <p className="text-xs font-medium text-gray-600">Score d'autonomie</p>
        <p className="text-lg font-bold text-purple-700">{score}%</p>
      </div>
    </div>
  );
};
