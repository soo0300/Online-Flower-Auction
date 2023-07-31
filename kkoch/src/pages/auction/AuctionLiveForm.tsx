import React from 'react'
import HomePageAuction from '@/assets/HomePageAuction.png'
import { DoughnutChart } from '@/components/chart/Doughnut'
import './AuctionLiveForm.css';
// import axios from 'axios';


const AuctionLiveForm = () => {

  // const axios = require('axios');



  
  // 경매 정보 
  const auctionInfo = {
    point: 100000, // 보유 포인트
    quantity: 30, // 출하량
    provider: "홍승준", // 출하자
    flower: "장미/스프레이", // 품명
    grade: "특", // 등급
    nowPrice: 5000, // 현재가
    bidPrice: 2000, // 낙찰가
    buyer: "김싸피", // 낙찰자
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
          <img src={ HomePageAuction } alt="LiveStream" />
        </div>
        <div className='auction-info'>
          <div className='auction-point'>
            보유 포인트: { auctionInfo.point }
          </div>
          <div className='auction-border'>
            <div className='favorite-icon'>
              <button className='favorite-button'>
                <svg 
                  xmlns="http://www.w3.org/2000/svg"
                  fill="yellow"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="white"
                  className="w-6 h-6">
                  <path 
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z"
                  />
                </svg>
              </button>
            </div>
            <div className='auction-status'>
              경매중
            </div>
            <div className='auction-container'>
              <div className='graph-container'>
                <DoughnutChart />
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
                현재가: { auctionInfo.nowPrice} <br />
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
          <button className='bidding-button'>입찰하기</button>
        </div>
      </div>
    </div>
  )
}

export default AuctionLiveForm
