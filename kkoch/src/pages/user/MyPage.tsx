import {
  MDBCol,
  MDBContainer,
  MDBTabs,
  MDBTabsItem,
  MDBTabsLink,
  MDBTabsContent,
  MDBTabsPane,
  MDBCardBody,
  MDBCard
} from 'mdb-react-ui-kit';

import { useState } from 'react';
import MyAuctionList from './MyAuctionList';
import MyProfile from './MyProfile';
import MyReservation from './MyReservation';

const MyPage = () => {
  const [basicActive, setBasicActive] = useState('tab1');

  const handleBasicClick = (value: string) => {
    if (value === basicActive) {
      return;
    }

    setBasicActive(value);
  };

  return (
    <section className='pt-[115px]'>
      <MDBContainer className="py-5">
        
      <MDBCard className='mb-4'>
        <h1 className='pl-5 pt-3'>마이페이지</h1>
        <MDBTabs className='rounded-3'>
          <MDBTabsItem>
            <MDBTabsLink onClick={() => handleBasicClick('tab1')} active={basicActive === 'tab1'}>
              프로필 변경
            </MDBTabsLink>
          </MDBTabsItem>
          <MDBTabsItem>
            <MDBTabsLink onClick={() => handleBasicClick('tab2')} active={basicActive === 'tab2'}>
              낙찰 현황
            </MDBTabsLink>
          </MDBTabsItem>
          <MDBTabsItem>
            <MDBTabsLink onClick={() => handleBasicClick('tab3')} active={basicActive === 'tab3'}>
              화훼 예약 현황
            </MDBTabsLink>
          </MDBTabsItem>
        </MDBTabs>
      </MDBCard>

        <MDBCol className='bg-light rounded'>
          {/* <MDBBreadcrumb className="">
            <MDBBreadcrumbItem>
              <a href='#'>프로필 변경</a>
            </MDBBreadcrumbItem>
            <MDBBreadcrumbItem>
              <a href="#">낙찰 현황</a>
            </MDBBreadcrumbItem>
            <MDBBreadcrumbItem active>User Profile</MDBBreadcrumbItem>
          </MDBBreadcrumb> */}
        </MDBCol>

        <MDBTabsContent>
          <MDBTabsPane show={basicActive === 'tab1'}><MyProfile/></MDBTabsPane>
          <MDBTabsPane show={basicActive === 'tab2'}><MyAuctionList/></MDBTabsPane>
          <MDBTabsPane show={basicActive === 'tab3'}><MyReservation/></MDBTabsPane>
        </MDBTabsContent>
      </MDBContainer>


    </section>
  )
}

export default MyPage