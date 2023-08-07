import React, { useState } from 'react';
import { DatePicker } from '@mui/x-date-pickers';
import { MenuItem, Select, FormControl, SelectChangeEvent } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import './TableFilter.css';
import FilterValues from './TableInterface';

// 테이블 검색 필터 구현
const TableFilter = ({ onFilterChange } : { onFilterChange: (filter: FilterValues) => void }) => {
  // 상태관리
  // 처음엔 비어있는 상태
  const [filter, setFilter] = useState<FilterValues>({ startDate: dayjs(new Date()), endDate: dayjs(new Date()), flower: '', variety: '', location: '' });

  // 시작날짜 바뀌었을 때
  const handleStartDateChange = (date: dayjs.Dayjs) => {
    setFilter({ ...filter, startDate: date });
  };

  // 종료날짜 바뀌었을 때
  const handleEndDateChange = (date: dayjs.Dayjs) => {
    setFilter({ ...filter, endDate: date });
  };
  
  // const handleDateChange = (date: dayjs.Dayjs) => {
  //   setFilter({ ...filter, date });
  // }

  // const handleDatePickerChange = (date: dayjs.Dayjs | null) => {
  //   if (date) {
  //     handleDateChange(date);
  //   }
  // };
  

  // 클릭할 때 선택한 필터정보 저장
  const handleFlowerChange = (event: SelectChangeEvent<string>) => {
    // 해당 입력 필드의 이름(name)과 변경된 값(value)을 가져옴
    const { name, value } = event.target;
    setFilter({ ...filter, [name]: value });
  };

  const handleVarietyChange = (event: SelectChangeEvent<string>) => {
    const { name, value } = event.target;
    setFilter({ ...filter, [name]: value });
  };

  const handleLocationChange = (event: SelectChangeEvent<string>) => {
    const { name, value } = event.target;
    setFilter({ ...filter, [name]: value });
  };

  // 검색을 누르면 선택한 정보 출력하는 변수
  const handleSearch = () => {
    onFilterChange(filter);
  };

  const currentDate = dayjs();

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <div className='tablecontent'>
        <div className='tablecontent-inner '>
          <div className='datecontent'>
            <div className='datetitle'>
              날짜
            </div>
            <DatePicker
              className='datepicker'
              value={filter.startDate}
              label="시작날짜"
              format="YYYY-MM-DD"
              maxDate={filter.endDate}
              onChange={handleStartDateChange}
              // startDate= {filter.startDate}
              // endDate= {filter.endDate}
              slotProps={{ textField: { size: 'small' } }}
            />
            <DatePicker
              className='datepicker'
              value={filter.endDate}
              label="종료날짜"
              onChange={handleEndDateChange}
              format="YYYY-MM-DD"
              minDate={filter.startDate}
              maxDate={currentDate}
              slotProps={{ textField: { size: 'small' } }}
            />
          </div>
          <div className='flowercontent'>
            <div className='flowertitle'>
              품목
            </div>
            <FormControl className='flowerinput' variant="outlined" size="small">
              <Select
                id="flower-select"
                className='flowerinput'
                name="flower"
                value={filter.flower}
                onChange={handleFlowerChange}
              >
                <MenuItem value="">전체</MenuItem>
                <MenuItem value="장미">장미</MenuItem>
                <MenuItem value="국화">국화</MenuItem>
                {/* 추가적인 품목 옵션들 */}
              </Select>
            </FormControl>
          </div>
          <div className='varietycontent'>
            <div className='varietytitle'>
              품종
            </div>
            <FormControl className='varietyinput' variant="outlined" size="small">
              <Select
                id="variety-select"
                className='varietyinput'
                name="variety"
                value={filter.variety}
                onChange={handleVarietyChange}
              >
                <MenuItem value="">전체</MenuItem>
                <MenuItem value="스프린트">스프린트</MenuItem>
                <MenuItem value="다이아몬드">다이아몬드</MenuItem>
                {/* 추가적인 품종 옵션들 */}
              </Select>
            </FormControl>
          </div>
          <div className='locationcontent'>
            <div className='locationtitle'>
              지역
            </div>
            <FormControl className='locationinput' variant="outlined" size="small">
              <Select
                id="location-select"
                className='locationinput'
                name="location"
                value={filter.location}
                onChange={handleLocationChange}
              >
                <MenuItem value="">전체</MenuItem>
                <MenuItem value="양재">양재</MenuItem>
                <MenuItem value="광주">광주</MenuItem>
              </Select>
            </FormControl>
            <div className='button-search' onClick={handleSearch}>
              <svg 
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-6 h-6"
              >
                <path 
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
                />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </LocalizationProvider>
  );
};

export default TableFilter;
