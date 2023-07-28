import Navbar from "@/pages/navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { useEffect, useState } from "react";
import AuctionLocation from "@/pages/auction/AuctionLocation";
import AuctionWaitingRoom from "@/pages/auction/AuctionWaitingRoom";
import MainPage from "./pages/mainPages/MainPage";
import Login from "@/pages/user/LoginPage";
import Signup from "@/pages/user/SignUpPage";
// import AuctionLiveRoom from "./pages/auction/AuctionLiveRoom";
import TradingInfo from "./components/forms/TradingInfo";

function App() {
  const [isTop, setIsTopOfPage] = useState<boolean>(true);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY === 0) {
        setIsTopOfPage(true);
      }
      if (window.scrollY !== 0) setIsTopOfPage(false);
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <div className="app bg-gray-20">
      <BrowserRouter>
        <Navbar isTop={isTop}/>
        {/* 라우팅 정보 */}
        <Routes>
          <Route path={"/"} element={<MainPage />}/>
          <Route path={"/auction"} element={<AuctionLocation/>}/>
          <Route path={"/auction/waitingroom"} element={<AuctionWaitingRoom/>}/>

          <Route path={"/flowers"} element={<TradingInfo />}/>
          <Route path={"/customer"} />

          {/* 로그인 */}
          <Route path={"/login"} element={<Login />}/>
          <Route path={"/signup"} element={<Signup />}/>
        </Routes>
        
      </BrowserRouter>
    </div>
  )
}

export default App
