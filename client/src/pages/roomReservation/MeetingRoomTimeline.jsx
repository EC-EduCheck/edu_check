import React, { useState, useEffect } from 'react';

const MeetingRoomTimeline = () => {
  // 예약 데이터 상태
  const [reservationData, setReservationData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 시간대 생성 (9시부터 22시까지)
  const timeSlots = Array.from({ length: 14 }, (_, i) => i + 9);
  
  // 서버에서 데이터 가져오기 (실제로는 API 호출)
  useEffect(() => {
    // 예제 데이터 사용 (실제로는 fetch 사용)
    const exampleData = {
      "message": "회의실 예약 내역 조회 성공",
      "code": "OK",
      "data": {
        "campusId": 1,
        "date": "2025-03-24",
        "meetingRooms": [
          {
            "meetingRoomId": 1,
            "meetingRoomName": "1층 회의실1",
            "reservations": []
          },
          {
            "meetingRoomId": 3,
            "meetingRoomName": "2층 회의실1",
            "reservations": []
          },
          {
            "meetingRoomId": 4,
            "meetingRoomName": "3층 회의실1",
            "reservations": []
          },
          {
            "meetingRoomId": 2,
            "meetingRoomName": "1층 회의실2",
            "reservations": [
              {
                "meetingRoomReservationId": 5,
                "reserverId": 5,
                "reserverName": "홍둘리",
                "startDateTime": "2025-03-24T12:30:00",
                "endDateTime": "2025-03-24T14:00:00"
              },
              {
                "meetingRoomReservationId": 11,
                "reserverId": 5,
                "reserverName": "홍둘리",
                "startDateTime": "2025-03-24T17:30:00",
                "endDateTime": "2025-03-24T18:30:00"
              }
            ]
          }
        ]
      }
    };

    // 데이터 수정 (예제 데이터의 시간 포맷 이슈 수정)
    setReservationData(exampleData.data);
    setLoading(false);
  }, []);

  // 시간을 시각적 위치로 변환하는 함수
  const getTimePosition = (timeString) => {
    if (!timeString) return 0;
    
    // "2025-03-24T12:30:00" 형식에서 시간과 분 추출
    const time = new Date(timeString);
    const hours = time.getHours();
    const minutes = time.getMinutes();
    
    // 9시부터 시작, 13시간(9시-22시) 범위에서의 비율 계산
    return ((hours - 9) + (minutes / 60)) / 13 * 100;
  };

  // 예약 시간 계산 함수 (예약 길이를 퍼센트로 변환)
  const getReservationWidth = (startTime, endTime) => {
    if (!startTime || !endTime) return 0;
    
    const start = new Date(startTime);
    const end = new Date(endTime);
    
    // 시간 차이를 시간 단위로 계산
    const diffHours = (end - start) / (1000 * 60 * 60);
    
    // 13시간(9시-22시) 범위에서의 비율 계산
    return (diffHours / 13) * 100;
  };

  if (loading) return <div className="text-center p-4">로딩 중...</div>;
  if (error) return <div className="text-center p-4 text-red-500">에러: {error.message}</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">회의실 예약 현황 ({reservationData.date})</h1>
      
      {/* 시간대 표시 */}
      <div className="flex mb-4 border-b ml-32">
        {timeSlots.map((hour) => (
          <div key={hour} className="flex-1 text-center text-sm">
            {hour}:00
          </div>
        ))}
      </div>
      
      {/* 회의실별 타임라인 */}
      {reservationData.meetingRooms.map((room) => (
        <div key={room.meetingRoomId} className="flex items-center mb-4">
          <div className="w-32 font-medium truncate pr-2">{room.meetingRoomName}</div>
          
          {/* 타임라인 */}
          <div className="flex-1 h-10 bg-gray-100 relative rounded">
            {/* 시간 구분선 */}
            {timeSlots.map((hour) => (
              <div 
                key={hour} 
                className="absolute h-full w-px bg-gray-300" 
                style={{ left: `${((hour - 9) / 13) * 100}%` }}
              />
            ))}
            
            {/* 예약 표시 */}
            {room.reservations.map((reservation) => {
              // 시간대가 뒤바뀐 데이터 수정 (서버 데이터 이슈)
              let start = reservation.startDateTime;
              let end = reservation.endDateTime;
              
              // 종료 시간이 시작 시간보다 앞에 있으면 교체
              if (new Date(end) < new Date(start)) {
                [start, end] = [end, start];
              }
              
              // 시작 시간이 9시 이전이면 9시로 조정
              const startDate = new Date(start);
              if (startDate.getHours() < 9) {
                startDate.setHours(9, 0, 0);
                start = startDate.toISOString();
              }
              
              // 종료 시간이 22시 이후면 22시로 조정
              const endDate = new Date(end);
              if (endDate.getHours() >= 22) {
                endDate.setHours(22, 0, 0);
                end = endDate.toISOString();
              }
              
              const leftPos = getTimePosition(start);
              const width = getReservationWidth(start, end);
              
              return (
                <div
                  key={reservation.meetingRoomReservationId}
                  className="absolute h-10 bg-blue-500 text-white text-xs flex items-center justify-center px-2 rounded"
                  style={{
                    left: `${leftPos}%`,
                    width: `${width}%`,
                    minWidth: '60px'
                  }}
                  title={`${reservation.reserverName}: ${new Date(start).toLocaleTimeString()} - ${new Date(end).toLocaleTimeString()}`}
                >
                  {width > 10 ? (
                    <div className="truncate">
                      {reservation.reserverName}
                    </div>
                  ) : null}
                </div>
              );
            })}
          </div>
        </div>
      ))}
      
      <div className="mt-4 text-sm text-gray-500">
        * 회의실을 예약하려면 타임라인을 클릭하세요
      </div>
    </div>
  );
};

export default MeetingRoomTimeline;