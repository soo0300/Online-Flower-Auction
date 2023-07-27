import React from 'react'
import HomePageAuction from '@/assets/HomePageAuction.png'
import { DoughnutChart } from '../chart/Doughnut';
import './AuctionLiveForm.css';

const AuctionLiveForm = () => {
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
      <div className='auction-heading'>
        at화훼 양재공판장 경매방 경매중!!
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
              <div>
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
    </div>
  )
}

export default AuctionLiveForm