# TTT

## About
TTT (TicTacToe) is a full stack web application where a user can play the game of Tic Tac Toe, provided there is a connection with another user.

## Application requirements
+ User can create a new game board
+ Allow two (and only two) players to connect to a game board
+ Persist game state on the server
+ Follow standard rules for tic-tac-toe (or noughts and crosses)
+ Display the game result and persist in the database at the end of the game
+ Display a ranking of the top five players and allow players to start a new game

## Prerequisites
- Docker
- Docker Compose

## Quick Start
1. Clone the repository
2. `cp .env.example .env`
3. `docker compose up --build`
4. Open http://localhost:5173

## Database Schema
- **players** — stores player name and id
- **game_sessions** — stores board state, status, connect code, current turn and winner
- **game_players** — links players to game sessions with their assigned symbol (X or O)

## Architecture Decisions
- **HTTP Polling vs WebSockets:** Chose HTTP polling every 1 second over WebSockets for simplicity and reliability in a local development environment. For production at scale, WebSockets or SSE would be more appropriate given lower latency requirements.
- **Board storage:** Given that one of the requirement is "Follow standard rules of tic-tac-toe", board will be a 3x3. Board state is stored as a JSONB array of 9 integers in PostgreSQL (0=empty, 1=X, 2=O), keeping the schema simple while allowing flexible querying
- **Connect code:** 5-digit numeric code generated server-side to connect two players to the same game session

## Technologies
+ Frontend: React/TS
+ Backend: Spring Boot/Java
+ DB: PostgreSQL
+ Containerization: Docker
+ Frontend-Backend Communication Protocol: HTTP (REST API)

## API Endpoints
| Method | Path                   | Description               |
|--------|------------------------|---------------------------|
| POST   | /api/players           | Create or find a player   |
| POST   | /api/games             | Create a new game session |
| POST   | /api/games/{code}/join | Join an existing game     |
| GET    | /api/games/{code}      | Get current game state    |
| POST   | /api/games/{code}/move | Make a move               |
| GET    | /api/leaderboard       | Get top 5 players by wins |

## Time Log
| Day   | Activity                                                                                                                | Hours      |
|-------|-------------------------------------------------------------------------------------------------------------------------|------------|
| Day 1 | Receive requirements, request clarifications, brainstorm database schema, setup docker                                  | 2.5        |
| Day 2 | Scaffold applications (UI, backend)                                                                                     | 1          |
| Day 3 | Implementation for Backend Application (DB migration, DB entities, Repository layer, DTO layer, Controller layer)       | 4          |
| Day 4 | Implementation for UI Application (API connection layer, route, store, Home page, Game page), setup shacdn and tailwind | 4          |
| Day 5 | Finish Result page and Leaderboard Page, testing, final touchups, update documentation                                  | 3          |
| Total |                                                                                                                         | 14.5 hours |