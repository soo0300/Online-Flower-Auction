import React, { useState } from 'react'
import { DataGrid, GridRowParams } from '@mui/x-data-grid';
import TableFilter from './TableFilter';
import './TradingTable.css';
import columns from './TableColumns'
import { initialRows } from './TableRows';
import FilterValues from './TableInterface';

const TradingTable = () => {
  // 테이블 데이터 초기 상태
  const [tableData, setTableData] = useState(initialRows); 
  const [selectCategory, setSelectCategory] = useState('');
  const [isClicked, setIsClicked] = useState(false);

  // TableFilter에서 필터 정보를 받아와서 테이블 데이터를 필터링하는 함수
  const handleFilterChange = (filter: FilterValues) => {
    const filterData = initialRows.filter((item) => {
      return (
        (!filter.flower || item.flower === filter.flower) &&
        (!filter.variety || item.variety === filter.variety) &&
        (!filter.location || item.location === filter.location)
        );
      });
      console.log(filterData);
      setTableData(filterData);
    };

  const handleCategoryClick = (item: string) => {
    setSelectCategory(item);
    setIsClicked(true);
  };

  const getRowClassName = (params: GridRowParams) => {
    return params.row.col4 >= 0 ? 'custom-row-sold' : '';
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
      <TableFilter onFilterChange={handleFilterChange} />
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
            rows={selectCategory ? tableData.filter((item) => item.flower === selectCategory) : tableData}
            columns={columns}
            getRowClassName={ getRowClassName }
            disableColumnMenu
          />
        </div>
      </div>
    </div>
  )
}

export default TradingTable
