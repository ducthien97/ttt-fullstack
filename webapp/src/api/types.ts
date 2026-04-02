export interface PlayerResponse {
    id: string
    name: string
}

export interface LeaderboardEntry {
    playerName: string
    winCount: number
}

export interface GameSessionResponse {
    id: string
    status: string
    board: number[]
    currentTurnPlayerName: string
    winnerName: string
    connectCode: string
    playerSymbol: 'X' | 'O' | null
}