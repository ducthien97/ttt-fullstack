import {apiGet, apiPost} from "./client.ts";
import type {GameSessionResponse} from "./types.ts";

export async function getGame (code: string) : Promise<GameSessionResponse> {
    return apiGet<GameSessionResponse>(`/api/games/${code}`);
}

export async function createGame (playerName: string): Promise<GameSessionResponse> {
    return apiPost<GameSessionResponse>('/api/games', {playerName});
}

export async function joinGame (playerName: string, code: string): Promise<GameSessionResponse> {
    return apiPost<GameSessionResponse>(`/api/games/${code}/join`, {playerName});
}

export async function makeMove (playerName: string, code: string, moveIndex: number): Promise<GameSessionResponse> {
    return apiPost<GameSessionResponse>(`/api/games/${code}/move`, {playerName, moveIndex});
}