import Navbar from "@/pages/navbar";
import HomeAuction from "@/pages/mainPages/HomeAuction";
import HomeFlowers from "@/pages/mainPages/HomeFlowers";
import TodayFlower from "@/pages/mainPages/TodayFlower";

function App() {
  return (
    <div className="app bg-gray-20">
      <Navbar />
      <HomeAuction/>
      <HomeFlowers/>
      <TodayFlower/>
    </div>
  )
}

export default App
