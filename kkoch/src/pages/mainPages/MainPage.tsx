import HomeAuction from "@/pages/mainPages/HomeAuction";
import HomeFlowers from "@/pages/mainPages/HomeFlowers";
import TodayFlower from "@/pages/mainPages/TodayFlower";

function MainPage() {
  return (
    <div className="app bg-gray-20">
      <HomeAuction/>
      <HomeFlowers/>
      <TodayFlower/>
    </div>
  )
}

export default MainPage
