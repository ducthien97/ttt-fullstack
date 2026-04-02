import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomePage from './pages/HomePage.tsx'
import GamePage from './pages/GamePage.tsx'
import ResultPage from './pages/ResultPage.tsx'
import LeaderboardPage from './pages/LeaderboardPage.tsx'

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/game" element={<GamePage />} />
          <Route path="/result" element={<ResultPage />} />
          <Route path="/leaderboard" element={<LeaderboardPage />} />
        </Routes>
      </BrowserRouter>
  )
}

export default App