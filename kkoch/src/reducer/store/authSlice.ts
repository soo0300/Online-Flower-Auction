import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import secureLocalStorage  from  "react-secure-storage";

import axios from 'axios';

interface AuthState {
  memberkey: string | null;
  token: string | null;
}

const initialState: AuthState = {
  memberkey: null,
  token: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<object>) => {
      state.memberkey = action.payload["mem_key"];
      state.token = action.payload["mem_token"];

      secureLocalStorage.setItem("memberkey", state.memberkey);
      secureLocalStorage.setItem("token", state.token);
      secureLocalStorage.setItem("loginTime", new Date());
      
      axios({
        method:"get",
        url: `https://i9c204.p.ssafy.io/api/user-service/${state.memberkey}`,
        // url: `/api/api/user-service/${state.memberkey}`,
        headers:{
          Authorization : `Bearer ${state.token}`
        }
      })
      .then((res) =>{
        localStorage.setItem('username', res.data.data["name"]);
				alert(res.data.data["name"] + "님 환영합니다.");
      })
      .catch(err => console.log(err));

    },
    logout: (state) => {
      state.memberkey = null;
      state.token = null;
      localStorage.removeItem('username');
      secureLocalStorage.removeItem("memberkey");
      secureLocalStorage.removeItem("token");
      secureLocalStorage.removeItem("loginTime");
    },
  },
});


export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
