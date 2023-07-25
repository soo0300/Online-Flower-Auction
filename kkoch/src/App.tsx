import Navbar from "@/scenes/navbar";
import HomeAuction from "@/scenes/mainPages/HomeAuction";
import HomeFlowers from "@/scenes/mainPages/HomeFlowers";
import TodayFlower from "@/scenes/mainPages/TodayFlower";

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
