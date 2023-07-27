import React, { useState, useEffect, useRef } from 'react';
import { OpenVidu, Publisher, Session } from 'openvidu-browser';
import CameraOff from '@/assets/cameraOff.png';

const AuctionWaitingRoom: React.FC = () => {
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [isCameraOn, setIsCameraOn] = useState<boolean>(false);
  const [isMicOn, setIsMicOn] = useState<boolean>(false);
  const [session, setSession] = useState<Session | null>(null);
  const videoRef = useRef<HTMLVideoElement>(null);

  useEffect(() => {
    const initOpenVidu = async () => {
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
      } catch (error) {
        console.error('Error:', error);
      }
    };

    initOpenVidu();
  }, []);

  const toggleCamera = () => {
    if (publisher) {
      const videoEnabled = !publisher.stream.videoActive; // OpenVidu의 API를 사용하여 비디오 활성화 여부 확인
      publisher.publishVideo(videoEnabled); // OpenVidu의 API를 사용하여 비디오 트랙 토글
      setIsCameraOn(videoEnabled); // 카메라 상태를 현재 상태의 반대로 변경
    }
  };
  

  const toggleMic = () => {
    if (publisher) {
      const audioEnabled = publisher.stream.getMediaStream().getAudioTracks()[0].enabled;
      publisher.stream.getMediaStream().getAudioTracks()[0].enabled = !audioEnabled;
      setIsMicOn(!audioEnabled); // 마이크 상태를 현재 상태의 반대로 변경
    }
  };

  const joinSession = () => {
    setIsCameraOn(false);
    setIsMicOn(false);
    setPublisher(null);
    setSession(null);
  };

  return (
    <section className="gap-16 bg-gray-20 py-10 pt-[150px] md:h-full md:pb-0"> 
      <div className="mx-auto w-5/6 items-center md:flex">
        <h1>aT화훼 공판장 (양재동) 경매방</h1>
      </div>

      <div className="mx-auto w-5/6 items-center justify-center md:flex md:flex-wrap md:h-5/6">
        {/* 카메라 테스트 화면 */}
        {publisher && (
          <div className='w-full flex justify-center'>
            {isCameraOn ? (
              <video ref={videoRef} autoPlay playsInline />
            ) : (
              <img src={CameraOff} alt="" />
            )}
          </div>
        )}

        <div>
          {/* 마이크 on/off 버튼 */}
          <button onClick={toggleCamera}>{isCameraOn ? '카메라 끄기' : '카메라 켜기'}</button>
          {/* 마이크 on/off 버튼 */}
          <button onClick={toggleMic}>{isMicOn ? '마이크 끄기' : '마이크 켜기'}</button>
        </div>


        {/* 입장하기 버튼 */}
        {isCameraOn && !session ? (
          <button onClick={joinSession}>화상 통화에 입장하기</button>
        ) : (
          <button disabled>입장하기</button>
        )}
      </div>
    </section>
  );
};

export default AuctionWaitingRoom;