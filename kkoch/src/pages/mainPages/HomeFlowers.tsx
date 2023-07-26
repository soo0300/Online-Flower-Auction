import { motion } from "framer-motion";
import HomeFlowerChart from "@/assets/HomeFlowerChart.png"
import ActionButton from "@/components/ActionButton";

const HomeAuction = () => {
  return (
    <section className="mx-auto w-5/6 my-10"> 
      <motion.div>
				{/* HEADER */}
				<motion.div
          className="mx-auto md:my-5 md:w-3/5 flex flex-col items-center"
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.5 }}
          transition={{ duration: 0.5 }}
          variants={{
            hidden: { opacity: 0, x: -50 },
            visible: { opacity: 1, x: 0 },
          }}
        >
          <h1 className="fontFamily-montserrat font-extrabold text-5xl">실시간 꽃 정보</h1>
          <p className="my-5 text-sm">
            실시간 화훼 경매 현황과 시세를 확인하여 
						낙찰 가격을 정해보세요. 
          </p>
					<ActionButton>
              Join Now
					</ActionButton>
        </motion.div>

				<motion.div
					className="mx-auto py-10"
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.5 }}
          transition={{ duration: 0.5 }}
          variants={{
            hidden: { opacity: 0, x: 100 },
            visible: { opacity: 1, x: 0 },
          }}
        >
					<img
						alt="HomeFlowerChart"
						src={HomeFlowerChart}
					/>
				</motion.div>
			</motion.div>
    </section>
  )
}

export default HomeAuction