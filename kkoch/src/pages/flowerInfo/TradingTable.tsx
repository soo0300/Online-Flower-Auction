import { useEffect, useState } from 'react';
import './TradingTable.css';
import { Link } from 'react-router-dom';
import axios from 'axios';

const TradingTable = () => {
  // const formatDate = (timestamp) => {
  //   const date = new Date(timestamp);
  //   const year = date.getFullYear();
  //   let month = date.getMonth() + 1;
  //   let day = date.getDate();
    
  //   day = day >= 10 ? day  :  day;
  //   month = month >= 10 ? month : month;
  //   return `${year}-${month}-${day}`;
  // };

  // const todayDate = formatDate(new Date());

  // const [series, setSeries] = useState([]);
  // const todayList = []

  // const response = () => {
  //   axios({
  //     url: '/api/api/admin-service/stats',
  //     method: 'get',
  //     params: {
  //       type: "장미",
  //       name: "5번가",
  //       searchDay: 365
  //     }
  //   })
  //   .then((res) => {
  //     const rawData = res.data.data;
  //     console.log(rawData)
  //     // 데이터를 grade별로 나누기
  //     const dataByGrade = {}; // grade별 데이터를 담을 객체

  //     // rawData를 순회하며 grade별로 데이터를 분류
  //     rawData.forEach(item => {
  //       const { grade } = item;
        
  //       if (formatDate(new Date(item.createdDate)) === todayDate && item.grade === "SUPER") {
  //         todayList.push(item)
  //         console.log(todayList)
  //       }
  //       if (!dataByGrade[grade]) {
  //         dataByGrade[grade] = [];
  //       }
  //       dataByGrade[grade].push(item);
  //     });

  //     console.log("!!!!", formatDate(new Date(rawData[0].createdDate)))

      
  //     setSeries([
  //       {
  //         data: dataByGrade["ADVANCED"] ? dataByGrade["ADVANCED"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
  //         color: "#ffe7e6",
  //       },
  //       {
  //         data: dataByGrade["NORMAL"] ? dataByGrade["NORMAL"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
  //         color: "#FF9994",
  //       },
  //       {
  //         data: dataByGrade["SUPER"] ? dataByGrade["SUPER"].map(item => [new Date(item.createdDate).getTime(), item.priceAvg]) : [],
  //         color: "#FF9994",
  //       },
  //     ]);
  //   })
  //   .catch(err => {
  //     console.log(err)
  //   })
  // }
  
  // useEffect(() => {
  //   console.log("useEffect is being executed");
  //   response();
  // }, [])

    return (
      <div>
      <div className='paging-title'>
        <Link to="/flowers/절화"
        state={{ code: "절화" }} 
        >
          절화 
        </Link>
        <Link to="/flowers/난"
        state={{ code: "난" }}
        >
          난
        </Link>
        <Link to="/flowers/관엽"
        state={{ code: "관엽" }}
        >
          관엽
        </Link>
        <Link to="/flowers/춘란" 
          state={{ code: "춘란" }}

        >
          춘란
        </Link>
      </div>
      </div>
  )
}

export default TradingTable
