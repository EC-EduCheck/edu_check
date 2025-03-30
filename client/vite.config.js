import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    rollupOptions: {
      external: [
        '@fullcalendar/resource-timegrid',
        '@fullcalendar/react',
        '@fullcalendar/interaction'
      ]
    }
  }
})
