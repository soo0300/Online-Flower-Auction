import React from 'react'
import HomePageAuction from '@/assets/HomePageAuction.png'
import { DoughnutChart } from '../chart/Doughnut';

const AuctionLiveForm = () => {
  // 경매 정보 
  const auctionInfo = {
    point: 100000,
    quantity: 30,
    provider: "홍승준",
    flower: "장미/스프레이",
    grade: "특",
  }; 


  return (
    <div>
      <div>
        at화훼 양재공판장 경매방 경매중!!
      </div>
      <div>
        <div>
          <img src={ HomePageAuction } alt="LiveStream" />
        </div>
        <div>
          <div>
            보유 포인트: { auctionInfo.point }
          </div>
          <div>
            <div>
              경매중
            </div>
            <div>
              <div className='GraphContainer'>
                <DoughnutChart />
              </div>
              <div>
                출하량: { auctionInfo.quantity } <br />
                출하자: { auctionInfo.provider } <br />
                품명: { auctionInfo.flower } <br />
                등급: { auctionInfo.grade} <br />
              </div>
              <div>

              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AuctionLiveForm