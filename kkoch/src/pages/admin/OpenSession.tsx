import { OpenVidu } from 'openvidu-browser';
// import SocketIO from "socket.io";

import axios from 'axios';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import UserVideoComponent from './UserVideoComponent';
import Clock from '@/components/Clock/Clock';
import { useDispatch } from 'react-redux';

export default function OpenSession() {
  const [mySessionId, setMySessionId] = useState('Yangjae1')
  const [myUserName, setMyUserName] = useState(`관리자${Math.floor(Math.random() * 100)}`)
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [currentVideoDevice, setCurrentVideoDevice] = useState(null);
  
  const videoLog = useRef(null);
  const dispatch = useDispatch();

  const OV = useRef(new OpenVidu());
  
  // 세션 이름 변경
  const handleChangeSessionId = useCallback((e) => {
    setMySessionId(e.target.value);
  }, []);
  
  // 이름 변경
  const handleChangeUserName = useCallback((e) => {
    setMyUserName(e.target.value);
  }, []);
  
  // 메인 화면 변경
  const handleMainVideoStream = useCallback((stream) => {
    if (mainStreamManager !== stream) {
      setMainStreamManager(stream);
    }
    if (mainStreamManager === stream) {
      setMainStreamManager(undefined);
    }
  }, [mainStreamManager]);
  
  const removeMainStreamManager = useCallback(() => {
    if (mainStreamManager) {
      setMainStreamManager(undefined);
    }
  }, [mainStreamManager]);
  
  // 웹 소캣에 전송할 메세지 만들기
  function makeMessage(type1, type2) {
    const msg = {
      sessionId: type1,
      role: type2 
    };
    return JSON.stringify(msg);
  } 
  
  const appendToVideoLog = (text) => {
    if (videoLog.current) {
      videoLog.current.value += text + '\n';
    }
  };

  // 세션 열기
  const joinSession = useCallback(() => {
    const currentSessionId = mySessionId;

    const mySession = OV.current.initSession();
    // 웹 소캣도 연결
    const socket = new WebSocket('wss://i9c204.p.ssafy.io/ws/');
    
    socket.addEventListener('open', () => {
      console.log("---------------------------------")
      console.log('WebSocket connected'); // 웹소켓 연결 확인 메시지 출력

      // 서버에 들어가자마자 openVidu 방 코드 전송
      socket.send(makeMessage(currentSessionId, "admin"));
    });

    mySession.on('streamCreated', (event) => {
      const subscriber = mySession.subscribe(event.stream, undefined);
      const name = JSON.parse(event.stream.connection.data).clientData
			dispatch({ type: 'ADD_SUBSCRIBER', payload: subscriber });

      if (name.indexOf("관리자") === -1) {
        appendToVideoLog(`${name} 번 경매자가 입장 하였습니다.`);
      }
      setSubscribers((subscribers) => [...subscribers, subscriber]);
    });
    
    mySession.on('streamDestroyed', (event) => {
			dispatch({ type: 'REMOVE_SUBSCRIBER', payload: event.stream.streamManager });
      console.log('Stream destroyed:', event.stream.connection.data);
      const name = JSON.parse(event.stream.connection.data).clientData
  
      if (name.indexOf("관리자") === -1) {
        appendToVideoLog(`${name} 번 경매자가 퇴장 하였습니다.`);
      }
      deleteSubscriber(event.stream.streamManager);
    });

    mySession.on('exception', (exception) => {
        console.warn(exception);
    });

    setSession(mySession);

  }, []);

  useEffect(() => {
    if (session) {
      // Get a token from the OpenVidu deployment
      getToken().then(async (token) => {
        try {
          await session.connect(token, { clientData: myUserName });

          const publisher = await OV.current.initPublisherAsync(undefined, {
              audioSource: undefined,
              videoSource: undefined,
              publishAudio: true,
              publishVideo: true,
              resolution: '640x480',
              frameRate: 30,
              insertMode: 'APPEND',
              mirror: false,
          });

          session.publish(publisher);

          const devices = await OV.current.getDevices();
          const videoDevices = devices.filter(device => device.kind === 'videoinput');
          const currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
          const currentVideoDevice = videoDevices.find(device => device.deviceId === currentVideoDeviceId);

          // setMainStreamManager(publisher);
          setPublisher(publisher);
          setCurrentVideoDevice(currentVideoDevice);
        } catch (error) {
          console.log('There was an error connecting to the session:', error.code, error.message);
        }
      });
    }
  }, [session, myUserName]);


  const leaveSession = useCallback(() => {
    // Leave the session
    if (confirm("현재 경매가 진행중입니다. 종료하시겠습니까?") && session) {
      // 모든 구독자(subscribers)를 퇴장시킴
      subscribers.forEach(sub => sub.stream.dispose());
      
      // axios({
      //   method: "DELETE",
      //   url: `https://i9c204.p.ssafy.io:8443/api/sessions/${session}`,
      //   headers: {
      //     "Authorization": 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
      //     "Content-Type": "application/x-www-form-urlencoded",
      //   }
      // })
      // .then(res => {
      //   console.log(res)
      // })
      // .catch(err => {
      //   console.log(err)
      // })
      
      session.disconnect();

      alert("경매가 종료 되었습니다.")
    }

    // Reset all states and OpenVidu object
    OV.current = new OpenVidu();
    setSession(undefined);
    setSubscribers([]);
    setMySessionId('YangJae1');
    setMyUserName('관리자' + Math.floor(Math.random() * 100));
    setMainStreamManager(undefined);
    setPublisher(undefined);
  }, [session]);


  const deleteSubscriber = useCallback((streamManager) => {
    setSubscribers((prevSubscribers) => {
      const index = prevSubscribers.indexOf(streamManager);
      if (index > -1) {
        const newSubscribers = [...prevSubscribers];
        newSubscribers.splice(index, 1);
        return newSubscribers;
      } else {
        return prevSubscribers;
      }
    });
  }, []);

  useEffect(() => {
    const handleBeforeUnload = () => {
      leaveSession();
    };
    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [leaveSession]);

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
  const getToken = useCallback(async () => {
    return createSession(mySessionId).then(sessionId =>
      createToken(sessionId),
    );
  }, [mySessionId]);

  const createSession = async (sessionId) => {
    const response = await axios.post('https://i9c204.p.ssafy.io:8443/api/sessions', { customSessionId: sessionId }, {
      headers: {
        "Content-Type": "application/json",
        'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
      },
    });
    return response.data; // The sessionId
  };

  const createToken = async (sessionId) => {
    const response = await axios.post(`https://i9c204.p.ssafy.io:8443/api/sessions/${sessionId}/connections`, {}, {
      headers: {
        "Content-Type": "application/json",
        'Authorization': 'Basic ' + btoa('OPENVIDUAPP:1q2w3e4r'),
      },
    });
      return response.data; // The token
  };

  return (
    <div className="container">
        {session === undefined ? (
          <div id="join">
            <div id="join-dialog" className="container jumbotron vertical-center">
              <h1 className='my-6 text-center'>양재 경매방 생성하기 </h1>
              <hr />
              <Clock />
              <div className='border-2 border-[#5E0000] rounded-lg w-[70%] mt-[50px] mx-auto'>
                <form className="form-group p-6" onSubmit={joinSession}>
                  <p>
                    <label>관리자 이름: </label>
                    <input
                      className="form-control"
                      type="text"
                      id="userName"
                      value={myUserName}
                      onChange={handleChangeUserName}
                      required
                    />
                  </p>
                  <p>
                    <label> 방 key: </label>
                    <input
                      className="form-control"
                      type="text"
                      id="sessionId"
                      value={mySessionId}
                      onChange={handleChangeSessionId}
                      required
                    />
                  </p>
                  <p className="text-center">
                    <input className="btn btn-lg btn-success" name="commit" type="submit" value="경매방 오픈" />
                  </p>
                </form>
              </div>
            </div>
          </div>
        ) : null}

        {session !== undefined ? (
          <div className='mt-3'>
            <div className='flex justify-between items-center'>
              <h1 id="session-title">{mySessionId}</h1>
              <input
                className="btn btn-large btn-danger"
                type="button"
                id="buttonLeaveSession"
                onClick={leaveSession}
                value="경매 종료"
              />
              {/* <input
                className="btn btn-large btn-success"
                type="button"
                id="buttonSwitchCamera"
                onClick={switchCamera}
                value="Switch Camera"
              /> */}
            </div>

            {/* 메인 화면 띄우기 */}
            {mainStreamManager !== undefined ? (
              <div id="main-video" className="col-md-6" onClick={()=> removeMainStreamManager()}>
                <UserVideoComponent streamManager={mainStreamManager} />

              </div>
            ) : null}

            <hr />
      
            <div className="flex justify-between w-full h-[100%] mt-6">
              <div className='grid grid-cols-4 grid-rows-4 gap-4 w-[60%]'>
                {/* 현재 이 방 참여자들만 뿌려주게해줘 지금 이 반복문이 돌면서 이상한 것들이 자꾸 찎혀 */}
                {subscribers.map((sub, i) => (
                  JSON.parse(sub.stream.connection.data).clientData >= 0 && (
                    <div key={i} onClick={() => handleMainVideoStream(sub)}>
                      <UserVideoComponent streamManager={sub} />
                    </div>
                  )
                ))}
              </div>
              <div className='w-[40%] flex flex-col items-center'>
                <textarea
                  className='mt-3 w-full h-[500px] border-2 border-red-400 rounded-lg bg-white'
                  ref={videoLog}
                  disabled
                >
                  
                </textarea>  
                <button className='mx-auto pt-[50px]'>
                  다음
                </button>
              </div>
            </div>


            {/* {publisher !== undefined ? (
                <div className="stream-container col-md-6 col-xs-6" onClick={() => handleMainVideoStream(publisher)}>
                  <UserVideoComponent
                    streamManager={publisher} />
                </div>
              ) : null} */}
          </div>
        ) : null}
    </div>
  );
}