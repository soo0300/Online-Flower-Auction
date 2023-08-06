import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    // @ => src/ 하위 폴더경로로 잡음
    alias: [{ find: "@", replacement: path.resolve(__dirname, "src")}]
  },
  server: {
    proxy: {
      '/api': {
        target: 'https://i9c204.p.ssafy.io',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    },
  },
})
