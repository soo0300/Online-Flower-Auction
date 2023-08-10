import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface VideoAdminState {
  auctionSession: string | null;
}

// 초기 상태
const initialState: VideoAdminState = {
  auctionSession: null,
};

const videoAdminSlice = createSlice({
  name: 'videoAdmin',
  initialState,
  reducers: {
    setAuctionSession : (state, action: PayloadAction<object>) => {
      console.log("===============================")
      console.log(action.payload)
      state.auctionSession = action.payload["session"];
    },
  },
});


export const { setAuctionSession } = videoAdminSlice.actions;
export default videoAdminSlice.reducer;
