import Navbar from "@/pages/navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import AuctionReady from "@/pages/auction/AuctionReady";
import MainPage from "./pages/mainPages/MainPage";
import Login from "@/pages/user/Login";

function App() {
  return (
    <div className="app bg-gray-20">
      <BrowserRouter>
        <Navbar />
        {/* 라우팅 정보 */}
        <Routes>
          <Route path={"/"} element={<MainPage />}/>
          <Route path={"/auction"} element={<AuctionReady/>}/>
          <Route path={"/flowers"} />
          <Route path={"/customer"} />

          {/* 로그인 */}
          <Route path={"/login"} element={<Login />}/>

        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
