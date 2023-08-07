import { GridColDef } from '@mui/x-data-grid';
import { Link } from 'react-router-dom';

const CustomLinkCell = ({ value, row }) => (
  <Link
    to={`/flowers/info/${row.flower}/${row.variety}`}
    state = {{ flowerData: row }}
  >
    {value}
  </Link>
);

const columns: GridColDef[] = [
  { field: 'type', headerName: '품목', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
  { field: 'name', headerName: '품종',flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
  { field: 'grade', headerName: '등급', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'count', headerName: '단(속)', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'bidPrice', headerName: '낙찰단가', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'bidTime', headerName: '낙찰시간', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'region', headerName: '지역', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
];

export default columns;