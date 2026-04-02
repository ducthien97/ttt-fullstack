import {apiPost} from "./client.ts";
import type {PlayerResponse} from "./types.ts";

export async function createPlayer (playerName: string) : Promise<PlayerResponse> {
    return apiPost<PlayerResponse>('/api/players', {name: playerName})
}