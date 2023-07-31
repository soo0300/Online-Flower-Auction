import { GridColDef } from '@mui/x-data-grid';

const columns: GridColDef[] = [
  { field: 'flower', headerName: '품목' },
  { field: 'variety', headerName: '품종' },
  { field: 'col3', headerName: '등급'},
  { field: 'col4', headerName: '단(속)'},
  { field: 'col5', headerName: '낙찰단가'},
  { field: 'col6', headerName: '낙찰시간'},
  { field: 'location', headerName: '지역' },
];

export default columns;