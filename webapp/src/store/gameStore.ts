import {create} from 'zustand'
import type {GameSessionResponse} from "../api/types.ts";

interface GameStore {
    playerName: string
    playerSymbol: 'X' | 'O' | null
    connectCode: string
    currentGame: GameSessionResponse | null

    setPlayerName: (name:string) => void
    setPlayerSymbol: (symbol: 'X' | 'O' | null) => void
    setConnectCode: (code: string) => void
    setCurrentGame: (game: GameSessionResponse | null) => void,
}

export const useGameStore = create<GameStore>((set) => ({
    // default vals
    playerName: '',
    connectCode: '',
    currentGame: null,
    playerSymbol: null,

    // setter implementations
    setPlayerName: (name) => set({playerName: name}),
    setPlayerSymbol: (symbol) => set({playerSymbol: symbol}),
    setConnectCode: (code) => set({connectCode: code}),
    setCurrentGame: (game) => set({currentGame: game}),
}))