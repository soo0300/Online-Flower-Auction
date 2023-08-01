import { MDBInput } from 'mdb-react-ui-kit';
import SelectForm from '@/pages/user/MyReservation/SelectForm';

export default function ReservForm() {
  return (
    <form className=''>
      <SelectForm placeholder='품목'/>
      <SelectForm placeholder='품종'/>
      <MDBInput label='Name' />
    </form>
  );
} 