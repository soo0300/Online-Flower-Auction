import { MDBCard, MDBCardBody, MDBCardTitle } from "mdb-react-ui-kit";
import { DataGrid, GridRowsProp, GridColDef, gridClasses } from '@mui/x-data-grid';
import { alpha, styled } from '@mui/material/styles';
import MyReservModal from "./MyReservModal";

const ODD_OPACITY = 0.2;

const StripedDataGrid = styled(DataGrid)(({ theme }) => ({
  [`& .${gridClasses.row}.even`]: {
    backgroundColor: theme.palette.grey[200],
    '&:hover, &.Mui-hovered': {
      backgroundColor: alpha(theme.palette.primary.main, ODD_OPACITY),
      '@media (hover: none)': {
        backgroundColor: 'transparent',
      },
    },
    '&.Mui-selected': {
      backgroundColor: alpha(
        theme.palette.primary.main,
        ODD_OPACITY + theme.palette.action.selectedOpacity,
      ),
      '&:hover, &.Mui-hovered': {
        backgroundColor: alpha(
          theme.palette.primary.main,
          ODD_OPACITY +
            theme.palette.action.selectedOpacity +
            theme.palette.action.hoverOpacity,
        ),
        // Reset on touch devices, it doesn't add specificity
        '@media (hover: none)': {
          backgroundColor: alpha(
            theme.palette.primary.main,
            ODD_OPACITY + theme.palette.action.selectedOpacity,
          ),
        },
      },
    },
  },
}));


const rows: GridRowsProp = [
  { id: 1, col1: '1', col2: '장미', col3: '하젤', col4: '특2', col5: '양재', col6: '10', col7: '1000', col8: '취소'},
  { id: 2, col1: '2', col2: '장미', col3: '스프레이', col4: '특2', col5: '광주', col6: '20', col7: '2500', col8: '취소'},
  { id: 3, col1: '3', col2: '국화', col3: '다이아몬드', col4: '특2', col5: '양재', col6: '20', col7: '12000', col8: '취소'},
  { id: 4, col1: '4', col2: '수국', col3: '베레나핑크', col4: '중3', col5: '부산', col6: '30', col7: '3800', col8: '취소'},
  { id: 5, col1: '5', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '취소'},
];

const columns: GridColDef[] = [
  { field: 'col1', headerName: 'No', width: 150 },
  { field: 'col2', headerName: '품목', width: 150 },
  { field: 'col3', headerName: '품종', width: 150 },
  { field: 'col4', headerName: '등급', width: 150 },
  { field: 'col5', headerName: '지역', width: 150 },
  { field: 'col6', headerName: '단수', width: 150 },
  { field: 'col7', headerName: '가격', width: 150 },
  { field: 'col8', headerName: '예약 관리', width: 150 },
];


const MyReservation = () => {
  return (
    <MDBCard>
      <MDBCardBody>
        <MDBCardTitle className="mb-4 font-bold flex justify-between items-start">
          <p>화훼 예약 목록</p>
          <MyReservModal></MyReservModal>
        </MDBCardTitle>

        <div style={{ height: 400, width: '100%' }}>
          <StripedDataGrid
            rows={rows} columns={columns}
            getRowClassName={(params) =>
              params.indexRelativeToCurrentPage % 2 === 0 ? 'even' : 'odd'
            }
          />
        </div>

      </MDBCardBody>
    </MDBCard>
  )
}

export default MyReservation