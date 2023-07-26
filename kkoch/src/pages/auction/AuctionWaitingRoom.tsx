import React, { useState, useEffect, useRef } from 'react';
import { OpenVidu, Publisher, Session } from 'openvidu-browser';

const AuctionWaitingRoom: React.FC = () => {
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isCameraTested, setIsCameraTested] = useState<boolean>(false);
  const [isMicTested, setIsMicTested] = useState<boolean>(false);
  const [session, setSession] = useState<Session | null>(null);
  const videoRef = useRef<HTMLVideoElement>(null); // useRef 훅을 사용하여 video 엘리먼트를 참조

  const startCameraTest = async () => {
    const OV = new OpenVidu();
    const mySession = OV.initSession();

    try {
      const publisher = OV.initPublisher('cameraTest', {
        videoSource: undefined, // 기본 비디오 장치 사용
        audioSource: undefined, // 기본 오디오 장치 사용
        publishAudio: true,
        publishVideo: true,
        resolution: '640x480',
        frameRate: 30,
      });
      mySession.publish(publisher);

      setSession(mySession);
      setPublisher(publisher);
      setIsCameraTested(true); // 카메라 테스트 시작 상태로 변경
    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    if (isCameraTested && publisher && videoRef.current) {
      // publisher가 생성된 후에 비디오 엘리먼트에 웹캠 스트림을 할당
      publisher.addVideoElement(videoRef.current);
    }
  }, [isCameraTested, publisher]);


  const startMicTest = () => {
    if (publisher) {
      publisher.publishAudio(true);
      setIsMicTested(true);
    }
  };

  const joinSession = () => {
    setIsCameraTested(false);
    setIsMicTested(false);
    setPublisher(null);
    setSession(null);
  };

  return (
    <section className="gap-16 bg-gray-20 py-10 pt-[150px] md:h-full md:pb-0"> 
      <div className="mx-auto w-5/6 items-center md:flex">
        <h1>aT화훼 공판장 (양재동) 경매방</h1>
      </div>

      <div className="mx-auto w-5/6 items-center justify-center md:flex md:flex-wrap md:h-5/6">
        {/* 카메라 테스트 버튼 */}
        {!isCameraTested && !isMicTested && !session && (
          <button onClick={startCameraTest}>카메라 테스트 시작</button>
        )}

        {/* 카메라 테스트 화면 */}
        {isCameraTested && publisher && (
          <div id="cameraTest" style={{ width: '100%', height: 'auto' }}>
            <video ref={videoRef} autoPlay playsInline /> {/* videoRef를 video 엘리먼트의 ref로 설정 */}
          </div>
        )}

        {/* 마이크 테스트 버튼 */}
        {isCameraTested && !isMicTested && (
          <button onClick={startMicTest}>마이크 테스트 시작</button>
        )}

        {/* 입장하기 버튼 */}
        {isCameraTested && isMicTested && !session && (
          <button onClick={joinSession}>화상 통화에 입장하기</button>
        )}
      </div>
    </section>
  );
};

export default AuctionWaitingRoom;