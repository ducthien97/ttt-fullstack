import {apiGet} from "./client.ts";
import type {LeaderboardEntry} from "./types.ts";

export async function viewLeaderboard() : Promise<LeaderboardEntry[]> {
    return apiGet<LeaderboardEntry[]>('/api/leaderboard')
}