import React from 'react'
import TradingTable from './TradingTable'
// import axios from 'axios'
// import FlowerChart from '@/components/chart/FlowerChart'

const TradingInfo = () => {
  // axios.get('/admin-service/trades/1?term=1')
  // .then((Response)=>{console.log(Response.data)})
  // .catch((Error)=>{console.log(Error)})

  return (

    <div className='gap-24 bg-gray-20 md:h-full md:pb-0'>
      <TradingTable />
      {/* <FlowerChart /> */}
    </div>
  )
}

export default TradingInfo
