import React, { useEffect, useRef, useState } from 'react'
// import HomePageAuction from '@/assets/HomePageAuction.png'
import { DoughnutChart } from '@/components/chart/Doughnut'
import './AuctionLiveForm.css';
import Video from '../buyer/VideoRoom';

const AuctionLiveForm = () => {

  // 경매 정보 
  const auctionInfo = {
    point: 100000, // 보유 포인트
    quantity: 30, // 출하량
    provider: "홍승준", // 출하자
    flower: "장미/스프레이", // 품명
    grade: "특", // 등급
    nowPrice: 20000, // 현재가
    bidPrice: 2000, // 낙찰가
    buyer: "김싸피", // 낙찰자
  }; 
  
  // 원래 가격과 최종 가격을 지정
  const originalPrice = auctionInfo.nowPrice;
  const finalPrice = originalPrice / 2; // 최종 가격은 원래 가격의 1/2로 설정

  // 현재가를 변화시킬 state
  const [currentPrice, setCurrentPrice] = useState(originalPrice);
  const [isBiddingActive, setIsBiddingActive] = useState(true);

  const clickedPriceRef = useRef(null);
  const animationFrameRef = useRef(null);
  
  useEffect(() => {
    const totalTime = 10000; // 10초
    const startTime = Date.now(); // 시작 시간 저장
    const decreaseAmount = (originalPrice - finalPrice) / totalTime; // 감소량 계산
  
    // 현재가 함수 정의
    const animatePrice = () => {
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
      }
    };
  
    // 애니메이션 시작
    animationFrameRef.current = requestAnimationFrame(animatePrice);
  
    return () => {
      // 컴포넌트가 언마운트될 때 애니메이션 정리
      cancelAnimationFrame(animationFrameRef.current);
    };
  }, [isBiddingActive]);

  const handleBiddingButtonClick = () => {
    setIsBiddingActive(false); // 입찰 프로세스를 비활성화로 설정
    console.log("낙찰가", currentPrice)
    console.log("이즈비딩상태", isBiddingActive)
    // setGraph((prevGraph) => clickedPriceRef.current);
    clickedPriceRef.current = currentPrice; // 현재 가격을 클릭한 시점의 가격으로 업데이트
    // 필요한 경우 추가 로직을 이곳에 추가하여 입찰 프로세스를 처리할 수 있음
  };

  return (
    <div className='gap-24 bg-gray-20 py-28 md:h-full md:pb-0'>
      <div className='auction-top'>
        <button className='leave-button'>
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
          <Video />
          {/* <img src={ HomePageAuction } alt="LiveStream" /> */}
        </div>
        <div className='auction-info'>
          <div className='auction-point'>
            보유 포인트: { auctionInfo.point }
          </div>
          <div className='auction-border'>
            <div className='auction-status'>
              경매중
            </div>
            <div className='auction-container'>
              <div className='graph-container'>
                <DoughnutChart isBiddingActive={isBiddingActive} />
              </div>
              <div className='auction-status-info'>
                출하량: { auctionInfo.quantity } <br />
                출하자: { auctionInfo.provider } <br />
                품명: { auctionInfo.flower } <br />
                등급: { auctionInfo.grade} <br />
              </div>
            </div>
          </div>
          <div className='auction-bidding-border'>
            <div className='auction-bidding-info'> 
              <div className='auction-bidding-price'>
                {/* 10원단위까지만 표시될 수 있게 */}
                현재가: { Math.floor(isBiddingActive ? currentPrice / 10 : clickedPriceRef.current / 10) * 10 } <br />
                낙찰자: { auctionInfo.buyer} <br />
                낙찰단가: {auctionInfo.bidPrice} <br />
              </div>
              <div className='auction-next-title'>
                다음 꽃 정보
              </div>
              <div className='auction-next-info'>
                품명: { auctionInfo.flower } <br />
                등급: { auctionInfo.grade} <br />
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
    </div>
  )
}

export default AuctionLiveForm
