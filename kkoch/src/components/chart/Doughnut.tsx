import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

export function DoughnutChart() {
  const [graph, setGraph] = useState(40); // 초기 그래프 크기 설정
  

  const calculateColor = (redValue) => {
    // 데이터가 25일 때까지는 파란색
    if (redValue >= 25) {
      const hue = 240;
      return `hsl(${hue}, 100%, 50%)`;
    // 데이터가 10일때까지는 노란색
    } else if (redValue >= 10) {
      const hue = 60;
      return `hsl(${hue}, 100%, 50%)`;

    // 그 이하는 빨간색
    } else {
      return `hsl(0, 100%, 50%)`;
    }
  };

  const data = {
    datasets: [
      {
        data: [graph, 100-graph],
        backgroundColor: [
          calculateColor(graph),
          'rgba(0, 0, 0, 0)'
        ],
        borderColor: [
          calculateColor(graph),
          'rgba(0, 0, 0, 0)'
        ],
        borderWidth: 0.5,
      },
    ],
  };

  const options = {
    rotation: 230, // 도넛 차트를 회전
    responsive: false
  };

    const decreaseData = () => {
      setGraph((prevData) => Math.max(prevData - 0.001, 0)); // 예시로 0.5로 변경
      // }
    };
  
    const animate = () => {
      decreaseData();
      requestAnimationFrame(animate);
    };
    
    useEffect(() => {
      const animationId = requestAnimationFrame(animate);
      
      // const stopAnimationTimeout = setTimeout(() => {
      //   cancelAnimationFrame(animationId);
      // }, 10000);
      
      return () => {
        cancelAnimationFrame(animationId);
      }; // 컴포넌트가 unmount될 때 애니메이션 정리
    }, [graph]);

  return <Doughnut data={data} options={options} />;
}
