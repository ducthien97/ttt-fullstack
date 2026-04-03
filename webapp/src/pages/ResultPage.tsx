import {useGameStore} from "@/store/gameStore.ts";
import {useNavigate} from "react-router-dom";
import {Button} from "@/components/ui/button.tsx";


export default function ResultPage() {
    const navigate = useNavigate();

    const {currentGame, resetGame} = useGameStore();

    if (!currentGame) {
        navigate('/')
        return null
    }

    const handleNewGame = () => {
        resetGame()
        navigate('/')
    }

    const handleViewLeaderboard = () => {
        navigate('/leaderboard')
    }

    return (
        <div className="min-h-screen flex flex-col items-center justify-center gap-6 bg-background">
            <div
                className="bg-card text-card-foreground p-8 rounded-xl border w-full max-w-sm flex flex-col gap-4 shadow-sm">

                {currentGame?.winnerName ? (
                    <div className="text-2xl font-bold text-center">
                        🎉 {currentGame.winnerName} wins!
                    </div>
                ) : (
                    <div className="text-2xl font-bold text-center">
                        It's a draw!
                    </div>
                )}

                <Button onClick={handleNewGame} className="w-full">
                    New Game
                </Button>
                <Button onClick={handleViewLeaderboard} variant="outline" className="w-full">
                    View Leaderboard
                </Button>
            </div>
        </div>
    )
}

