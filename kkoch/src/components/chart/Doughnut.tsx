import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

export function DoughnutChart() {
  const [yellow, setYellow] = useState(10); // 초기 노란색 부분 데이터 설정
  const [blue, setBlue] = useState(15); // 초기 노란색 부분 데이터 설정
  const [red, setRed] = useState(20); // 초기 노란색 부분 데이터 설정

  const data = {
    datasets: [
      {
        data: [red, blue, yellow, 100 - yellow - blue ],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)', // red
          'rgba(54, 162, 235, 0.2)', // blue
          'rgba(255, 206, 86, 0.2)', // yellow
          'rgba(0, 0, 0, 0)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)', // red
          'rgba(54, 162, 235, 1)', // blue
          'rgba(255, 206, 86, 1)', // yellow
          'rgba(0, 0, 0, 0)'
        ],
        borderWidth: 0.5,
      },
    ],
  };

  const options = {
    rotation: 230, // 도넛 차트를 회전
  };

  // 시간이 지남에 따라 노란색 부분 데이터 업데이트
  useEffect(() => {
    const yellowInterval = setInterval(() => {
      // 노란색 부분 데이터가 1씩 줄어들도록 설정 (여기에서는 1로 가정)
      setYellow((prevData) => Math.max(prevData - 2, 0));
    }, 1000); // 1초마다 업데이트 (원하는 시간 간격으로 변경 가능)

    const BlueInterval = setInterval(() => {
      // 노란색 부분 데이터가 1씩 줄어들도록 설정 (여기에서는 1로 가정)
      if (yellow === 0 && blue > 0) {
        setBlue((prevData) => Math.max(prevData - 2, 0));
      }
    }, 1000); // 1초마다 업데이트 (원하는 시간 간격으로 변경 가능)

    const RedInterval = setInterval(() => {
      // 노란색 부분 데이터가 1씩 줄어들도록 설정 (여기에서는 1로 가정)
      if (yellow === 0 && blue === 0 && red > 0) {
        setRed((prevData) => Math.max(prevData - 2, 0));
      }
    }, 1000); // 1초마다 업데이트 (원하는 시간 간격으로 변경 가능)
    

    return () => {
      clearInterval(yellowInterval)
      clearInterval(BlueInterval)
      clearInterval(RedInterval)
    }; // 컴포넌트가 unmount될 때 interval 정리
  }, [yellow, blue, red]);

  return <Doughnut data={data} options={options} />;
}
