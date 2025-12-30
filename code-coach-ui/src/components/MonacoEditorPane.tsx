/**
 * Monaco Editor Pane - Éditeur de code
 */

import React from 'react';
import Editor from '@monaco-editor/react';

interface MonacoEditorPaneProps {
  code: string;
  onChange: (code: string) => void;
  language: string;
  isReadOnly?: boolean;
}

export const MonacoEditorPane: React.FC<MonacoEditorPaneProps> = ({
  code,
  onChange,
  language,
  isReadOnly = false,
}) => {
  const getLanguageMode = (lang: string): string => {
    const langMap: Record<string, string> = {
      JAVA: 'java',
      PYTHON: 'python',
      JAVASCRIPT: 'javascript',
      CPP: 'cpp',
    };
    return langMap[lang] || 'plaintext';
  };

  return (
    <div className="h-full flex flex-col bg-white rounded-lg border border-gray-200 overflow-hidden">
      <div className="bg-gray-100 border-b border-gray-200 px-4 py-2 flex justify-between items-center">
        <span className="text-sm font-medium text-gray-700">Code</span>
        <span className="text-xs text-gray-500">{language}</span>
      </div>
      <div className="flex-1 overflow-hidden">
        <Editor
          height="100%"
          defaultLanguage={getLanguageMode(language)}
          language={getLanguageMode(language)}
          value={code}
          onChange={(value) => onChange(value || '')}
          theme="vs-dark"
          options={{
            minimap: { enabled: false },
            fontSize: 14,
            fontFamily: "'Fira Code', 'Courier New', monospace",
            readOnly: isReadOnly,
            automaticLayout: true,
            tabSize: 2,
          }}
        />
      </div>
    </div>
  );
};
