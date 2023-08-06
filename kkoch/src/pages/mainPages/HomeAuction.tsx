import HomePageAuction from "@/assets/HomePageAuction.png";
import MiraeLogo from "@/assets/MiraeLogo.png";
import NongHyupLogo from "@/assets/NongHyupLogo.png";
import SSAFYLogo from "@/assets/SSAFYLogo.png";
import SamsungLogo from "@/assets/SamsungLogo.png";
import { motion } from "framer-motion";
import useMediaQuery from "@/hooks/useMediaQuery";

const HomeAuction = () => {
  const sponsors = "w-[20%] mx-6";

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
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. 
              Suscipit sapiente alias rerum provident aspernatur debitis 
              odit nesciunt dignissimos dicta. 
              Vitae ad rem soluta laboriosam doloribus 
              accusamus impedit eaque, amet dolorem!
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
            
            <button>
              Join Now
            </button>
          </motion.div>
        </div>
        
        <div className="flex basis-3/5 justify-center md:z-10
          md:ml-40 md:mt-16 md:justify-items-end">
          <img className="my-5" src={HomePageAuction} alt="HomePageAuction" />
        </div>
      </motion.div>
      
      <div className="border-b-2"></div>

      {/* SPONSORS */}
      {useMediaQuery("(min-width:1060px)") && (
        <div className="h-[139px] w-full bg-primary-100 py-10">
          <div className="mx-auto w-5/6">
            <div className="flex w-3/5 items-center justify-between gap-8">
              <img alt="MiraeLogo" src={MiraeLogo} className={sponsors}/>
              <img alt="NongHyupLogo" src={NongHyupLogo} className={sponsors}/>
              <img alt="SSAFYLogo" src={SSAFYLogo} className={sponsors}/>
              <img alt="SamsungLogo" src={SamsungLogo} className={sponsors}/>
            </div>
          </div>
        </div>
      )}
    </section>
  )
}

export default HomeAuction