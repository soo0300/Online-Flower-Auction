import Navbar from "@/pages/navbar";
import HomeAuction from "@/pages/mainPages/HomeAuction";
import HomeFlowers from "@/pages/mainPages/HomeFlowers";
import TodayFlower from "@/pages/mainPages/TodayFlower";
import SignUp from "./pages/user/SignUp";
import Login from "./pages/user/Login";

function App() {
  return (
    <div className="app bg-gray-20">
      <Navbar />
      <HomeAuction/>
      <HomeFlowers/>
      <TodayFlower/>
      <SignUp />
      <Login />
    </div>
  )
}

export default App
