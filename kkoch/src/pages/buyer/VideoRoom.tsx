import { OpenVidu } from 'openvidu-browser';

import axios from 'axios';
import React, { useCallback, useEffect, useReducer, useRef } from 'react';
import UserVideoComponent from './UserVideoComponent';
import { initialState, videoUserInfo } from '@/reducer/store/videoUser';
import { useNavigate } from 'react-router-dom';

const APPLICATION_SERVER_URL = 'https://i9c204.p.ssafy.io:8443/api/sessions';

export default function Video() {
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
	console.log(mySessionId);

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
		return createSession(mySessionId).then(sessionId =>
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
		<div className="container">
			{session === undefined ? (
				<div id="join">
					<div id="join-dialog" className="jumbotron vertical-center">
					</div>
				</div>
			) : null}

			{session !== undefined ? (
				<div id="session">
					{mainStreamManager !== undefined ? (
						<div id="main-video" className="col-md-6">
							<UserVideoComponent streamManager={mainStreamManager} />
						</div>
					) : null}
					<div id="video-container" className="col-md-6">
						{publisher !== undefined ? (
							<div className="stream-container col-md-6 col-xs-6" onClick={() => handleMainVideoStream(publisher)}>
								<UserVideoComponent
									streamManager={publisher} />
							</div>
						) : null}
					{subscribers.map((sub, i) => (
							<div key={sub.id} className="stream-container col-md-6 col-xs-6" onClick={() => handleMainVideoStream(sub)}>
								<span>{sub.id}</span>
								<UserVideoComponent streamManager={sub} />
							</div>
						))}
					</div>
				</div>
			) : null}
		</div>
	);
}