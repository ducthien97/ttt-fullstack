import {Input} from '../components/ui/input'
import {Button} from '../components/ui/button'
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useGameStore} from "@/store/gameStore.ts";
import {createPlayer} from "@/api/playerApi.ts";
import {createGame, joinGame} from "@/api/gameApi.ts";


export default function HomePage() {
    const [playerInputName, setInputName] = useState('');
    const [showJoinDialog, setShowJoinDialog] = useState(false);
    const [connectCode, setConnectCode] = useState('');
    const navigate = useNavigate();
    const {setPlayerName, setConnectCode: setStoreConnectCode, setCurrentGame, setPlayerSymbol} = useGameStore()
    const [banner, setBanner] = useState<{ type: 'success' | 'error', message: string } | null>(null)


    const handleCreateGame =
        (async () => {
            try {
                const createdPlayer = await createPlayer(playerInputName);
                const createdGame = await createGame(createdPlayer.name);
                setPlayerName(createdPlayer.name);
                setStoreConnectCode(createdGame.connectCode);
                setCurrentGame(createdGame);
                setPlayerSymbol('X');

                setBanner({type: 'success', message: 'Created game successfully! Taking you to the board...'})
                await new Promise(r => setTimeout(r, 2000));
                navigate('/game');
            } catch (err) {
                setBanner({
                    type: 'error',
                    message: err instanceof Error ? err.message : 'An error occurred when creating this game, please try again'
                })
            }
        });

    const handleOnSubmitJoinDialog =
        (async () => {
            if (connectCode.length != 5) {
                return;
            }
            try {
                const game = await joinGame(playerInputName, connectCode);
                setStoreConnectCode(game.connectCode);
                setPlayerName(playerInputName);
                setCurrentGame(game);
                setPlayerSymbol('O');

                setBanner({type: 'success', message: 'Joined game successfully! Taking you to the board...'})
                await new Promise(r => setTimeout(r, 2000));
                navigate('/game')
            } catch (err) {
                setBanner({
                    type: 'error',
                    message: err instanceof Error ? err.message : 'An error occurred when joining this game, please try again'
                })
            }
        });


    const handleJoinGameButtonClick = (() => {
        setShowJoinDialog(true);
    })

    return (
        <div className="min-h-screen flex flex-col items-center justify-center gap-6 bg-background">
            <h1 className="text-4xl font-bold text-foreground">Tic Tac Toe</h1>

            <div
                className="bg-card text-card-foreground p-8 rounded-xl border w-full max-w-sm flex flex-col gap-4 shadow-sm">
                <p className="text-muted-foreground font-medium">Enter your name to start</p>
                <Input
                    value={playerInputName}
                    placeholder="Your name"
                    onChange={(e) => setInputName(e.target.value)}
                />

                {playerInputName && (
                    <div className="flex flex-col gap-2">
                        <Button onClick={handleCreateGame} className="w-full">
                            Create Game
                        </Button>
                        <Button onClick={handleJoinGameButtonClick} variant="outline" className="w-full">
                            Join Game
                        </Button>
                    </div>
                )}

                {showJoinDialog && (
                    <div className="flex flex-col gap-3 border-t pt-4">
                        <p className="text-muted-foreground font-medium">Enter your 5-digit code:</p>
                        <Input
                            value={connectCode}
                            onChange={(e) => setConnectCode(e.target.value)}
                            placeholder="00000"
                            maxLength={5}
                            className="text-center text-lg tracking-widest"
                        />
                        <div className="flex gap-2">
                            <Button onClick={handleOnSubmitJoinDialog} className="flex-1">Join</Button>
                            <Button onClick={() => setShowJoinDialog(false)} variant="outline"
                                    className="flex-1">Cancel</Button>
                        </div>
                    </div>
                )}

                {banner && (
                    <div className={`w-full p-3 rounded-lg text-sm font-medium ${
                        banner.type === 'success'
                            ? 'bg-green-100 text-green-800 border border-green-200'
                            : 'bg-red-100 text-red-800 border border-red-200'
                    }`}>
                        {banner.message}
                    </div>
                )}
            </div>
        </div>
    )
}