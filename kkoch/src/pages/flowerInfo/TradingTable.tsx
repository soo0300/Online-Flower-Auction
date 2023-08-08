import './TradingTable.css';
import { Link } from 'react-router-dom';

const TradingTable = () => {
    return (
      <div>
      <div className='paging-title'>
        <Link to="/flowers/절화"
        state={{ code: "절화" }} 
        >
          절화 
        </Link>
        <Link to="/flowers/난"
        state={{ code: "난" }}
        >
          난
        </Link>
        <Link to="/flowers/관엽"
        state={{ code: "관엽" }}
        >
          관엽
        </Link>
        <Link to="/flowers/춘란" 
          state={{ code: "춘란" }}

        >
          춘란
        </Link>
      </div>
      </div>
  )
}

export default TradingTable
