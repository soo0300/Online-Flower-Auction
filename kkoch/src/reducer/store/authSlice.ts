import { createSlice, PayloadAction } from '@reduxjs/toolkit';
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

      axios({
        method:"get",
        url: `api/api/user-service/${state.memberkey}`,
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
    },
    // 다른 액션들...
  },
});


export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
