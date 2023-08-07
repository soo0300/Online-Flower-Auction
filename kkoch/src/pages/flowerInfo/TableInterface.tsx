import dayjs from "dayjs";

interface FilterValues {
  startDate: dayjs.Dayjs;
  endDate: dayjs.Dayjs;
  flower: string;
  variety: string;
  location: string;
}

export default FilterValues;