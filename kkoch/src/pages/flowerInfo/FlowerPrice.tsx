import React from 'react'
import './FlowerPrice.css'

const FlowerPrice = ({ todayList }) => {
  const LowerPrice : number = todayList.priceMin
  const HigherPrice : number = todayList.priceMax
  const AveragePrice : number = todayList.priceAvg

  console.log("오늘가격", todayList)

  return (
    <div className='price-container'>
      <div className="lower-price-title">
        <div className='div-price-title'>오늘 최저가</div>
        <p className="lower-price-tag">
          ￦{ LowerPrice }
          {/* <span className="lower-price-percent">
            36%↑
          </span> */}
        </p>
      </div>
      <div className="higher-price-title">
        <div className='div-price-title'>오늘 최고가</div>
        <p className="higher-price-tag">
          ￦{ HigherPrice }
          {/* <span className="higher-price-percent">
            14%↑
          </span> */}
        </p>
      </div>
      <div className="average-price-title">
        <div className='div-price-title'>오늘 평균가</div>
        <p className="average-price-tag">
          ￦{ AveragePrice }
          {/* <span className="average-price-percent">
            36%↑
          </span> */}
        </p>
      </div>
    </div>
  )
}

export default FlowerPrice