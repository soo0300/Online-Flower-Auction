import { useState } from "react"; 
import { Bars3Icon, XMarkIcon } from "@heroicons/react/24/solid";
import Logo from "@/assets/logo.png";
// import Link from './Link';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import useMediaQuery from '@/hooks/useMediaQuery';
// import ActionButton from "@/shared/ActionButton";

const Navbar = () => {
  const flexBetween = "flex items-center justify-between";
  
  // 모바일 용 토글버튼은 false로 초기화 
  const [isMenuToggled, menuToggle] = useState<boolean>(false);
  const isAboveMediumScreens = useMediaQuery("(min-width: 1060px)");
  // const navbarBackground = isTop ? "" : "bg-primary-100 drop-shadow";

  return <BrowserRouter>
    <nav>
    <div
      className={`$${flexBetween} fixed top-0 z-30 w-full py-3 border-b-2 bg-primary-100`}
    >
      <div className={`${flexBetween} mx-auto w-5/6`}>
        <div className={`${flexBetween} w-full gap-16`}>
          {/* 로고 이미지 */}
          <img src={Logo} alt="logo" className="h-20"/>

          {/* 메뉴 */}
          {isAboveMediumScreens ? // PC화면일때 : 모바일 화면일때 
            (<div className={`${flexBetween} w-full`}>
              <div className={`${flexBetween} gap-8 text-sm`}>
                <Routes>
                  {/* <Link to="" page="메인페이지" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/> */}
                  <Route path="/" ></Route>
                  <Route path="/auction" ></Route>
                  <Route path="/flowers"></Route>
                  <Route path="/customer"></Route>
                  {/* <Link to="" page="경매" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/>
                  <Link to="" page="꽃 정보" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/>
                  <Link to="" page="고객센터" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/> */}
                </Routes>
              </div>
              <div className={`${flexBetween} gap-8`}>
                <p>로그인</p>
                <p>회원가입</p>
              </div>
            </div>)
            : (
                <button
                  className="rounded-full bg-secondary-500 p-2"
                  onClick={() => menuToggle(!isMenuToggled)}
                >
                  <Bars3Icon className="h-6 w-6 text-white" />
                </button>
            )}
        </div>
      </div>
    </div>  
    
    {/* 모바일 화면 부분 */}
    {!isAboveMediumScreens && isMenuToggled && (
      <div className="fixed right-0 bottom-0 z-40 h-full w-[300px] bg-primary-100 drop-shadow-xl">
        
        {/* 메뉴 닫기 */}
        <div className="flex justify-end p-12 ">
          <button onClick={() => menuToggle(!isMenuToggled)}>
            <XMarkIcon className="h-6 w-6 text-gray-400" />
          </button>
        </div>

        {/* 메뉴 목록 */}
        <div className="ml-[33%] flex flex-col gap-10 text-2xl">
          
          {/* <Link page="메인페이지" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/>
          <Link page="경매" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/>
          <Link page="꽃 정보" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/>
          <Link page="고객센터" selectedPage={selectedPage} setSelectedPage={setSelectedPage}/> */}
        </div>
      </div>
    )}
    </nav>
  </BrowserRouter>
  
}

export default Navbar