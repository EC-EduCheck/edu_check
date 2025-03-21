import { createBrowserRouter } from 'react-router-dom';
import RootLayout from '../layout/RootLayout';

import NotFound from '../pages/error/NotFound';
import Home from '../pages/home/Home';

import Login from '../pages/login/Login';
import DashBoard from '../pages/dashBoard/DashBoard';

import StudentAttendance from '../pages/studentAttendance/StudentAttendance';
import RoomReservation from '../pages/roomReservation/RoomReservation';
import StudentSetting from '../pages/studentSetting/StudentSetting';
import StudentAttendanceAbsence from '../pages/studentAttendanceAbsence/StudentAttendanceAbsence';

import StaffAttendance from '../pages/staffAttendance/StaffAttendance';
import StaffAttendanceDetail from '../pages/staffAttendanceDetail/StaffAttendanceDetail';
import StaffAttendanceAbsence from '../pages/staffAttendanceAbsence/StaffAttendanceAbsence';
import StaffStudentManage from '../pages/staffStudentManage/StaffStudentManage';
import StaffRoomReservation from '../pages/staffRoomReservation/StaffRoomReservation';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    errorElement: <NotFound />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/dashBoard',
        element: <DashBoard />,
        children: [
          {
            path: '/dashBoard/student',
            element: <DashBoard />,
          },
          {
            path: '/dashBoard/student/attendance',
            element: <StudentAttendance />,
          },
          {
            path: '/dashBoard/student/attendance/absence',
            element: <StudentAttendanceAbsence />,
          },
          {
            path: '/dashBoard/student/reservation',
            element: <RoomReservation />,
          },
          {
            path: '/dashBoard/student/setting',
            element: <StudentSetting />,
          },
          {
            path: '/dashBoard/staff',
            element: <DashBoard />,
          },
          {
            path: '/dashBoard/staff/attendance',
            element: <StaffAttendance />,
          },
          {
            path: '/dashBoard/staff/attendance/detail',
            element: <StaffAttendanceDetail />,
          },
          {
            path: '/dashBoard/staff/attendance/absence',
            element: <StaffAttendanceAbsence />,
          },
          {
            path: '/dashBoard/staff/studentManage',
            element: <StaffStudentManage />,
          },
          {
            path: '/dashBoard/staff/reservation',
            element: <StaffRoomReservation />,
          },
        ],
      },
    ],
  },
]);

export default router;
