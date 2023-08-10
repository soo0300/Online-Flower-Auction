import { useNavigate } from 'react-router-dom';
import './LocationButton.css';
import { useSelector } from 'react-redux';
import { RootState } from '@/reducer/store';

type Props = {
	location: React.ReactNode;
	type: string;
	auctionArticles: object;
}
  

const LocationButton = ({location, type, auctionArticles } : Props) => {
	const navigate = useNavigate();
	const auctionSession = useSelector((state:RootState) => state.videoAdmin.auctionSession);

	// 양재 지역만 활성화 하기 위한 handler
  const handleCheck = () => {
    if (location === "aT화훼공판장(양재동)") {
			if(type==="admin"){
				navigate("/admin/openSession");
			}else if(type==="user"){
				console.log(auctionSession)
				if (auctionSession) {
					// 세션에 참여
					console.log(`세션 ${auctionSession}에 참여합니다.`);
					// 세션 참여 로직 작성
					navigate("/auction/waitingroom", { state: auctionArticles })
				} else {
					alert("경매가 준비 중입니다.");
				}

			}else{
				alert("잘못된 접근입니다.")
			}
    } else {
      alert("서비스 준비중 입니다");
    }
  };

  return (
		<button className="btn mx-6" onClick={handleCheck}>
			<div className="wrapper">
				<p className="text">{location} </p>

				<div className="flower flower1">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
				<div className="flower flower2">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
				<div className="flower flower3">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
				<div className="flower flower4">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
				<div className="flower flower5">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
				<div className="flower flower6">
					<div className="petal one"></div>
					<div className="petal two"></div>
					<div className="petal three"></div>
					<div className="petal four"></div>
				</div>
			</div>
		</button>

  )
}

export default LocationButton