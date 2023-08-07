import { useEffect, useState } from 'react';
import { MDBCard, MDBCardBody, MDBCardTitle } from "mdb-react-ui-kit";
import {GridRowsProp, GridColDef } from '@mui/x-data-grid';
import StripedDataGrid from "../stripedDataGrid";
import './MyAuctionList.css';
import axios from 'axios';

function MyAuctionList() {
  const initialRows: GridRowsProp = [
    { id: 1, col1: '1', col2: '장미', col3: '하젤', col4: '특2', col5: '양재', col6: '10', col7: '1000', col8: '픽업대기'},
    { id: 2, col1: '2', col2: '장미', col3: '스프레이', col4: '특2', col5: '광주', col6: '20', col7: '2500', col8: '픽업완료'},
    { id: 3, col1: '3', col2: '국화', col3: '다이아몬드', col4: '특2', col5: '양재', col6: '20', col7: '12000', col8: '픽업완료'},
    { id: 4, col1: '4', col2: '수국', col3: '베레나핑크', col4: '중3', col5: '부산', col6: '30', col7: '3800', col8: '픽업완료'},
    { id: 5, col1: '5', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 6, col1: '6', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 7, col1: '7', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 8, col1: '8', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 9, col1: '9', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 10, col1: '10', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 11, col1: '11', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 12, col1: '12', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 13, col1: '13', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 14, col1: '14', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 15, col1: '15', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 16, col1: '16', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 17, col1: '17', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
    { id: 18, col1: '18', col2: '수국', col3: '베레나핑크', col4: '상3', col5: '부산', col6: '5', col7: '4950', col8: '픽업완료'},
  ];
  
  const [rows, setRows] = useState<GridRowsProp>(initialRows);

  // 회원 아이디 (더미데이터)
  const pk = 1;
  // 회원 낙찰 내역 가져오기 (미완성)
  useEffect(() => {
    axios({
      method: "get",
      url: `/api/admin-service/trades/${pk}?term=1&page=0`
    })
    .then(res => {
      console.log(res);
      // const rawData = response.data.data; // Get the array of data from the API response
      // const formattedData = rawData.map((item) => ({
      //   id: item.unique_id,
      //   col1: item.unique_id.toString(),
      //   col2: item.item,
      //   col3: item.type,
      //   col4: item.grade,
      //   col5: item.location,
      //   col6: item.amount,
      //   col7: item.price,
      //   col8: item.status,
      // }));
      // setRows(formattedData); // Update the rows state with the formatted data

    })
    .catch(err => {
      console.log(err);
    })
  }, []);

  const handleDelete = (id) => {
    // 클릭한 행을 삭제하는 로직 구현
    axios({
      method: "delete",
      url: `/api/admin-service/trades/${pk}/${id}`
    })
    .then((res) => {
      console.log(res);
      // 서버에서 삭제 성공하면 rows 상태 업데이트하여 화면에서도 삭제
      // const updatedRows = rows.filter((row) => row.id !== id);
      // setRows(updatedRows);
    })
    .catch(err => {
      console.log(err);
    });
  };
  
  const columns: GridColDef[] = [
    { field: 'col1', headerName: 'No', width: 110 },
    { field: 'col2', headerName: '품목', width: 110 },
    { field: 'col3', headerName: '품종', width: 110 },
    { field: 'col4', headerName: '등급', width: 110 },
    { field: 'col5', headerName: '지역', width: 110 },
    { field: 'col6', headerName: '단수', width: 110 },
    { field: 'col7', headerName: '가격', width: 110 },
    { field: 'col8', headerName: '픽업 현황', width: 110 },
    {
      field: 'col9',
      headerName: '낙찰 내역 삭제',
      width: 150,
      renderCell: (params) => (
        <button className='delete-btn' onClick={() => handleDelete(params.row.id)}>삭제하기</button>
      ),
    },
  ];
  
  return (
    <MDBCard>
      <MDBCardBody>
        <MDBCardTitle className="mb-4 font-bold">
          나의 낙찰 내역
        </MDBCardTitle>

        <div className="h-full w-full">
          <StripedDataGrid
            rows={rows} columns={columns}
            getRowClassName={(params) =>
              params.indexRelativeToCurrentPage % 2 === 0 ? 'even' : 'odd'
            }
            pageSizeOptions={[5, 10, 25]}
          />
        </div>

      </MDBCardBody>
    </MDBCard>
  )
}

export default MyAuctionList