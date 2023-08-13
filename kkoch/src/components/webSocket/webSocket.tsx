import AuctionModal from '@/pages/auction/AuctionModal';
import React, { useEffect, useRef, useState } from 'react';
import secureLocalStorage from 'react-secure-storage';
import { DoughnutChart } from '../chart/Doughnut';
import axios from 'axios';

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
  const [bidderInfo, setBidderInfo] = useState<BidderInfo>();
  // 멤버키
  const memberToken = secureLocalStorage.getItem("memberkey");
  // 낙찰 모달 출력
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  // 경매 전체 목록
  // const [auctionInfos, setAuctionInfos] = useState([]);
  // 경매 물품 인덱스
  // 현재 경매 물품
  const [auctionNowInfo, setAuctionNowInfo] = useState<auctionInfo>();
  // 다음 경매 물품
  const [auctionNextInfo, setAuctionNextInfo] = useState<auctionInfo>();
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
  // 모달창 고유ID
  const [key, setKey] = useState(0);

  // const getMessage = (e) => {
  //   console.log(e)
  //   if (!e.data.data.message.admin) {
  //     // setBidderInfo(e.data.data)
  //     // setShowSuccessModal(true);
  //     setAuctionInfos(e.data.data)
  //   }
  // }

  // 현재 경매품 목록 함수
  const getNowList = (auctionNowInfo) => {
    // 시작가 갱신
    setStartPrice(auctionNowInfo?.startPrice);
    // 현재가 갱신
    setFinalPrice(auctionNowInfo?.startPrice / 2);
    
    // 시작가 변경 후 현재가 갱신
    setCurrentPrice(auctionNowInfo?.startPrice);
  };

  // 입찰하기 버튼 클릭하면 입찰자 정보 소켓전송
  const handleBiddingButtonClick = () => {
    if (showSuccessModal) {
      return;
    }
    setIsBiddingActive(false); // 입찰 프로세스를 비활성화로 설정
    // 그래프 멈추는 변수 소켓 전송
    const stopGraph = {
      message: "press",
      isBiddingActive: false
    };
    socket.send(JSON.stringify(stopGraph));

    if (socket) {
      // 현재가 10원 단위 절삭
      const roundedCurrentPrice = Math.floor(currentPrice / 10) * 10;
      console.log("절삭현재가", roundedCurrentPrice);
      const info = {
        price: roundedCurrentPrice
      }
      socket.send(JSON.stringify(info))

      // memberInfo 갱신 후 JSON 변환 후 전송
      axios({
        method: "post",
        // url: "/api/auction-service/auctions/participant",
        url: "/api/api/auction-service/auctions/participant",
        data: {
          "memberToken": memberToken,
          // "auctionArticleId": auctionNowInfo.auctionArticleId,
          "auctionArticleId": 1,
          "price": roundedCurrentPrice || 0
        }
      }).then((res) => {
        const bidder = res.data.data
        getBidderInfo(bidder)
        if (bidder.message === "낙찰 성공") {
          const winnerInfo = {
            message: "success",
            winnerNumber: bidder.winnerNumber,
            auctionArticleId: bidderInfo[0]?.auctionArticleId,
            price: bidder.price
          }
          socket.send(JSON.stringify(winnerInfo));
          console.log(winnerInfo)
        }
      })

      
      //낙찰자 선정될 때까지 집계중 표시
      setCurrentPrice(-1);
    }
  };

  // 낙찰자 정보 가져오기
  const getBidderInfo = (e) => {
    if (currentPrice === -1 && !showSuccessModal) {
      // 낙찰자 정보 갱신
      setBidderInfo(e)
      // 모달 창 오픈
      setShowSuccessModal(true);

      // 5초 딜레이
      setTimeout(() => {
        // 마지막 경매품인지 확인
        if (auctionNextInfo) {
          setShowSuccessModal(false); // 모달 닫기
          setBidderInfo(null); // 낙찰 정보 초기화
        }
      }, 5000);
    }
  }

  // 페이지 마운트될 때
  useEffect(() => {
    // 웹 소켓 링크 설정
    const newSocket = new WebSocket('wss://i9c204.p.ssafy.io/ws/');
    console.log(newSocket, "소켓생성확인")

    // 웹 소켓 열리면 이벤트 핸들러
    newSocket.onopen = () => {
      console.log("웹 소켓 시작");
      setSocket(newSocket);
    };

    // 소켓 들어온 메시지 확인
    newSocket.addEventListener("message", (msg) => {
      const message = JSON.parse(msg.data);
      console.log("경매품목록", message)
      // 최초 경매 시작시 경매물품은 2개 받아옴
      if (!auctionNowInfo && message.auctionNumber) {
        // 현재 경매 물품
        setAuctionNowInfo(message);
        // 다음 경매 물품
        // setAuctionNextInfo(message[1]);
        // 모달창 키값 업데이트
        setKey((prevKey) => prevKey + 1);
        console.log(auctionNowInfo, "지금목록")
      }
      // 최초 이후 경매물품
      else if (auctionNowInfo && !auctionNextInfo && message.auctionNumber) {
        setAuctionNextInfo(message);
        console.log("여기다음목록", auctionNextInfo)
      }
      else if (auctionNowInfo && auctionNextInfo && message.auctionNumber) {
        setAuctionNowInfo(auctionNextInfo);
        setAuctionNextInfo(message);
        setIsBiddingActive(true);
        console.log("다음목록", auctionNextInfo)
      }
      else if (message === "article") {
        // 현재 물품 다음 물품으로 갱신
        setAuctionNowInfo(auctionNextInfo);
        // 다음 물품 갱신
        setAuctionNextInfo(message);
        setKey((prevKey) => prevKey + 1);
      }
      // 관리자가 경매 다음 버튼 누르면 
      else if (message === "start") {
        // 경매 활성화로 바꿈
        setIsBiddingActive(true);
      }
      // 누구든 낙찰버튼 누르면
      else if (message === "press") {
        // 경매 비활성화
        setIsBiddingActive(false);
      }
      // 낙찰자 나오면 
      else if (message === "winner") {
        // 낙찰자 정보 업데이트
        setBidderInfo(message)
      }
      console.log(msg.data)
    });

    newSocket.onclose = (e) => {
      console.log('웹 소켓 종료', e.code);
      console.log('웹 소켓 에러 코드', e);
    };

    // 컴포넌트 언마운트 시 웹 소켓 종료
    return () => {
      newSocket.close();
    };
  }, [memberToken]);


  // // auctionNextInfo가 바뀌면 실행
  // useEffect(() => {
  //   // 경매시작 활성화
  //   setIsBiddingActive(true);
  //   // 경매물품 업데이트
  //   setAuctionNowInfo(auctionNextInfo); // auctionInfoIndex에 해당하는 정보 할당
  //   setKey((prevKey) => prevKey + 1);
  // }, [auctionNextInfo])

  // 경매 물품이 하나씩 갱신되면 실행
  useEffect(() => {
    getNowList(auctionNowInfo)
  }, [auctionNowInfo])

  useEffect(() => {
    if (startPrice !== 0 && currentPrice !== -1) {
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
          setCurrentPrice(-1); // 클릭한 시점의 현재 가격을 집계중으로 바꾸기 위해 -1로 셋팅
        } 
        else if (isBiddingActive && auctionNextInfo) {
          // 입찰 중이고, 마지막 경매 정보가 아니면 3초 뒤에 다음 경매 정보로 업데이트
          const updateTimer = setTimeout(() => {
            // setAuctionNowInfo(auctionNextInfo);
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
  },[isBiddingActive])



  return (
    <div>
      <div className='auction-border'>
        <div className='auction-status'>
          경매중
        </div>
        <div className='auction-container'>
          <div className='auction-graph-info-container'>
            <div className='graph-container'>
              <DoughnutChart isBiddingActive={ isBiddingActive } key={key}/>
            </div>
            <div className='auction-status-info'>
              <div className='info-row'>
                <span className='info-title'>출하량</span>
                  <span className='red-text'>{ auctionNowInfo?.count }</span>
              </div>
              <div className='info-row'>
                <span className='info-title'>출하자</span>
                <span className='info-data'>{ auctionNowInfo?.shipper }</span>
              </div>
              <div className='info-row'>
                <span className='info-title'>품명</span>
                <span className='info-data'>{ auctionNowInfo?.type } / { auctionNowInfo?.name }</span>
              </div>
              <div className='info-row'>
                <span className='info-title'>등급</span>
                <span className='info-data'>{ auctionNowInfo?.grade }</span>
              </div>
              <div className='info-row'>
                <span className='info-title'>현재가</span>
                <span className='info-data'>
                  {currentPrice === -1 ? (
                    <span className='red-text'>집계중</span>
                  ) : (
                    <span className='red-text'>
                      {Math.floor(isBiddingActive ? currentPrice / 10 : clickedPriceRef.current / 10) * 10}
                    </span>
                  )}
                </span>
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
                  { bidderInfo?.memberToken }
                </span>  
              </div> 
              <div>
                <span className='info-title'>
                  낙찰단가 
                </span>
                <span className='red-text'>
                  { bidderInfo?.price }
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
                  {auctionNextInfo?.type + ' /'} {auctionNextInfo?.name}
                </span>
              </div>
              <div>
                <span className='info-title'>
                  등급 
                </span>
                <span className='red-text'>{auctionNextInfo?.grade}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className='button-container'>
        {/* <div>
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
        </div> */}
        <div>
          <button className='bidding-button' onClick={handleBiddingButtonClick}>
            입찰하기
          </button>
        </div>
      </div>
       {showSuccessModal && (
        <AuctionModal modalMessage={bidderInfo.message} onClose={() => setShowSuccessModal(false)} />
      )}
    </div>
  );
}

export default WebSocketComponent;
