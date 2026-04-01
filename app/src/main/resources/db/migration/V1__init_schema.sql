CREATE TABLE players (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE game_sessions (
    id UUID PRIMARY KEY,
    status VARCHAR NOT NULL,
    board JSONB NOT NULL,
    connect_code VARCHAR(5) NOT NULL,
    current_turn UUID,
    FOREIGN KEY (current_turn) REFERENCES players(id),
    winner_id UUID,
    FOREIGN KEY (winner_id) REFERENCES players(id)
);

CREATE TABLE game_players (
    game_session_id UUID,
    FOREIGN KEY (game_session_id) REFERENCES game_sessions(id),
    player_id UUID,
    FOREIGN KEY (player_id) REFERENCES players(id),
    symbol VARCHAR(1) NOT NULL,
    PRIMARY KEY (game_session_id, player_id)
);
