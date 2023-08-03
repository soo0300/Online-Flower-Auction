import React, { useState, useEffect, useRef } from 'react';
import { OpenVidu, Session } from 'openvidu-browser';
import CameraOff from '@/assets/cameraOff.png';
import axios from 'axios';

const AuctionWaitingRoom: React.FC = () => {
  const [token, setToken] = useState('');
  const [session, setSession] = useState<Session | null>(null);
  const [subscriber, setSubscriber] = useState(null);
  const [publisher, setPublisher] = useState(null);
  const [isCameraOn, setIsCameraOn] = useState<boolean>(false);
  

  const subscriberContainer = useRef<HTMLDivElement | null>(null);

  // 경매사랑 구매자 구분
  const initSessionAndToken = async () => {
    try {
      // OpenVidu 서버에 세션 생성 요청 보내기
      const sessionResponse = await axios.post('http://i9c204.p.ssafy.io:5000/api/sessions', null, {
        headers: {
          "Content-Type": "application/json",
          // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
        },
      });
      const sessionId = sessionResponse.data;

      // console.log(sessionId)

      // OpenVidu 서버에 토큰 생성 요청 보내기
      const tokenResponse = await axios.post(`http://i9c204.p.ssafy.io:5000/api/sessions/${sessionId}/connections`, null, {
        headers: {
          "Content-Type": "application/json",
          // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
        },
      });
      setToken(tokenResponse.data.split('&')[1].split('=')[1]);
      // console.log(tokenResponse.data)
    }
    catch (error) {
      console.error('Error:', error.response.data);
    }
  }
  
  useEffect(() => {
    initSessionAndToken();
  }, [])

  useEffect(() => {
    console.log(token)
    if (token) {
      const OV =  new OpenVidu()
      const mySession = OV.initSession()
      setSession(mySession)
      mySession.connect(token);
      // console.log('세션이요', mySession);

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
      

      mySession.on('streamCreated', (e) => {
        if (!subscriber && e.stream.connection.connectionId !== mySession.connection.connectionId) {
          setSubscriber(e.stream);
          mySession.publish(publisher);
          console.log('이거이거이거')
          // publisher.addVideoElement(subscriberContainer);
        }
      });

      return () => {
        mySession.disconnect();
        mySession.off('streamCreated');
      };

      // mySession.on('streamCreated', (e) => {
      //   console.log('이벤또', e)
      //   setSubscriber(e.stream);
      //   console.log('구독중', subscriber, 'subVideo')
      //   mySession.publish(publisher)
      //   publisher.addVideoElement(subscriberContainer)
      // })

      // if (publisher) {
      //   publisher.addVideoElement(subscriberContainer);
       
      // }
            
    }
  }, [token, isCameraOn])


  // useEffect(() => {
  //   if (publisher && subscriberContainer) {
  //     // 스트리밍 시작 및 영상 출력
  //     session.publish(publisher);
  //     publisher.addVideoElement(subscriberContainer);
  //   }
  // })

  // 카메라 상태를 토글하는 함수
  const toggleCamera = () => {
    if (publisher) {
      console.log('카메라상태', isCameraOn)
      if (isCameraOn) {
        publisher.publishVideo(false); // 카메라 끄기
      } else {
        publisher.publishVideo(true); // 카메라 켜기
      }
      setIsCameraOn((prev) => !prev); // isCameraOn 상태를 반전시킴
    }
  };

  return (
    <div className="gap-16 bg-gray-20 py-10 pt-[150px] md:h-full md:pb-0">
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
      <div>
        <button onClick={toggleCamera}>{isCameraOn ? '카메라 끄기' : '카메라 켜기'}</button>
      </div>
    </div>
  )
}

export default AuctionWaitingRoom;