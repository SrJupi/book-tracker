// eslint.config.js
import { defineConfig, globalIgnores } from 'eslint/config'
import js from '@eslint/js'
import tsPlugin from '@typescript-eslint/eslint-plugin'
import tsParser from '@typescript-eslint/parser'
import reactPlugin from 'eslint-plugin-react'
import reactHooksPlugin from 'eslint-plugin-react-hooks'
import importPlugin from 'eslint-plugin-import'
import prettier from 'eslint-config-prettier'
import globals from 'globals'

export default defineConfig([
  // Ignore build/output folders globally
  globalIgnores(['dist', 'node_modules']),

  {
    files: ['**/*.{ts,tsx,js,jsx}'], // Apply to all JS/TS files
    languageOptions: {
      parser: tsParser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
        ecmaFeatures: { jsx: true },
      },
      globals: globals.browser,
    },
    plugins: {
      react: reactPlugin,
      'react-hooks': reactHooksPlugin,
      '@typescript-eslint': tsPlugin,
      import: importPlugin,
    },
    settings: {
        react: { version: 'detect' },
        'import/resolver': {
            typescript: {}, // This allows ESLint to resolve TS paths and aliases
        },
    },
    rules: {
      // ESLint recommended rules
      ...js.configs.recommended.rules,
      // TypeScript plugin rules
      ...tsPlugin.configs.recommended.rules,
      // React plugin rules
      ...reactPlugin.configs.recommended.rules,
      // React Hooks plugin rules
      ...reactHooksPlugin.configs.recommended.rules,
      // Import plugin recommended rules
      ...importPlugin.configs.recommended.rules,
      // Prettier rules
      ...prettier.rules,

      // Custom overrides
      'react/react-in-jsx-scope': 'off',
    },
  },
])
