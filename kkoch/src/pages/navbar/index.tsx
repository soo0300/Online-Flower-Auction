import { useState, useEffect } from "react"; 
import { Bars3Icon, XMarkIcon, UserCircleIcon, BellAlertIcon } from "@heroicons/react/24/solid";
import Logo from "@/assets/logo.png";
import { Link } from 'react-router-dom';
import useMediaQuery from '@/hooks/useMediaQuery';
import { useDispatch } from "react-redux";
import { logout } from '@/reducer/store/authSlice';
import secureLocalStorage from "react-secure-storage";
import axios from "axios";
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

  // 회원 이모지 누르면 나올 dropdown
  const [isDropdownOpen, setDropdownOpen] = useState(false); 
  // Navbar 드롭다운
  const [isNotificationOpen, setNotificationOpen] = useState(false);

  const dispatch = useDispatch();

  // 로그인 여부 확인
  const isLoggedIn = secureLocalStorage.getItem("memberkey") && secureLocalStorage.getItem("token");
  const [isLoggedOut, setIsLoggedOut] = useState(true);

  const username = localStorage.getItem("username");

  useEffect(() => {
    setIsLoggedOut(!isLoggedIn);
  }, [isLoggedIn]);

  const handleLogout = () => {
    // 로그아웃
    if(confirm("로그아웃 하시겠습니까?")) {
      setDropdownOpen(false);
      dispatch(logout());
    }
  }

  // 로그인 되어 있으면 알림 내역 가져오기
  if(isLoggedIn) {
    axios({
      method: "get",
      // url: `https://i9c204.p.ssafy.io/api/user-service/${secureLocalStorage.getItem("memberkey")}/alarms`,
      url: `/api/api/user-service/${secureLocalStorage.getItem("memberkey")}/alarms`,
      headers: {
        Authorization: `Bearer ${secureLocalStorage.getItem("token")}`
      }
    })
    .then((res) => {
      console.log(res);
    })
  }

  return <nav>
    <div
      className={`${navbarBackground} ${flexBetween} top-0 z-30 w-full py-3 border-b-2`}
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
                <Link to="/flowers/절화" state ={{ code: "절화"}}> 화훼정보</Link>
                <Link to="/notices" >공지사항</Link>
              </div>
                { isLoggedOut ? (
                  <div className={`${flexBetween} gap-8`}>
                    <Link to="/login">로그인</Link>
                    <Link to="/signup">회원가입</Link>
                  </div>
                  ) : (
                    <div className="flex justify-between">
                      <button
                        className="flex justify-between items-center"
                        onClick={() => setNotificationOpen(!isNotificationOpen)}
                      >
                        <BellAlertIcon className="h-10 w-10 text-blue-500"/>
                      </button>
                      <button className="flex justify-between items-center" onClick={() => setDropdownOpen(!isDropdownOpen)}>
                        <UserCircleIcon  className="h-10 w-10 text-blue-500"/>
                        {/* <span>{username}님</span> */}
                      </button>
                    </div>
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

    {/* 회원 메뉴 드롭다운 */}
    { isAboveMediumScreens && isDropdownOpen && (
      <div className="absolute z-20 top-20 right-10 bg-white border border-gray-300 rounded shadow-lg">
        <ul className="flex flex-col justify-center pl-0 mb-0">
          <li >
            <Link to="/mypage" className="block px-4 py-2 hover:bg-gray-100" onClick={() => setDropdownOpen(false)}>마이페이지</Link>
          </li>
          <li>
            <button onClick={handleLogout} className="block px-4 py-2 hover:bg-gray-100">로그아웃</button>
          </li>
          <li>
            {/* Add an event handler for 회원탈퇴 */}
            <button className="block px-4 py-2 hover:bg-gray-100">회원탈퇴</button>
          </li>
        </ul>
      </div>
    )}

    {/* 알림 드롭다운 */}
    { isAboveMediumScreens && isNotificationOpen && (
      <div className="absolute z-20 top-20 right-10 bg-white border border-gray-300 rounded shadow-lg">
        {/* 알림 내용 */}
        {/* 이곳에 알림 목록을 렌더링하는 컴포넌트나 요소를 추가하세요 */}
        ㅁㄴㅇㄹ
      </div>
    )}
  </nav>
}

export default Navbar