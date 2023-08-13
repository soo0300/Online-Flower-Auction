import HomePageAuction from "@/assets/HomePageAuction.png";
import MiraeLogo from "@/assets/MiraeLogo.png";
import NongHyupLogo from "@/assets/NongHyupLogo.png";
import SSAFYLogo from "@/assets/SSAFYLogo.png";
import SSAFY from "@/assets/SSAFY.png";
import SamsungLogo from "@/assets/SamsungLogo.png";
import Logo from "@/assets/logo.png";
import SAMSUNG from "@/assets/SAMSUNG.png";
import atflowerlogo from "@/assets/atflowerlogo.png"
import atcenter from "@/assets/atcenter.png"
import { motion } from "framer-motion";
import useMediaQuery from "@/hooks/useMediaQuery";
import Slider from "react-styled-carousel";
import ActionButton from "@/components/buttons/ActionButton";

const HomeAuction = () => {

  const responsive = [
    { breakPoint: 1280, cardsToShow: 5 }, // this will be applied if screen size is greater than 1280px. cardsToShow will become 4.
    { breakPoint: 760, cardsToShow: 5 },
  ];
  
  return (
    <section className="gap-16 bg-gray-20 md:h-full md:pb-0"> 
      {/* 경매 소개 부분 */}

      <motion.div
        className="mx-auto w-5/6 items-center justify-center md:flex md:h-5/6"
      >
        <div className="z-10 mt-32 md:basis-3/5">
          <motion.div 
            // 애니메이션 효과
            className="md:-mt-20"
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, amount: 0.5 }}
            transition={{ duration: 1 }}
            variants={{
              hidden: { opacity: 0, x: -50 },
              visible: { opacity: 1, x: 0 },
            }}
          >
            <div className="relative">
              <div className="before:absolute before:-top-20 before:-left-20 before:z-[-1] md:before:content-backgroundImage">
                <p className="fontFamily-montserrat font-extrabold text-5xl">
                  실시간 꽃 경매 시스템
                </p>
              </div>
            </div>
            
            <p className="mt-8 text-sm">
              소개
            </p>
          </motion.div>

          <motion.div 
            className="mt-8 flex items-center gap-8"
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, amount: 0.5 }}
            transition={{ delay: 0.3, duration: 1 }}
            variants={{
              hidden: { opacity: 0, x: -50 },
              visible: { opacity: 1, x: 0 },
            }}
          >
            
            <ActionButton msg={"경매 하러 가기"}/>
          </motion.div>
        </div>
        
        <div className="flex basis-3/5 justify-center md:z-10
          md:ml-40 md:mt-16 md:justify-items-end">
          <img className="my-5" src={HomePageAuction} alt="HomePageAuction" />
        </div>
      </motion.div>
      
      {/* SPONSORS */}
      {useMediaQuery("(min-width:1060px)") && (
        <div className="bg-primary-100 pb-3">
          <div className="mx-auto w-auto h-[130px]">
            <Slider 
              className="h-[100px]"
              slidesPerPage={4}
              responsive={responsive}
              autoPlay={2000}
              showArrows={false}
              showDots={false}
            >
              <img alt="MiraeLogo" src={MiraeLogo} className="p-[50px] relative h-[150px]"/>
              <img alt="SSAFYLogo" src={SSAFYLogo} className="p-[30px] relative h-[150px]"/>
              <img alt="SamsungLogo"  src={SamsungLogo} className="p-[50px] ml-[-50px] relative h-[150px]"/>
              <img alt="SAMSUNG" src={SAMSUNG} className="p-[40px] relative h-[150px]"/>
              <img alt="SSAFY" src={SSAFY} className="p-[30px] relative h-[150px]"/>
              <img alt="NongHyupLogo" src={NongHyupLogo} className="p-[50px] relative h-[150px]"/>
              <img alt="Logo"  src={Logo} className="p-[10px] relative h-[150px]"/>
              <img alt="atflowerlogo"  src={atflowerlogo} className="ml-[-70px] p-[10px] relative h-[150px]"/>
              <img alt="atcenter"  src={atcenter} className="ml-[-50px] p-[10px] relative h-[150px]"/>
            </Slider>
          </div>
        </div>
      )}
    </section>
  )
}

export default HomeAuction