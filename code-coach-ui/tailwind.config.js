/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        'coach-primary': '#3b82f6',
        'coach-dark': '#1f2937',
        'coach-light': '#f3f4f6',
      }
    },
  },
  plugins: [],
}
