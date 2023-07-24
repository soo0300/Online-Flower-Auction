import Navbar from "@/scenes/navbar";
import { useState, useEffect } from "react";
import { SelectedPage } from "./shared/types";



function App() {
  const [selectedPage, setSelectedPage] = useState<SelectedPage>(SelectedPage.Home);
  const [isTop, setIsTop] = useState<boolean>(true);

  useEffect(() => {
    const handleScroll = () => {
      if(window.scrollY === 0) {
        setIsTop(true);
        setSelectedPage(SelectedPage.Home);
      } 

      if(window.scrollY !== 0) {
        setIsTop(false);
      }
    }

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <div className="app bg-gray-20">
      <Navbar 
        isTop = {isTop}  
        selectedPage={selectedPage} setSelectedPage={setSelectedPage} 
      />
    </div>
  )
}

export default App
