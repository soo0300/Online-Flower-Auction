import React from 'react'
import './FlowerPrice.css'

const FlowerPrice = () => {
  const LowerPrice : number = 1605
  const HigherPrice : number = 4098
  const AveragePrice : number = 2831

  return (
    <div className='price-container'>
      <div className="lower-price-title">
        <p>당일 최저가</p>
        <p className="lower-price-tag">
          ￦{ LowerPrice }
          <span className="lower-price-percent">
            36%↑
          </span>
        </p>
      </div>
      <div className="higher-price-title">
        <p>당일 최고가</p>
        <p className="higher-price-tag">
          ￦{ HigherPrice }
          <span className="higher-price-percent">
            14%↑
          </span>
        </p>
      </div>
      <div className="average-price-title">
        <p>평균가</p>
        <p className="average-price-tag">
          ￦{ AveragePrice }
          <span className="average-price-percent">
            36%↑
          </span>
        </p>
      </div>
    </div>
  )
}

export default FlowerPrice