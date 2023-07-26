import Navbar from "@/pages/navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { useEffect, useState } from "react";
import AuctionReady from "@/pages/auction/AuctionLocation";
import MainPage from "./pages/mainPages/MainPage";
import Login from "@/pages/user/LoginPage";
import Signup from "@/pages/user/SignUpPage";
import AuctionLiveRoom from "./pages/auction/AuctionLiveRoom";

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
          <Route path={"/auction"} element={<AuctionLiveRoom/>}/>
          <Route path={"/auction"} element={<AuctionReady/>}/>

          <Route path={"/flowers"} />
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
