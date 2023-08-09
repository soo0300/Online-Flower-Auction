import FlowerData from '@/pages/flowerInfo/FilterInterface';
import axios from 'axios';
import React, { useEffect, useState } from 'react'
import ReactApexChart from 'react-apexcharts';
import secureLocalStorage from 'react-secure-storage';

interface ApexChartProps {
  flowerData: FlowerData
}

const FlowerChart: React.FC<ApexChartProps> = ({ flowerData }) => {
  const formatDate = (date) => {
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    
    day = day >= 10 ? day  : '0' + day;
    month = month >= 10 ? month : '0' + month;
    return `${year}-${month}-${day}`
  }

  const response = () => {
    axios({
      url: '/api/api/admin-service/stats',
      method: 'get',
      data: {
        "type": flowerData.type,
        "name": flowerData.name,
        "searchDay": "7"
      }
    })
    .then((res) => {
      console.log(res.data)
      console.log(11111111111)
    })
  }

  useEffect(() => {
    response()
  })

  const series = [
    {
      data:[
        [1, 1]
      ],
      color: "#ffe7e6"
    },
    {
      data: [
        [2,2]
      ],
      color: "#FF9994"
    }
  ];

  const options = {
    chart: {
      id: 'area-datetime',
      type: 'area',
      height: 350,
      zoom: {
        autoScaleYaxis: true
      },
    },

    stroke: {
      // 선 스타일 설정
      colors: ['#E85757', '#FF9994'], // 데이터 배열에 대한 선 색상 (0번 인덱스는 검정색, 1번 인덱스는 빨간색)
      curve: 'smooth', // 곡선형 그래프로 설정
      width: [2, 2], // 데이터 배열에 대한 선 굵기 (0번 인덱스는 2px, 1번 인덱스는 2px)
    },

    dataLabels: {
      enabled: false
    },

    markers: {
      size: 0,
      style: 'hollow',
    },

    xaxis: {
      type: 'datetime',
      min: new Date('01 Mar 2012').getTime(),
      tickAmount: 6,
    },
    tooltip: {
      x: {
        format: 'dd MMM yyyy'
      }
    },
    fill: {
      type: 'gradient',
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.7,
        opacityTo: 0.9,
        stops: [0, 100]
      }
    },
  };

  const [selection, setSelection] = useState('one_year');

  const updateData = (timeline: string) => {
    let minTimestamp, maxTimestamp;
    switch (timeline) {
      case 'one_month':
        minTimestamp = new Date('28 Jan 2013').getTime();
        maxTimestamp = new Date('27 Feb 2013').getTime();
        break;
      case 'six_months':
        minTimestamp = new Date('27 Sep 2012').getTime();
        maxTimestamp = new Date('27 Feb 2013').getTime();
        break;
      case 'one_year':
        minTimestamp = new Date('27 Feb 2012').getTime();
        maxTimestamp = new Date('27 Feb 2013').getTime();
        break;
      case 'ytd':
        minTimestamp = new Date('01 Jan 2013').getTime();
        maxTimestamp = new Date('27 Feb 2013').getTime();
        break;
      case 'all':
        minTimestamp = new Date('23 Jan 2012').getTime();
        maxTimestamp = new Date('27 Feb 2013').getTime();
        break;
      default:
        break;
    }
  
    // Get the min and max values of the y-axis data for the selected time range
    const filteredData = series[0].data.filter(
      ([timestamp]) => timestamp >= minTimestamp && timestamp <= maxTimestamp
    );
    const yValues = filteredData.map(([, value]) => value);
    const minYValue = Math.min(...yValues);
    const maxYValue = Math.max(...yValues);
  
    // Update the chart with new x-axis and y-axis ranges
    ApexCharts.exec('area-datetime', 'zoomX', minTimestamp, maxTimestamp);
    ApexCharts.exec('area-datetime', 'updateOptions', {
      yaxis: {
        min: minYValue - 1,
        max: maxYValue + (maxYValue - minYValue), // Add a buffer for the y-axis range
      },
    });
  };
  useEffect(() =>  {
    updateData(selection);
  }, [selection]);

  return (
    <div id="chart">
      <div className="toolbar">
        <button 
          id="one_month"
          onClick={()=> {
            updateData('one_month');
            setSelection('one_month');
          }}
          className={ (selection==='one_month' ? 'active' : '')}
        >
          1개월
        </button>
        <button 
          id="six_months"
          onClick={()=> {
            updateData('six_months');
            setSelection('six_months');
          }}
          className={(selection==='six_months' ? 'active' : '')}
        >
          6개월
        </button>
        <button
          id="one_year"
          onClick={()=> {
            updateData('one_year');
            setSelection('one_year');
          }}
          className={ (selection==='one_year' ? 'active' : '')}
        >
          1년
        </button>
        <button
          id="ytd"
          onClick={()=> {
            updateData('ytd');
            setSelection('ytd');
          }}
          className={ (selection==='ytd' ? 'active' : '')}
        >
          올해
        </button>
        <button
          id="all"  
          onClick={()=> {
            updateData('all');
            setSelection('all');
          }}
          className={ (selection==='all' ? 'active' : '')}
        >
          전체
        </button>
      </div>
      <div id="chart-timeline">
        <ReactApexChart options={options} series={series} type="area" height={350} />
      </div>
    </div>
    );
  };

export default FlowerChart;