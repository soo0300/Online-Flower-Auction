import { useState, useEffect } from "react"; 
import { Bars3Icon, XMarkIcon } from "@heroicons/react/24/solid";
import Logo from "@/assets/logo.png";
import { Link } from 'react-router-dom';
import useMediaQuery from '@/hooks/useMediaQuery';
import { useSelector, useDispatch } from "react-redux";
import { RootState } from '@/reducer/store'; // RootState 추가
import { logout } from '@/reducer/store/authSlice';
// import ActionButton from "@/shared/ActionButton";

type Props = {
  isTop: boolean;
}

const Navbar = ({isTop} : Props) => {
  const flexBetween = "flex items-center justify-between";
  
  // 모바일 용 토글버튼은 false로 초기화 
  const [isMenuToggled, menuToggle] = useState<boolean>(false);
  const isAboveMediumScreens = useMediaQuery("(min-width: 1060px)");
  const navbarBackground = isTop ? "" : "bg-primary-100 drop-shadow";

  const dispatch = useDispatch();

  // 로그인 여부 확인
  const isLoggedIn = useSelector((state: RootState) => state.auth.token !== null);
  const [isLoggedOut, setIsLoggedOut] = useState(false);

  useEffect(() => {
    setIsLoggedOut(!isLoggedIn);
  }, [isLoggedIn]);


  const handleLogout = () => {
    // 로그아웃
    if(confirm("로그아웃 하시겠습니까?")) {
      dispatch(logout());
    }
  }

  return <nav>
    <div
      className={`${navbarBackground} ${flexBetween} fixed top-0 z-30 w-full py-3 border-b-2`}
    >
      <div className={`${flexBetween} mx-auto w-5/6`}>
        <div className={`${flexBetween} w-full gap-16`}>
          {/* 로고 이미지 */}
          <Link to="/">
            <img src={Logo} alt="logo" className="h-20"/>
          </Link>

          {/* 메뉴 */}
          {isAboveMediumScreens ? // PC화면일때 : 모바일 화면일때 
            (<div className={`${flexBetween} w-full`}>
              <div className={`${flexBetween} gap-8 text-sm`}>
                <Link to="/auction"> 경매</Link>
                <Link to="/flowers"> 화훼정보</Link>
                <Link to="/flowers/info"> 화훼상세정보</Link>
                <Link to="/customer" />
              </div>
                { isLoggedOut ? (
                  <div className={`${flexBetween} gap-8`}>
                    <Link to="/login">로그인</Link>
                    <Link to="/signup">회원가입</Link>
                  </div>
                  ) : (
                  <a href="" onClick={handleLogout}>로그아웃</a>
                )}
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
          <Link to="/auction"> 경매</Link>
          
        </div>
      </div>
    )}
  </nav>
}

export default Navbar