import React, { useState } from 'react';
import {
  MDBBtn,
  MDBModal,
  MDBModalDialog,
  MDBModalContent,
  MDBModalHeader,
  MDBModalTitle,
  MDBModalBody,
  MDBModalFooter,
  MDBInput
} from 'mdb-react-ui-kit';
import SelectForm from '@/pages/user/MyReservation/SelectForm';

export default function MyReservModal() {
  const [centredModal, setCentredModal] = useState(false);

  // Select 컴포넌트에서 받아온 값들 (카테고리 제외)
  const [selectedFlower, setSelectedFlower] = useState('');
  const [selectedVariety, setSelectedVariety] = useState('');

  // Input 값들 설정
  const [grade, setGrade] = useState('');
  const [quantity, setQuantity] = useState('');
  const [price, setPrice] = useState('');

  const [isGradeValid, setIsGradeValid] = useState(true);
  const [quantityWarning, setQuantityWarning] = useState(false);

  const toggleShow = () => setCentredModal(!centredModal);
  
  // 예약 submit
  const reservSubmit = () => {
    // 등급이 특, 상, 보가 아니면 다시 입력하라고 경고
    if (!/^(특|상|보)$/.test(grade)) {
      setIsGradeValid(false);
      return;
    } else{
      setIsGradeValid(true);
    }

    // Check if "단수" exceeds 20
    if (parseInt(quantity) > 20) {
      setQuantityWarning(true);
      return;
    }else {
      setQuantityWarning(false);
    }

    // Prepare the data to be submitted
    const formData = {
      type: selectedFlower,
      name: selectedVariety,
      grade: grade,
      count: quantity,
      price: price,
      // Add other form inputs here if needed
    };

    console.log(formData)
  }
  return (
    <>
      <MDBBtn onClick={toggleShow}>Vertically centered modal</MDBBtn>

      <MDBModal tabIndex='-1' show={centredModal} setShow={setCentredModal}>
        <MDBModalDialog centered>
          <MDBModalContent>
            <MDBModalHeader>
              <MDBModalTitle>화훼 예약 하기</MDBModalTitle>
              <MDBBtn className='btn-close' color='none' onClick={toggleShow}></MDBBtn>
            </MDBModalHeader>
            <MDBModalBody className='flex flex-col'>
              <form className=''>
                <SelectForm 
                  onFlowerChange={setSelectedFlower}
                  onVarietyChange={setSelectedVariety}
                />
                <div className='mt-3 flex justify-between'>
                  <MDBInput
                    className='w-[33%]'
                    label='등급'
                    value={grade}
                    type='text'
                    placeholder='특, 상, 보'
                    pattern='^(특|상|보)$'
                    onChange={(e) => {
                      setGrade(e.target.value)
                    }}
                  />

                  <MDBInput
                    className='w-[33%]'
                    label='단수'
                    value={quantity}
                    type='number'
                    max={20}
                    onChange={(e) => setQuantity(e.target.value)}
                    />

                  <MDBInput
                    className='w-[33%]'
                    label='가격'
                    value={price}
                    type='number'
                    onChange={(e) => setPrice(e.target.value)}
                    />
                </div>
                {!isGradeValid && (
                  <div className='text-red text-base'>올바른 등급을 입력해주세요. (특, 상, 보 중 하나)</div>
                  )}
                {quantityWarning && (
                  <div className='text-red text-base'>
                    최대 단수는 '20' 입니다
                  </div>
                )}
              </form>
            </MDBModalBody>
            <MDBModalFooter>
              <MDBBtn color='secondary' onClick={toggleShow}>
                취소
              </MDBBtn>
              <MDBBtn onClick={reservSubmit}>예약 하기</MDBBtn>
            </MDBModalFooter>
          </MDBModalContent>
        </MDBModalDialog>
      </MDBModal>
    </>
  );
}