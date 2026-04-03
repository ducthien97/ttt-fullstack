# TTT Web App

## Overview
This frontend application allows users to play tic-tac-toe game in standard rules, users can create or join an existing game and compete with each others.
Players connect to the same game using a 5-digit code generated when a game is created.
Also users can see who's the current leaders with the most wins. 

## Tech Stack
- React — Main application logic
- TypeScript — Type-safe programming language
- Vite — Fast development server and build tool
- shadcn/ui — Lightweight UI component library
- Tailwind — Utility-first styling framework
- Zustand — Lightweight state management

## Architecture Decisions
- **Zustand vs Redux:** Zustand is more lightweight and modern, suitable for this project's complexity
- **React Router vs Single Page:** Gives clear routes for different pages since they serve different purposes, easier to separate components by page
- **HTTP Polling vs WebSockets:** Chose polling every 1 second over WebSockets for simplicity and reliability in a local development environment. For production at scale, WebSockets or SSE would provide lower latency.

## Project Structure
```
src/
├── api/          # Backend API interaction, separated by resource
├── components/   # Reusable UI components (shadcn/ui)
├── pages/        # Application pages
│   ├── HomePage.tsx
│   ├── GamePage.tsx
│   ├── ResultPage.tsx
│   └── LeaderboardPage.tsx
├── store/        # Zustand global state
└── assets/       # Static assets
```

## Running Locally

Note: ensure the backend is running first (see /app README)

npm install
npm run dev

Open http://localhost:5173 or http://localhost:5174