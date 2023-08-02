import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  token: string | null;
}

const initialState: AuthState = {
  token: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<string>) => {
      state.token = action.payload;
      console.log(state.token, action.payload)
			localStorage.setItem('token', state.token);
    },
    logout: (state) => {
      state.token = null;
      localStorage.removeItem('token');
    },
    // 다른 액션들...
  },
});


export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
