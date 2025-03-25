import axios from 'axios';

const HOLIDAY_API_KEY = import.meta.env.VITE_HOLIDAY_API_KEY;
const HOLIDAY_BASE_URL = import.meta.env.VITE_HOLIDAY_BASE_URL;

export const fetchHolidays = async (year, month) => {
  try {
    const response = await axios.get(`${HOLIDAY_BASE_URL}/getRestDeInfo`, {
      params: {
        ServiceKey: HOLIDAY_API_KEY,
        solYear: year,
        solMonth: month < 10 ? `0${month}` : month,
        numOfRows: 100,
        _type: 'json', // JSON 응답을 요청
      },
    });

    const items = response.data.response.body.items.item;

    if (!items) {
      console.log('공휴일 데이터가 없습니다.');
      return [];
    }

    // 단일 항목인 경우 배열로 변환
    const itemArray = Array.isArray(items) ? items : [items];

    const holidayList = itemArray.map((item) => ({
      date: `${item.locdate.toString().substring(0, 4)}-${item.locdate.toString().substring(4, 6)}-${item.locdate.toString().substring(6, 8)}`,
      name: item.dateName,
    }));
    return holidayList;
  } catch (error) {
    console.error('공휴일 데이터를 가져오는 중 오류 발생:', error);
    return [];
  }
};
