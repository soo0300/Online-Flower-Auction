import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],

  // 파일 경로를 해석하는 방법 변경하기 위해 사용
  // ../../../ 와 같은 폴더 경로 축약
  resolve:  {
    alias: [{ find: "@", replacement: path.resolve(__dirname, "src")}]
  }
})
