import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // 로컬 스토리지 사용
import authReducer from './authSlice';

const rootReducer = combineReducers({
  // 여기에 각 리듀서를 추가하세요 (ex: authReducer, ... )
  auth: authReducer,
});

// Redux-persist 설정
const persistConfig = {
  key: 'root', // localStorage에 저장될 키 이름
  storage, // 사용할 스토리지 객체 (여기서는 로컬 스토리지 사용)
  whitelist: ['auth'], // 보존할 리듀서 이름 (여기서는 'auth' 리듀서만 보존)
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
  reducer : persistedReducer
})


export default store 
export const persistor = persistStore(store); // persistor를 생성합니다.
export type RootState = ReturnType<typeof store.getState>;