import React, { useEffect, useState } from 'react'
import FlowerPrice from './FlowerPrice';
import FlowerChart from '@/components/chart/FlowerChart';
import './FlowerDetail.css';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const FlowerDetail = () => {
  const location = useLocation();
  const flowerData = location.state?.flowerData;
  const [loading, setLoading] = useState(true);


  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    
    day = day >= 10 ? day  :  day;
    month = month >= 10 ? month : month;
    return `${year}-${month}-${day}`;
  };

  // const getSearchDay = (selection) => {
  //   switch (selection) {
  //     case 'one-week':
  //       return 7;
  //     case 'one_month':
  //       return 30;
  //     case 'six_months':
  //       return 180;
  //     case 'one_year':
  //       return 365;
  //     default:
  //       return 7; // 기본값은 1주일로 설정
  //   }
  // };

  // const series = flowerSeries;
  // const [selection, setSelection] = useState('one-week');

  const [series, setSeries] = useState([]);
  const [todayList, setTodayList] = useState([]);

  const response = () => {
    axios({
      url: '/api/api/admin-service/stats',
      // url: 'https://i9c204.p.ssafy.io/api/admin-service/stats',
      method: 'get',
      params: {
        type: flowerData.type,
        name: flowerData.name,
        searchDay: 365
      }
    })
    .then((res) => {
      const rawData = res.data.data;
      console.log(rawData)
      // 데이터를 grade별로 나누기
      const dataByGrade = {}; // grade별 데이터를 담을 객체

      // rawData를 순회하며 grade별로 데이터를 분류
      rawData.forEach(item => {
        const { grade } = item;
        
        if (formatDate(new Date(item.createdDate)) === "2023-8-7" && item.grade === "SUPER") {
          setTodayList(item);
          setLoading(false);
          // console.log("11111", todayList)
        }
        if (!dataByGrade[grade]) {
          dataByGrade[grade] = [];
        }
        dataByGrade[grade].push(item);
      });

      // console.log("!!!!", formatDate(new Date(rawData[0].createdDate)))
      // console.log("오늘데이터", todayList)
      
      setSeries([
        { 
          name: "보통",
          data: dataByGrade["NORMAL"] ? dataByGrade["NORMAL"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
          color: "#fba1b7",
        },
        { 
          name: "상급",
          data: dataByGrade["ADVANCED"] ? dataByGrade["ADVANCED"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
          color: "#CBC3D2",
        },
        { 
          name: "특급",
          data: dataByGrade["SUPER"] ? dataByGrade["SUPER"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
          color: "#ffdbaa",
        },
      ]);
      
      console.log("가져올때 시리즈", series)
    })
    .catch(err => {
      console.log(err)
    })
  }
  
  useEffect(() => {
    response();
  }, [])

  return (
    <div className='gap-24 bg-gray-20 pt-3 pl-10 pr-10 md:h-full md:pb-0'>
      <div className="detail-title">
        {flowerData.type}
        <span className='detail-subtitle'>
          {flowerData.name}
        </span>
      </div>
      <div>
        {loading ? (
          <div>Loading...</div>
        ) : (
          <>
            <FlowerPrice todayList={todayList} />
          </>
        )}
      </div>
      <div className='chart-container'>
          <FlowerChart flowerSeries = { series } />
        </div>
    </div>
  )
}

export default FlowerDetail