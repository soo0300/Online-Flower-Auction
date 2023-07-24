# React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type aware lint rules:

- Configure the top-level `parserOptions` property like this:

```js
   parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    project: ['./tsconfig.json', './tsconfig.node.json'],
    tsconfigRootDir: __dirname,
   },
```

- Replace `plugin:@typescript-eslint/recommended` to `plugin:@typescript-eslint/recommended-type-checked` or `plugin:@typescript-eslint/strict-type-checked`
- Optionally add `plugin:@typescript-eslint/stylistic-type-checked`
- Install [eslint-plugin-react](https://github.com/jsx-eslint/eslint-plugin-react) and add `plugin:react/recommended` & `plugin:react/jsx-runtime` to the `extends` list

# 프로젝트 설정

### vite 생성기

- npm create vite@latest
- project name: kkoch
- framework: React
- variant: TypeScript


### 추가설정
- npm i framer-motion : 애니메이션 라이브러리
- npm i react-router-dom : 라우터 추가
- react-anchor-link-smooth-scroll@1.0.12 : nav bar 이동 라이브러리
- @heroicons/react : tailwind에서 제공해주는 아이콘 라이브러리


### Dev Dependencies
- npm i -D @types/react-anchor-link-smooth-scroll@1.0.2 
- @types/node : path module 

### Tailwind css 적용하기
- npm install -D tailwindcss postcss autoprefixer
- npx tailwindcss init -p
- tailwind.config.js 안 내용 수정
    ```
    /** @type {import('tailwindcss').Config} */
    export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {},
    },
    plugins: [],
    }
    ```
- index.css import하기
    ```
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
    ```
- npm i -D prettier prettier-plugin-tailwindcss