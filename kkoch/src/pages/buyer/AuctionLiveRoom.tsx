import { OpenVidu } from 'openvidu-browser';

import axios from 'axios';
import React, { useCallback, useEffect, useReducer, useRef } from 'react';
import UserVideoComponent from '../auction/UserVideoComponent';
import AuctionLiveForm from "../auction/AuctionLiveForm"
import { initialState, videoUserInfo } from '@/reducer/store/videoUser';
import { useLocation, useNavigate } from 'react-router-dom';
import { Navigate } from "react-router-dom"

const APPLICATION_SERVER_URL = 'https://i9c204.p.ssafy.io:8443/api/sessions';

const AuctionLiveRoom = () => {
	const location = useLocation();
	const { auctionArticles, sessionId } = location.state;

	const [state, dispatch] = useReducer(videoUserInfo, initialState);
	const {
		mySessionId,
    myUserName,
    session,
    mainStreamManager,
    publisher,
    subscribers,
  } = state;

	const OV = useRef(new OpenVidu());
	const navigate = useNavigate();
	console.log("여깅기ㅕ이겨미낭겨-----------------------------------")
	console.log(sessionId);

	const handleMainVideoStream = useCallback((stream) => {
		if (mainStreamManager !== stream) {
			dispatch({ type: 'SET_MAIN_STREAM_MANAGER', payload: stream});
		}
	}, [mainStreamManager]);
	

	const joinSession = useCallback(() => {
		const mySession = OV.current.initSession();

		mySession.on('streamCreated', (event) => {
			const subscriber = mySession.subscribe(event.stream, undefined);
			dispatch({ type: 'ADD_SUBSCRIBER', payload: subscriber });
		});

		mySession.on('streamDestroyed', (event) => {
			dispatch({ type: 'REMOVE_SUBSCRIBER', payload: event.stream.streamManager });
		});

		mySession.on('exception', (exception) => {
			console.warn(exception);
		});

		dispatch({ type: 'SET_SESSION', payload: mySession });
		console.log('join세션', state)
	}, []);

	useEffect(() => {
		joinSession();
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

					dispatch({ type: 'SET_MAIN_STREAM_MANAGER', payload: publisher });
					dispatch({ type: 'SET_PUBLISHER', payload: publisher });

					
					navigate('/auction/liveroom', {
						state: {
							mySessionId,
							myUserName,
							session,
							mainStreamManager,
							publisher,
							subscribers,
						}
					});
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
		dispatch({ type: 'RESET_STATE' })
	}, [session]);

	useEffect(() => {
		const handleBeforeUnload = () => {
			leaveSession();
		};
		window.addEventListener('beforeunload', handleBeforeUnload);

		return () => {
			window.removeEventListener('beforeunload', handleBeforeUnload);
		};
	}, [leaveSession]);

	const getToken = useCallback(async () => {
		return createSession(sessionId).then(sessionId =>
			createToken(sessionId),
		);
	}, [mySessionId]);

	const createSession = async (sessionId) => {
		const response = await axios.post(APPLICATION_SERVER_URL, { customSessionId: sessionId }, {
			headers: { 'Content-Type': 'application/json', },
		});
		return response.data; // The sessionId
	};

	const createToken = async (sessionId) => {
		const response = await axios.post(APPLICATION_SERVER_URL + '/' + sessionId + '/connections', {}, {
			headers: { 'Content-Type': 'application/json', },
		});
		console.log(response.data)
		return response.data; // The token
	};

	return (
    <div>
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
          </div>
  
          <hr />
    
          <div className="flex justify-between w-full h-[100%] mt-6">
            <div className=''>
              {subscribers.map((sub, i) => (
                JSON.parse(sub.stream.connection.data).clientData >= 0 && (

									
                  <div className="" onClick={() => handleMainVideoStream(sub)}>
                    <UserVideoComponent streamManager={sub} />
                  </div>
                )
              ))}
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

export default AuctionLiveRoom