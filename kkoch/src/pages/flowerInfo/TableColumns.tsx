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
  { field: 'flower', headerName: '품목', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
  { field: 'variety', headerName: '품종',flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
  { field: 'col3', headerName: '등급', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'col4', headerName: '단(속)', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'col5', headerName: '낙찰단가', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'col6', headerName: '낙찰시간', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell},
  { field: 'location', headerName: '지역', flex: 1, headerAlign: 'center', align: 'center', renderCell: CustomLinkCell },
];

export default columns;