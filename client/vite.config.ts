import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

/**
 * Vite Configuration
 * 
 * Vite is a fast build tool for modern web projects.
 * Much faster than Create React App's webpack.
 */
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,  // Frontend runs on this port
  },
});
