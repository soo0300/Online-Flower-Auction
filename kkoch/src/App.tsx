import Navbar from "@/pages/navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { useEffect, useState } from "react";
import AuctionLocation from "@/pages/auction/AuctionLocation";
import AuctionWaitingRoom from "@/pages/auction/AuctionWaitingRoom";
import MainPage from "@/pages/mainPages/MainPage";
import Login from "@/pages/user/login/LoginPage";
import Signup from "@/pages/user/signUp/SignUpPage";
import AuctionLiveRoom from "@/pages/auction/AuctionLiveRoom";
import MyPage from "@/pages/user/profile/MyPage";
import SelectArea from "@/pages/admin/SelectArea";
import OpenSession from "./pages/admin/OpenSession";
// import AuctionLiveRoom from "./pages/auction/AuctionLiveRoom";
import TradingInfo from "@/pages/flowerInfo/TradingInfo"
import FlowerDetail from "./pages/flowerInfo/FlowerDetail";
import Notice from "./pages/notice/Notice";
import NoticeDetail from "./pages/notice/NoticeDetail";

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
          <Route path={"/auction"} element={<AuctionLocation/>} />
          <Route path={"/auction/waitingroom"} element={<AuctionWaitingRoom/>}/>
          <Route path={"/auction/liveroom"} element={<AuctionLiveRoom/>}/>

          <Route path={"/flowers/"} element={<TradingInfo />}/>
          <Route path={"/flowers/:code"} element={<TradingInfo />}/>
          <Route path={"/flowers/info/:flower/:variety"} element={<FlowerDetail />}/>
          
          <Route path={"/notices"} element={<Notice />}/>
          <Route path={"/notices/:noticeId"} element={<NoticeDetail />}/>
 
          {/* 로그인 */}
          <Route path={"/login"} element={<Login />}/>
          <Route path={"/signup"} element={<Signup />}/>

          {/* 마이페이지 */}
          <Route path={"/mypage"} element={<MyPage/>}/>

          {/* 관리자 경매방 생성 경로 */}
          <Route path={"/admin/selectArea"} element={<SelectArea/>} />
          <Route path={"/admin/openSession"} element={<OpenSession/>} />
        </Routes>
        
      </BrowserRouter>
    </div>
  )
}

export default App
