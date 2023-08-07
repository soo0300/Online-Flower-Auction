import React, { useState, useEffect, useRef } from 'react';
import { OpenVidu, Session } from 'openvidu-browser';
import CameraOff from '@/assets/cameraOff.png';
import axios from 'axios';
// import Video from '../buyer/VideoRoom';
import { Link } from 'react-router-dom';


const AuctionWaitingRoom: React.FC = () => {
  const [token, setToken] = useState('');
  const [session, setSession] = useState<Session | null>(null);
  const [subscribers, setSubscribers] = useState(null);
  const [publisher, setPublisher] = useState(null);
  const [isCameraOn, setIsCameraOn] = useState<boolean>(false);
  

  const subscriberContainer = useRef<HTMLDivElement | null>(null);

    
  const response = axios.get('/api/api/admin-service/auction-articles/api?startDateTime=2023-08-06&endDateTime=2023-08-06&code=절화&type&name&region&page=0')

  console.log('여기', response)


  const initSessionAndToken = async () => {
    try {
      // OpenVidu 서버에 세션 생성 요청 보내기
      const sessionResponse = await axios.post('https://i9c204.p.ssafy.io:8443/api/sessions', null, {
        headers: {
          "Content-Type": "application/json",
          // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
        },
      });
      const sessionId = sessionResponse.data;

      // OpenVidu 서버에 토큰 생성 요청 보내기
      const tokenResponse = await axios.post(`https://i9c204.p.ssafy.io:8443/api/sessions/${sessionId}/connections`, null, {
        headers: {
          "Content-Type": "application/json",
          // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
        },
      });
      // console.log('포트번호', tokenResponse.data)
      setToken(tokenResponse.data.split('&')[1].split('=')[1]);
    }
    catch (error) {
      console.error('Error:', error.response.data);
    }
  }
  
  useEffect(() => {
    initSessionAndToken();
  }, [])

  useEffect(() => {
    if (token) {
      const OV =  new OpenVidu()
      const mySession = OV.initSession()
      // setSession(mySession)
      mySession.connect(token);

      const publisher = OV.initPublisher(subscriberContainer.current,
        {
          audioSource: undefined, // The source of audio. If undefined default audio input
          videoSource: undefined, // The source of video. If undefined default video input
          publishAudio: true,     // Whether you want to start the publishing with audio unmuted or muted
          publishVideo: true,     // Whether you want to start the publishing with video enabled or disabled
          resolution: '640x480',  // The resolution of your video
          frameRate: 30,          // The frame rate of your videot
          mirror: false           // Whether to mirror your local video or not
        },
        (error) => {
          if (error) {
            console.error('Error Publisher', error)
          } else {
            console.log('initialized')
          }
        }
      );

      setPublisher(publisher);
      setSession(mySession);
     
      return () => {
        // mySession.disconnect();
        mySession.off('streamCreated');
      };         
    }
  }, [token, isCameraOn])

  useEffect(() => {
    if (session) {
      // 이벤트 핸들러 등록
      session.on('streamCreated', handleStreamCreated);
      session.on('streamDestroyed', handleStreamDestroyed);
      return () => {
        // 이벤트 핸들러 제거
        session.off('streamCreated', handleStreamCreated);
        session.off('streamDestroyed', handleStreamDestroyed);
      };
    }
  }, [session, publisher]);
  
  // 새로운 스트림 생성 시 구독자 추가
  const handleStreamCreated = (event) => {
    const subscriber = session.subscribe(event.stream, undefined);
    if (!subscriber && event.stream.connection.connectionId !== session.connection.connectionId) {
      // subscribers 배열에 구독자 추가
      setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
      console.log('여기여기구독', subscribers)
    }
  };
  
  // 스트림 제거 시 구독자 제거
  const handleStreamDestroyed = (event) => {
    // event.preventDefault(); // 불필요한 코드입니다.
    // subscribers 배열에서 해당 구독자 제거
    setSubscribers((prevSubscribers) => prevSubscribers.filter((sub) => sub !== event.stream.streamManager));
  };

  // useEffect(() => {
  //   if (session) {
  //     console.log("세션이요", session)
  //     session.on('streamCreated', (e) => {
  //       console.log(1111111);
  //       const subscriber = session.subscribe(e.stream, undefined);
  //       console.log("구독자", subscriber);
  //       if (!subscriber && e.stream.connection.connectionId !== session.connection.connectionId) {
  //         setSubscribers(e.stream);
  //         session.publish(publisher);
  //         console.log('이거이거이거')
  //         publisher.addVideoElement(subscriberContainer);
  //       }
  //     });
  //   }
  // }, [publisher])

  // 카메라 상태를 토글하는 함수
  const toggleCamera = () => {
    if (publisher) {
      // console.log('카메라상태', isCameraOn)
      if (isCameraOn) {
        publisher.publishVideo(false); // 카메라 끄기
      } else {
        publisher.publishVideo(true); // 카메라 켜기
      }
      setIsCameraOn((prev) => !prev); // isCameraOn 상태를 반전시킴
    }
  };

  return (
    <div className="gap-16 bg-gray-20 py-10">
      <div className="mx-auto w-5/6 items-center md:flex">
        <h1>aT화훼 공판장 (양재동) 경매방</h1>
      </div>
      <div className="mx-auto w-5/6 items-center justify-center md:flex md:flex-wrap md:h-5/6">
        {isCameraOn ? (
          <div ref={subscriberContainer} className='w-full flex justify-center'></div>
        ) : (
          <img src={CameraOff} alt="" />
        )}
      </div>
      {/* 구독자들 렌더링 */}
      {subscribers && subscribers.map((sub, index) => (
        <div key={index} ref={subscriberContainer} className='w-full flex justify-center'></div>
      ))}
        <div>
          <button onClick={toggleCamera}>{isCameraOn ? '카메라 끄기' : '카메라 켜기'}</button>
        </div>
        
        <div>
          <Link to='/auction/liveroom'>
            <button>입장하기</button>
          </Link>
        </div>
        {/* <Video></Video> */}
        {/* <input className="btn btn-lg btn-success" name="commit" type="submit" value="입장하기" /> */}
        {/* <Link to='http://localhost:5173/auction/liveroom'>
        </Link> */}
      </div> 
  )
}

export default AuctionWaitingRoom;