import React, { useEffect, useState } from 'react'
import { DataGrid, GridRowParams } from '@mui/x-data-grid';
import TableFilter from './TableFilter';
import './TradingTable.css';
import columns from './TableColumns'
import FilterValues from './TableInterface';
import axios from 'axios';

const TradingTable = () => {

  const todayDate = new Date();
  const weekagoDate = new Date(todayDate.getTime());
  weekagoDate.setDate(weekagoDate.getDate() - 7)

  const formatDate = (date) => {
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    
    day = day >= 10 ? day  : '0' + day;
    month = month >= 10 ? month : '0' + month;
    return `${year}-${month}-${day}`
  }

  // 테이블 데이터 초기 상태
  const [tableData, setTableData] = useState(null);
  const [filteredTableData, setFilteredTableData] = useState([]);
  const [selectCategory, setSelectCategory] = useState('');
  const [isClicked, setIsClicked] = useState(false);
  
  const response = () => {
    axios({
      method: "get",
      url: `/api/api/admin-service/auction-articles/api?startDateTime=${formatDate(weekagoDate)}&endDateTime=${formatDate(todayDate)}&code=${selectCategory}`
    })
    .then((res) => {
      const dataWithId = res.data.data.content.map((item, index) => ({
        ...item,
        id: index + 1, // Assuming IDs start from 1
      }));
      setTableData(dataWithId);
      console.log(tableData)
      setFilteredTableData(dataWithId);
    })
  }
  
  useEffect(() => {
    response();
  },[])

  // TableFilter에서 필터 정보를 받아와서 테이블 데이터를 필터링하는 함수
  const handleFilterChange = (filter: FilterValues) => {
    const filterData = tableData.filter((item) => {
      return (
        ((filter.startDate.format('YYYY-MM-DD') <= formatDate(new Date(item.bidTime))) && 
          (filter.endDate.format('YYYY-MM-DD') >= formatDate(new Date(item.bidTime)))) &&
        (!filter.flower || item.type === filter.flower) &&
        (!filter.variety || item.name === filter.variety) &&
        (!filter.location || item.region === filter.location)
        );
      });
      setFilteredTableData(filterData);
    };

  // 카테고리 클릭
  const handleCategoryClick = (item: string) => {
    setSelectCategory(item);
    setIsClicked(true);
    response();
  };

  const getRowClassName = (params: GridRowParams) => {
    return params.row.bidPrice >= 0 ? 'custom-row-sold' : '';
  }

  return (
    <div className='table-container'>
      <div className='paging-title'>
        <div 
          onClick={() => 
            handleCategoryClick('절화')
          }
          className={isClicked && selectCategory === '절화' ? 'clicked' : ''}
        >
          절화 
        </div>
        <div 
          onClick={() => 
            handleCategoryClick('난')
          }
          className={isClicked && selectCategory === '난' ? 'clicked' : ''}
        >
          난
        </div>
        <div
          onClick={() => 
            handleCategoryClick('관엽')
          }
          className={isClicked && selectCategory === '관엽' ? 'clicked' : ''}
        >
          관엽
        </div>
        <div 
          onClick={() =>
            handleCategoryClick('춘란')
          }
          className={isClicked && selectCategory === '춘란' ? 'clicked' : ''}
        >
          춘란
        </div>
      </div>
      <TableFilter onFilterChange={handleFilterChange} selectedCategory={selectCategory}/>
      <div>
        <div className='table-title'>
          실시간 거래 현황
          <span className='table-units'>
            단위: 원 | 기간: 1주일 단위
          </span>
        </div>
      </div>
      <div className='tablecontent'>
        <div className='datagrid-container'>
          <DataGrid 
            rows={selectCategory ? filteredTableData.filter((item) => item.flower === selectCategory) : filteredTableData}
            columns={columns}
            getRowClassName={ getRowClassName }
            paginationMode='client'
            initialState={{
              pagination: {
                paginationModel: { pageSize: 15, page: 0 }
              }
            }}
            disableColumnMenu
          />
        </div>
      </div>
    </div>
  )
}

export default TradingTable
