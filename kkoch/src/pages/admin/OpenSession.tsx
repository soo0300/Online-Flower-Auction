import { OpenVidu } from 'openvidu-browser';
// import SocketIO from "socket.io";

import axios from 'axios';
import React, { useCallback, useEffect, useRef, useState } from 'react';

import UserVideoComponent from './UserVideoComponent';

export default function OpenSession() {
  const [mySessionId, setMySessionId] = useState('YangJae1')
  const [myUserName, setMyUserName] = useState(`관리자${Math.floor(Math.random() * 100)}`)
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [currentVideoDevice, setCurrentVideoDevice] = useState(null);
  
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
  }, [mainStreamManager]);

  // 웹 소캣에 전송할 메세지 만들기
  function makeMessage(type1, type2) {
    const msg = {
      sessionId: type1,
      admin: type2 
    };
    return JSON.stringify(msg);
  } 
  
  // 세션 열기
  const joinSession = useCallback(() => {
    const mySession = OV.current.initSession();
    // 웹 소캣도 연결
    const socket = new WebSocket('wss://i9c204.p.ssafy.io/ws/');


    socket.addEventListener('open', () => {
      console.log("---------------------------------")
      console.log('WebSocket connected'); // 웹소켓 연결 확인 메시지 출력

      // 서버에 들어가자마자 openVidu 방 코드 전송
      socket.send(makeMessage(mySessionId, "관리자"));
    });

    mySession.on('streamCreated', (event) => {
        const subscriber = mySession.subscribe(event.stream, undefined);
        setSubscribers((subscribers) => [...subscribers, subscriber]);
    });

    mySession.on('streamDestroyed', (event) => {
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
    if (session) {
        session.disconnect();
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

  const switchCamera = useCallback(async () => {
    try {
      const devices = await OV.current.getDevices();
      const videoDevices = devices.filter(device => device.kind === 'videoinput');

      if (videoDevices && videoDevices.length > 1) {
        const newVideoDevice = videoDevices.filter(device => device.deviceId !== currentVideoDevice.deviceId);

        if (newVideoDevice.length > 0) {
          const newPublisher = OV.current.initPublisher(undefined, {
            videoSource: newVideoDevice[0].deviceId,
            publishAudio: true,
            publishVideo: true,
            mirror: true,
          });

          if (session) {
            await session.unpublish(mainStreamManager);
            await session.publish(newPublisher);
            setCurrentVideoDevice(newVideoDevice[0]);
            setMainStreamManager(newPublisher);
            setPublisher(newPublisher);
          }
        }
      }
    } catch (e) {
        console.error(e);
    }
  }, [currentVideoDevice, session, mainStreamManager]);

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
    const handleBeforeUnload = (event) => {
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
              <div id="img-div">
                <img src="" alt="OpenVidu logo" />
              </div>
              <div id="join-dialog" className="jumbotron vertical-center">
                <h1> Join a video session </h1>
                <form className="form-group" onSubmit={joinSession}>
                  <p>
                    <label>Participant: </label>
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
                    <label> Session: </label>
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
                    <input className="btn btn-lg btn-success" name="commit" type="submit" value="JOIN" />
                  </p>
                </form>
              </div>
            </div>
          ) : null}

          {session !== undefined ? (
            <div id="session">
              <div id="session-header">
                <h1 id="session-title">{mySessionId}</h1>
                <input
                  className="btn btn-large btn-danger"
                  type="button"
                  id="buttonLeaveSession"
                  onClick={leaveSession}
                  value="Leave session"
                />
                <input
                  className="btn btn-large btn-success"
                  type="button"
                  id="buttonSwitchCamera"
                  onClick={switchCamera}
                  value="Switch Camera"
                />
              </div>

              {/* 메인 화면 띄우기 */}
              {mainStreamManager !== undefined ? (
                <div id="main-video" className="col-md-6">
                  <UserVideoComponent streamManager={mainStreamManager} />

                </div>
              ) : null}

              <div id="video-container" className="col-md-6">
                {subscribers.map((sub, i) => (
                  <div key={sub.id} className="p-0 col-md-6 col-xs-6" onClick={() => handleMainVideoStream(sub)}>
                    <span>{sub.id}</span>
                    <UserVideoComponent streamManager={sub} />
                  </div>
                ))}
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