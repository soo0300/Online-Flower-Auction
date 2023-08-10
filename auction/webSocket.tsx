import React, { useEffect, useState } from 'react'

const WebSocketComponent = () => {
  // 소켓 설정
  const [socket, setSocket] = useState(null);
  const [auctionInfos, setAuctionInfos] = useState([]);
  // const [auctionInfoIndex, setAuctionInfoIndex] = useState(0);
  // console.log(secureLocalStorage.getItem("memberkey"))
  const memberToken = secureLocalStorage.getItem("memberkey");
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  // const [modalMessage, setModalMessage] = useState('');
  const [bidderInfo, setBidderInfo] = useState<BidderInfo>([]);
  // const [key, setKey] = useState(0);


  // 경매 정보 
  const [auctionInfo, setAuctionInfo] = useState(auctionInfos[0]);

  // 원래 가격과 최종 가격을 지정
  const originalPrice = auctionInfo.startPrice;
  console.log('원래가격', originalPrice)
  const finalPrice = originalPrice / 2; // 최종 가격은 원래 가격의 1/2로 설정

  // 현재가를 변화시킬 state
  const [currentPrice, setCurrentPrice] = useState(originalPrice);
  // const [isBiddingActive, setIsBiddingActive] = useState(true);
  // console.log('현재가', currentPrice)
  // const clickedPriceRef = useRef(null);
  // const animationFrameRef = useRef(null);

  // 경매품 목록
  const getArticles = (articles) => {
    const articleLists = JSON.parse(articles);
    setAuctionInfo(articleLists);
  }

  // 입찰 메시지 받으면 실행함수
  const getMessage = (message) => {
    const messages = JSON.parse(message);
    setBidderInfo(messages);
    // 낙찰 모달 띄우기
    setShowSuccessModal(true);

    setTimeout(() => {
      setShowSuccessModal(false); // 모달 닫기
      setBidderInfo([]); // 낙찰 정보 초기화
    }, 5000) // 5초 후 모달 닫고 다음 경매 정보로 업데이트
  }

  useEffect(() => {
    // 웹소켓 링크 설정
    const newSocket = new WebSocket("ws://i9c103.p.ssafy.io");

    // 웹소켓 열리면 이벤트 핸들러
    newSocket.onopen = () => {
      console.log("웹소켓 시작")
      setSocket(newSocket);
    };

    // 웹소켓으로 데이터 받을 때의 이벤트 핸들러
    newSocket.onmessage = (e) => {
      console.log(e)
      // 메시지를 받았을 때 실행
      if (e.data.data.code) {
        getArticles(e.data.data)
        console.log('getArticles')
      } else if (e.data.data.message) {
        getMessage(e.data.data)
        console.log('getMessage')
      }
    }

    newSocket.onclose = () => {
      console.log('웹소켓 종료');
    };
    
    // 컴포넌트 언마운튿 되면 웹 소켓 종료
    return () => {
      newSocket.close();
    };
  }, [])

  useEffect(() => {
    // 소켓이 있으면
    if (bidderInfo) {
      // 현재가 10원단위 절삭
      const roundedCurrentPrice = Math.floor(currentPrice / 10) * 10;
      console.log("절삭현재가", roundedCurrentPrice)

      // memberInfo 갱신 후 JSON 변환 후 전송
      const memberInfo = {
        memberToken: "memberToken",
        auctionArticleId: "auctionInfo.auctionArticleId",
        price: roundedCurrentPrice
      };
      socket.send(JSON.stringify(memberInfo));
    }
  }, [bidderInfo]) // bidderInfo가 변경될 때
  

  return (
    <div>webSocket</div>
  )
}

export default WebSocketComponent