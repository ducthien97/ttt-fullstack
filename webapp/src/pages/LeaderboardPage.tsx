import {useState, useEffect} from "react"
import {useNavigate} from "react-router-dom";
import {getLeaderboard} from "@/api/leaderboardApi.ts";
import type {LeaderboardEntry} from "@/api/types.ts";
import {Button} from "@/components/ui/button.tsx";


export default function LeaderboardPage() {
    const navigate = useNavigate();
    const [leaderboardEntries, setLeaderboardEntries] = useState<LeaderboardEntry[]>([]);

    useEffect(() => {
        const fetchLeaderboard = async () => {
            const entries = await getLeaderboard()
            setLeaderboardEntries(entries)
        }
        void fetchLeaderboard()
    }, [])

    const handleNewGame = () => {
        navigate('/')
    }

    return (
        <div className="min-h-screen flex flex-col items-center justify-center gap-6 bg-background">
            <div
                className="bg-card text-card-foreground p-8 rounded-xl border w-full max-w-sm flex flex-col gap-4 shadow-sm">
                <h1 className="text-2xl font-bold text-center">Leaderboard</h1>

                {leaderboardEntries.map((entry, index) => (
                    <div key={entry.playerName} className="flex justify-between items-center p-3 border-b">
                        <span className="font-bold">#{index + 1}</span>
                        <span>{entry.playerName}</span>
                        <span>{entry.winCount} wins</span>
                    </div>
                ))}

                <Button onClick={handleNewGame} className="w-full mt-4">
                    New Game
                </Button>
            </div>
        </div>
    )
}