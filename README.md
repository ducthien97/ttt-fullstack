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

## Technologies
+ Frontend: React/TS
+ Backend: Spring Boot/Java
+ DB: PostgreSQL
+ Containerization: Docker
+ Frontend-Backend Communication Protocol: chose HTTP polling over WebSockets for simplicity and reliability in a local development environment. For a production deployment with concurrent games at scale, WebSockets or SSE would be more appropriate given lower latency requirements.

## Important
+ docker credentials are to be stored in .env run `cp .env.example .env` before running `docker compose up` 