import AuctionModal from '@/pages/auction/AuctionModal';
import React, { useEffect, useRef, useState } from 'react';
import secureLocalStorage from 'react-secure-storage';
import { start } from 'repl';

interface BidderInfo {
  memberToken: string;
  auctionArticleId: number;
  price: number;
  message: string;
}

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

const WebSocketComponent = () => {
  // 소켓 활성화 여부
  const [socket, setSocket] = useState<WebSocket | null>(null);
  // 낙찰 정보
  const [bidderInfo, setBidderInfo] = useState<BidderInfo[]>([]);
  // 멤버키
  const memberToken = secureLocalStorage.getItem("memberkey");
  // 낙찰 모달 출력
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  // 경매 전체 목록
  const [auctionInfos, setAuctionInfos] = useState([]);
  // 경매 물품 인덱스
  const [auctionInfoIndex, setAuctionInfoIndex] = useState(0);
  // 경매 물품 하나씩
  const [auctionInfo, setAuctionInfo] = useState<auctionInfo[]>([]);
  // 경매 시작됐는지 체크
  const [isBiddingActive, setIsBiddingActive] = useState(false);
  // 경매 시작가
  const [startPrice, setStartPrice] = useState(0);
  // 경매 종료가
  const [finalPrice, setFinalPrice] = useState(0);
  // 경매 현재가
  const [currentPrice, setCurrentPrice] = useState(startPrice);
  // 경매 현재가 변동
  const animationFrameRef = useRef(null);
  // 입찰 버튼 클릭한 시점의 가격
  const clickedPriceRef = useRef(null);

  const getMessage = (e) => {
    console.log(e)
    if (!e.data.data.message.admin) {
      // setBidderInfo(e.data.data)
      // setShowSuccessModal(true);
      setAuctionInfos(e.data.data)
    }
  }

  const getNowList = (auctionInfo) => {
    setStartPrice(auctionInfo.startPrice);
    setCurrentPrice(startPrice)
    setFinalPrice(startPrice / 2);
  }

  // 입찰하기 버튼 클릭하면 입찰자 정보 소켓전송
  const handleBiddingButtonClick = () => {
    if (showSuccessModal) {
      return;
    }
    setIsBiddingActive(false); // 입찰 프로세스를 비활성화로 설정

    if (bidderInfo.length > 0 && socket) {
      // 현재가 10원 단위 절삭
      const roundedCurrentPrice = Math.floor(currentPrice / 10) * 10;
      console.log("절삭현재가", roundedCurrentPrice);

      // memberInfo 갱신 후 JSON 변환 후 전송
      const memberInfo = {
        memberToken: memberToken,
        auctionArticleId: bidderInfo[0]?.auctionArticleId,
        price: roundedCurrentPrice || 0
      };
      socket.send(JSON.stringify(memberInfo));
      console.log(memberInfo)
      
      //낙찰자 선정될 때까지 집계중 표시
      setCurrentPrice("집계중");
    }
    // setBidderInfo(res.data.data)
    // setShowSuccessModal(true)

    // 모달 열기 로직
    // if (!showSuccessModal && auctionInfoIndex < auctionInfos.length - 1) {
    //   setShowSuccessModal(true);

    //   // 5초 후에 모달 닫고 다음 경매 정보로 업데이트
    //   setTimeout(() => {
    //     if (auctionInfoIndex < auctionInfos.length - 1) {
    //         setAuctionInfoIndex(auctionInfoIndex + 1);
    //         setShowSuccessModal(false); // 모달 닫기
    //         setBidderInfo([]); // 낙찰 정보 초기화
    //         setIsBiddingActive(true); // 입찰 활성화 상태 초기화
    //       }
    //     }, 5000);
    //   }
    setBidderInfo([]);
    
    // console.log("낙찰가", currentPrice)
    // console.log("이즈비딩상태", isBiddingActive)
    // setGraph((prevGraph) => clickedPriceRef.current);
    clickedPriceRef.current = currentPrice; // 현재 가격을 클릭한 시점의 가격으로 업데이트
    // 필요한 경우 추가 로직을 이곳에 추가하여 입찰 프로세스를 처리할 수 있음
  };

  // 페이지 마운트될 때
  useEffect(() => {
    // 웹 소켓 링크 설정
    const newSocket = new WebSocket('wss://i9c204.p.ssafy.io/ws/');

    // 웹 소켓 열리면 이벤트 핸들러
    newSocket.onopen = (e) => {
      console.log("웹 소켓 시작");
      setSocket(newSocket);
      if (e) {
        getMessage(e);
      }
    };

    // 소켓 들어온 메시지 확인
    newSocket.addEventListener("message", (msg) => console.log(msg.data));

    newSocket.onclose = (e) => {
      console.log('웹 소켓 종료', e.code);
      console.log('웹 소켓 에러 코드', e);
    };

    // 컴포넌트 언마운트 시 웹 소켓 종료
    return () => {
      newSocket.close();
    };
  }, [memberToken]);

  // 낙찰 정보가 업데이트가 되면 실행
  // useEffect(() => {
  //   if (bidderInfo.length > 0 && socket) {
  //     // 현재가 10원 단위 절삭
  //     const roundedCurrentPrice = Math.floor(currentPrice / 10) * 10;
  //     console.log("절삭현재가", roundedCurrentPrice);

  //     // memberInfo 갱신 후 JSON 변환 후 전송
  //     const memberInfo = {
  //       memberToken: memberToken,
  //       auctionArticleId: bidderInfo[0]?.auctionArticleId,
  //       price: roundedCurrentPrice || 0
  //     };
  //     socket.send(JSON.stringify(memberInfo));
  //     console.log(memberInfo)
  //   }
  // }, [bidderInfo]);

  // 경매 전체 목록을 받아오거나 auctionindex가 바뀌면 실행
  useEffect(() => {
    // 경매시작 활성화
    setIsBiddingActive(true);
    // 경매물품 업데이트
    setAuctionInfo(auctionInfos[auctionInfoIndex]); // auctionInfoIndex에 해당하는 정보 할당
  }, [auctionInfos, auctionInfoIndex])

  // 경매 물품이 하나씩 갱신되면 실행
  useEffect(() => {
    getNowList(auctionInfo)
    //  // 현재가 갱신
    // setCurrentPrice(auctionInfos[auctionInfoIndex].startPrice)
    // // 모달창 띄우기
    // setKey((prevKey) => prevKey + 1)
  }, [auctionInfo])

  useEffect(() => {
    if (startPrice !== 0) {
      const totalTime = 10000; // 10초
      const startTime = Date.now(); // 시작 시간 저장
      const decreaseAmount = (startPrice - finalPrice) / totalTime; // 감소량 계산

      const animatePrice = () => {
        console.log("너 계속 실행되지?")
        if (!isBiddingActive) {
          return;
        }
        const currentTime = Date.now(); // 현재 시간 구하기
        const elapsedTime = currentTime - startTime; // 경과 시간 계산
    
        // 경과 시간에 따라 현재가 감소시키기
        const newPrice = startPrice - decreaseAmount * elapsedTime;
    
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
    }
  },[startPrice])



  return (
    <div>
       {showSuccessModal && (
        <AuctionModal modalMessage={bidderInfo.message} onClose={() => setShowSuccessModal(false)} />
      )}
    </div>
  );
}

export default WebSocketComponent;
