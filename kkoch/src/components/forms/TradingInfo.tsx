import React from 'react'
import TradingTable from './TradingTable'
// import axios from 'axios'

const TradingInfo = () => {
  // axios.get('/admin-service/trades/1?term=1')
  // .then((Response)=>{console.log(Response.data)})
  // .catch((Error)=>{console.log(Error)})

  return (

    <div className='gap-24 bg-gray-20 py-28 md:h-full md:pb-0'>
      <TradingTable />
    </div>
  )
}

export default TradingInfo
