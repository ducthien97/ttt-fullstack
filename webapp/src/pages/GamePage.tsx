import {useEffect} from "react";
import {getGame, makeMove} from "@/api/gameApi.ts";
import {useGameStore} from "@/store/gameStore.ts";
import {useNavigate} from "react-router-dom";
import {GameStatus} from "@/constants/gameStatus.ts";

export default function GamePage() {
    const navigate = useNavigate();
    const {currentGame, connectCode, setCurrentGame, playerName, playerSymbol} = useGameStore();

    useEffect(() => {
        if (!connectCode) {
            navigate('/')
            return;
        }

        if (currentGame?.status === GameStatus.COMPLETE) {
            navigate('/result')
            return
        }

        const fetchGame = async () => {
            const game = await getGame(connectCode);
            setCurrentGame(game)
            if (game.status === GameStatus.COMPLETE) {
                navigate('/result')
            }
        }

        const interval = setInterval(fetchGame, 1000);

        return () => clearInterval(interval);
    }, [connectCode, currentGame?.status, navigate, setCurrentGame]);

    const handleCellClick = async (moveIndex: number) => {
        if (currentGame && currentGame.board[moveIndex] === 0 && currentGame.currentTurnPlayerName === playerName) {
            const updatedGame = await makeMove(playerName, connectCode, moveIndex);
            setCurrentGame(updatedGame);
            if (updatedGame.status === GameStatus.COMPLETE){
                navigate('/result');
            }
        }
    };

    if (!currentGame) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <p className="text-muted-foreground">Loading game...</p>
            </div>
        )
    }

    return (

        <div className="min-h-screen flex flex-col items-center justify-center gap-6 bg-background">
            <h1 className="text-3xl font-bold text-foreground">Tic Tac Toe</h1>
            <p className="text-muted-foreground">Playing as <span className="font-bold text-foreground">{playerName}</span> ({playerSymbol})</p>
            {currentGame?.status === GameStatus.WAITING && (<div className="bg-yellow-100 text-yellow-800 border border-yellow-200 p-3 rounded-lg text-sm font-medium">
                Waiting for Player 2... Share this code: {connectCode}
            </div>)}

            {currentGame?.status === GameStatus.ACTIVE && (
                <div className="bg-blue-100 text-blue-800 border border-blue-200 p-3 rounded-lg text-sm font-medium">
                    {currentGame.currentTurnPlayerName === playerName
                        ? "Your turn!"
                        : `Waiting for ${currentGame.currentTurnPlayerName}...`}
                    <span className="ml-2">You are: {playerSymbol}</span>
                </div>
            )}

            <div className="grid grid-cols-3 gap-2">
                {currentGame?.board.map((value, index) => (
                    <div
                        key={index}
                        onClick={() => handleCellClick(index)}
                        className="w-24 h-24 flex items-center justify-center text-4xl font-bold border-2 cursor-pointer hover:bg-muted"
                    >
                        {value === 1 ? 'X' : value === 2 ? 'O' : ''}
                    </div>
                ))}
            </div>

        </div>
    )
}