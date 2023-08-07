const initialState = {
  mySessionId: 'SessionA',
  myUserName: `Participant${Math.floor(Math.random() * 100)}`,
  session: undefined,
  mainStreamManager: undefined,
  publisher: undefined,
  subscribers: [],
};

const videoUserInfo = (state, action) => {
  switch (action.type) {
    case 'SET_MY_SESSION_ID':
      return { ...state, mySessionId: action.payload };
    case 'SET_MY_USER_NAME':
      return { ...state, myUserName: action.payload };
    case 'SET_SESSION':
      return { ...state, session: action.payload };
    case 'SET_MAIN_STREAM_MANAGER':
      return { ...state, mainStreamManager: action.payload };
    case 'SET_PUBLISHER':
      return { ...state, publisher: action.payload };
    case 'ADD_SUBSCRIBER':
      return { ...state, subscribers: [...state.subscribers, action.payload] };
    case 'REMOVE_SUBSCRIBER':
      return {
        ...state,
        subscribers: state.subscribers.filter((sub) => sub.id !== action.payload.id),
      };
    case 'RESET_STATE':
      return initialState;
    default:
      return state;
  }
};

export { initialState, videoUserInfo };