import React from 'react'
import TradingTable from './TradingTable'
import axios from 'axios'

const TradingInfo = () => {
  axios.get('/admin-service/trades/1?term=1')
  .then((Response)=>{console.log(Response.data)})
  .catch((Error)=>{console.log(Error)})

  return (

    <div className='gap-24 bg-gray-20 py-28 md:h-full md:pb-0'>
      <div>
        <div>
          실시간 거래 현황
          
        </div>
        <div>
          단위: 원 | 기간: 1주일 단위
        </div>
      </div>
      <TradingTable />
    </div>
  )
}

export default TradingInfo