import dayjs from "dayjs";

interface FilterValues {
  date: dayjs.Dayjs;
  flower: string;
  variety: string;
  location: string;
}

export default FilterValues;