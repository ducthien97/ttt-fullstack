export const GameStatus = {
    WAITING: 'WAITING',
    ACTIVE: 'ACTIVE',
    COMPLETE: 'COMPLETE',
} as const

export type GameStatus = typeof GameStatus[keyof typeof GameStatus]