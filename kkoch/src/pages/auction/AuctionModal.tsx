import React from 'react'
import { ToastContainer, toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';

const AuctionModal = ({ modalMessage, onClose }) => {
  React.useEffect(() => {
    if (modalMessage === "낙찰 성공") {
      toast.success("낙찰 성공!", {
        position: toast.POSITION.TOP_CENTER,
      });
    } else {
      toast.error("낙찰 실패!", {
        position: toast.POSITION.TOP_LEFT,
      });
    }
  }, [modalMessage]);
  
  return (
    <div>
      <ToastContainer 
        position="top-right" // 알람 위치 지정
        autoClose={3000} // 자동 off 시간
        hideProgressBar={false} // 진행시간바 숨김
        rtl={false} // 알림 좌우 반전
        pauseOnFocusLoss // 화면을 벗어나면 알람 정지
        pauseOnHover // 마우스를 올리면 알람 정지
        theme="light"
      />
    </div>
  )
}

export default AuctionModal