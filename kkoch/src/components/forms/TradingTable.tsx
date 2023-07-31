import React from 'react'
import { DataGrid, GridColDef, GridColumnMenu, GridColumnMenuProps, GridRowsProp } from '@mui/x-data-grid';

const TradingTable = () => {

  const rows: GridRowsProp = [
    { id: 1, col1: '장미', col2: '스프린트', col3: '특', col4: '30', col5: '3,000', col6: '양재' },
    { id: 2, col1: '장미', col2: '스프린트', col3: '특', col4: '20', col5: '2,500', col6: '양재' },
    { id: 3, col1: '장미', col2: '스프린트', col3: '특', col4: '10', col5: '2,000', col6: '양재' },
    { id: 4, col1: '장미', col2: '스프린트', col3: '특', col4: '15', col5: '1,500', col6: '양재' },
    { id: 5, col1: '장미', col2: '스프린트', col3: '특', col4: '10', col5: '5,000', col6: '양재' },
  ];

  const columns: GridColDef[] = [
  { field: 'col1', headerName: '품목', width: 100 },
  { field: 'col2', headerName: '품종', width: 100 },
  { field: 'col3', headerName: '등급', width: 100 },
  { field: 'col4', headerName: '단(속)', width: 100 },
  { field: 'col5', headerName: '낙찰단가', width: 100 },
  { field: 'col6', headerName: '지역', width: 100 },
];


  return (
    <div style={{ height: '100%', width: '100%' }}>
      <DataGrid rows={rows} columns={columns} disableColumnMenu />
    </div>
  )
}

export default TradingTable