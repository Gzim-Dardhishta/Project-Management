module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs'],
  parser: '@typescript-eslint/parser',
  plugins: ['react-refresh'],
  rules: {
    "jsx-a11y/alt-text": "off",
        "react-hooks/exhaustive-deps": "off",
        "indent": ["error", 4],
        "linebreak-style": "off",
        "quotes": ["error", "single"],
        "semi": ["error", "never"],
        "comma-dangle": ["error", "never"],
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
  },
}
