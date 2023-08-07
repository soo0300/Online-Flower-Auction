import { MDBInput } from 'mdb-react-ui-kit';
import SelectForm from '@/pages/user/MyReservation/SelectForm';

export default function ReservForm() {
  const flowerOption = [
    { value: 'rose', label: 'Rose'},
    { value: 'tulip', label: 'Tulip'},
    { value: 'sumflower', label: 'Sunflower'},
    { value: 'lavender', label: 'Lavender'},
    { value: 'hydrangea', label: 'Hydrangea'},
  ]
  return (
    <form className=''>
      <SelectForm placeholder='품목'/>
      <SelectForm placeholder='품종'/>
      <MDBInput label='Name' />
    </form>
  );
} 