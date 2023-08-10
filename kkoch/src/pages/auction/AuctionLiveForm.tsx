import React, { useEffect, useRef, useState } from 'react'
// import HomePageAuction from '@/assets/HomePageAuction.png'
import { DoughnutChart } from '@/components/chart/Doughnut'
import './AuctionLiveForm.css';
import Video from '../buyer/VideoRoom';
import { useLocation, useNavigate } from 'react-router-dom';
import secureLocalStorage from 'react-secure-storage';
import axios from 'axios';
import AuctionModal from './AuctionModal';
import { toast } from 'react-toastify';

interface auctionInfo {
  auctionArticleId: number;
  code: string; // 카테고리
  name: string; // 품목
  type: string; // 품종
  count: number; //단수
  shipper: string; //출하자
  region: string; //지역
  grade: string; //등급
  startPrice: number; //시작가
}

interface BidderInfo {
  memberToken: string;
  auctionArticleId: number;
  price: number;
  message: string;
}

const AuctionLiveForm = () => {
  const navigate = useNavigate();
  const auctionArticles = useLocation();
  // console.log("상속", auctionArticles.state.auctionArticles[0][0])
  const auctionInfos = auctionArticles.state.auctionArticles[0]
  const [auctionInfoIndex, setAuctionInfoIndex] = useState(0);
  // console.log(secureLocalStorage.getItem("memberkey"))
  const memberToken = secureLocalStorage.getItem("memberkey");
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  // const [modalMessage, setModalMessage] = useState('');
  const [bidderInfo, setBidderInfo] = useState<BidderInfo>([]);
  const [key, setKey] = useState(0);
  

  // 경매 정보 
  const [auctionInfo, setAuctionInfo] = useState(auctionInfos[0]);

  // 원래 가격과 최종 가격을 지정
  const originalPrice = auctionInfo.startPrice;
  // console.log('원래가격', originalPrice)
  const finalPrice = originalPrice / 2; // 최종 가격은 원래 가격의 1/2로 설정

  // 현재가를 변화시킬 state
  const [currentPrice, setCurrentPrice] = useState(originalPrice);
  const [isBiddingActive, setIsBiddingActive] = useState(true);
  // console.log('현재가', currentPrice)
  const clickedPriceRef = useRef(null);
  const animationFrameRef = useRef(null);

  useEffect(() => {
    setIsBiddingActive(true);
    setAuctionInfo(auctionInfos[auctionInfoIndex]); // auctionInfoIndex에 해당하는 정보 할당
    setCurrentPrice(auctionInfos[auctionInfoIndex].startPrice)
    setKey((prevKey) => prevKey + 1);
  }, [auctionInfoIndex, auctionInfos]);
  
  useEffect(() => {
    const totalTime = 10000; // 10초
    const startTime = Date.now(); // 시작 시간 저장
    const decreaseAmount = (originalPrice - finalPrice) / totalTime; // 감소량 계산

    const animatePrice = () => {
      // console.log("너 계속 실행되지?")
      if (!isBiddingActive) {
        return;
      }
      const currentTime = Date.now(); // 현재 시간 구하기
      const elapsedTime = currentTime - startTime; // 경과 시간 계산
  
      // 경과 시간에 따라 현재가 감소시키기
      const newPrice = originalPrice - decreaseAmount * elapsedTime;
  
      // 최소값 제한
      setCurrentPrice(Math.max(newPrice, finalPrice));
  
      // 애니메이션이 끝나지 않았으면 다음 프레임에 애니메이션 함수 호출
      if (elapsedTime < totalTime && isBiddingActive) {
        animationFrameRef.current = requestAnimationFrame(animatePrice);
      } else if (!isBiddingActive) {
        setCurrentPrice(clickedPriceRef.current); // 클릭한 시점의 현재 가격으로 업데이트
      } else if (isBiddingActive && auctionInfoIndex < auctionInfos.length - 1) {
        // 입찰 중이고, 마지막 경매 정보가 아니면 3초 뒤에 다음 경매 정보로 업데이트
        const updateTimer = setTimeout(() => {
          setAuctionInfoIndex(auctionInfoIndex + 1);
        }, 3000);
        
        return () => {
          clearTimeout(updateTimer); // 컴포넌트가 unmount될 때 타이머 정리
        };
      }
    };
  
    // 애니메이션 시작
    animationFrameRef.current = requestAnimationFrame(animatePrice);
  
    return () => {
      // 컴포넌트가 언마운트될 때 애니메이션 정리
      cancelAnimationFrame(animationFrameRef.current);
    };
  }, [auctionInfo, auctionInfoIndex]);

  const handleBiddingButtonClick = () => {
    if (showSuccessModal) {
      return;
    }

    setIsBiddingActive(false); // 입찰 프로세스를 비활성화로 설정
    const roundedCurrentPrice = Math.floor(currentPrice / 10) * 10;
    // console.log("절삭현재가", roundedCurrentPrice)

    axios({
      method: "post",
      url: "/api/api/auction-service/auctions/participant",
      data: {
        "memberToken": memberToken,
        "auctionArticleId": auctionInfo.auctionArticleId,
        "price": roundedCurrentPrice
      }
    })    .then((res) => {
      console.log('입찰하기', res.data.data)
      setBidderInfo(res.data.data)
      // setShowSuccessModal(true)

      // 모달 열기 로직
      if (!showSuccessModal && auctionInfoIndex < auctionInfos.length - 1) {
        setShowSuccessModal(true);

        // 5초 후에 모달 닫고 다음 경매 정보로 업데이트
        setTimeout(() => {
          if (auctionInfoIndex < auctionInfos.length - 1) {
            setAuctionInfoIndex(auctionInfoIndex + 1);
            setShowSuccessModal(false); // 모달 닫기
            setBidderInfo([]); // 낙찰 정보 초기화
            setIsBiddingActive(true); // 입찰 활성화 상태 초기화
          }
        }, 5000);
      }
      setCurrentPrice(clickedPriceRef.current);
    })
    .catch((error) => {
      console.log("입찰실패",error)
    })

    setBidderInfo([]);
    
    console.log("낙찰가", currentPrice)
    console.log("이즈비딩상태", isBiddingActive)
    // setGraph((prevGraph) => clickedPriceRef.current);
    clickedPriceRef.current = currentPrice; // 현재 가격을 클릭한 시점의 가격으로 업데이트
    // 필요한 경우 추가 로직을 이곳에 추가하여 입찰 프로세스를 처리할 수 있음
  };

  return (
    <div className=''>
      <div className='auction-top'>
        <button className='leave-button' onClick={() => navigate('/auction')}>
          <svg 
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="black"
            className="w-10 h-10"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M15.75 9V5.25A2.25 2.25 0 0013.5 3h-6a2.25 2.25 0 00-2.25 2.25v13.5A2.25 2.25 0 007.5 21h6a2.25 2.25 0 002.25-2.25V15M12 9l-3 3m0 0l3 3m-3-3h12.75" 
            />
          </svg>
        </button>
        <div className='auction-heading'>
          at화훼 양재공판장 경매방 경매중!!
        </div>
      </div>
      <div className='auction-content'>
        <div className='stream-image'>
          {/* <Video /> */}
          {/* <img src={ HomePageAuction } alt="LiveStream" /> */}
        </div>
        <div className='auction-info'>
          <div className='auction-point'>
            {/* 보유 포인트: { auctionInfo.point } */}
          </div>
          <div className='auction-border'>
            <div className='auction-status'>
              경매중
            </div>
            <div className='auction-container'>
              <div className='auction-graph-info-container'>
                <div className='graph-container'>
                  <DoughnutChart isBiddingActive={isBiddingActive} key={key}/>
                </div>
                <div className='auction-status-info'>
                  <div className='info-row'>
                    <span className='info-title'>출하량</span>
                      <span className='red-text'>{auctionInfo.count}</span>
                  </div>
                  <div className='info-row'>
                    <span className='info-title'>출하자</span>
                    <span className='info-data'>{ auctionInfo.shipper }</span>
                  </div>
                  <div className='info-row'>
                    <span className='info-title'>품명</span>
                    <span className='info-data'>{ auctionInfo.type } / { auctionInfo.name }</span>
                  </div>
                  <div className='info-row'>
                    <span className='info-title'>등급</span>
                    <span className='info-data'>{ auctionInfo.grade }</span>
                  </div>
                  <div className='info-row'>
                    <span className='info-title'>현재가</span>
                    <span className='info-data'>
                      <span className='red-text'>
                        {Math.floor(isBiddingActive ? currentPrice / 10 : clickedPriceRef.current / 10) * 10}
                      </span>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className='auction-bidding-border'>
            <div className='auction-bidding-info'> 
              <div className='auction-bidding-price'>
                {/* 10원단위까지만 표시될 수 있게 */}
                <div>
                  <span className='info-title'>
                    낙찰자 
                  </span>
                  <span className='red-text'>
                    { bidderInfo.memberToken ? bidderInfo.memberToken : ''}
                  </span>  
                </div> 
                <div>
                  <span className='info-title'>
                    낙찰단가 
                  </span>
                  <span className='red-text'>
                    {bidderInfo.price ? bidderInfo.price : ''}
                  </span>
                </div>
              </div>
              <div className='auction-next-title'>
                다음 꽃 정보
              </div>
              <div className='auction-next-info'>
                <div>
                  <span className='info-title'>
                    품명
                  </span>
                  <span className='red-text'>
                    {auctionInfos[auctionInfoIndex + 1]?.type + ' /'} {auctionInfos[auctionInfoIndex + 1]?.name}
                  </span>
                </div>
                <div>
                  <span className='info-title'>
                    등급 
                  </span>
                  <span className='red-text'>{auctionInfos[auctionInfoIndex + 1]?.grade}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className='button-container'>
        <div>
          <button className='normal-button'>마이크 켜기</button>
        </div>
        <div>
          <button className='normal-button'>비디오 켜기</button>
        </div>
        <div>
          <button className='normal-button'>위시리스트 보기</button>
        </div>
        <div>
          <button className='normal-button'>금일 경매 목록</button>
        </div>
        <div>
          <button className='bidding-button' onClick={handleBiddingButtonClick}>
            입찰하기
          </button>
        </div>
      </div>
       {/* 모달 컴포넌트 */}
       {showSuccessModal && (
        <AuctionModal modalMessage={bidderInfo.message} onClose={() => setShowSuccessModal(false)} />
      )}
    </div>
  )
}

export default AuctionLiveForm
