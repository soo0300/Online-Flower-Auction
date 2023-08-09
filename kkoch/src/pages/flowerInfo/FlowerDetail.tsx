import React from 'react'
import FlowerPrice from './FlowerPrice';
import FlowerChart from '@/components/chart/FlowerChart';
import './FlowerDetail.css';
import { useLocation } from 'react-router-dom';

const FlowerDetail = () => {
  const location = useLocation();
  const flowerData = location.state?.flowerData;
  console.log('데이터', flowerData);
  
  return (
    <div className='gap-24 bg-gray-20 pt-3 md:h-full md:pb-0'>
      <div className="detail-title">
        장미
        <span className='detail-subtitle'>
          푸에고
        </span>
      </div>
      <div>
        <FlowerPrice />
      </div>
      <div className='chart-container'>
          <FlowerChart flowerData={ flowerData }/>
        </div>
    </div>
  )
}

export default FlowerDetail