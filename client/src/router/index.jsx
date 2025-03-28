import { Navigate, createBrowserRouter } from 'react-router-dom';
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

import TmpLayout from '../layout/TmpLayout';
import { staffBaseUrl } from '../constants/baseUrl';

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
        path: '/dashBoard/student',
        element: <DashBoard />,
        children: [
          {
            index: true,
            element: <Navigate to="/dashBoard/student/attendance" replace />,
          },
          {
            path: '/dashBoard/student/attendance',
            element: <StudentAttendance />,
            children: [
              {
                index: true,
                element: <StudentAttendance />,
              },
              {
                path: '/dashBoard/student/attendance/absence',
                element: <StudentAttendanceAbsence />,
              },
            ],
          },

          {
            path: '/dashBoard/student/reservation',
            element: <RoomReservation />,
          },
          {
            path: '/dashBoard/student/setting',
            element: <StudentSetting />,
          },
        ],
      },

      {
        path: '/dashBoard/staff',
        element: <DashBoard />,
        children: [
          {
            index: true,
            element: <Navigate to="/dashBoard/staff/attendance" replace />,
          },
          {
            path: '/dashBoard/staff/attendance',
            element: <TmpLayout />,
            children: [
              {
                index: true,
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
            ],
          },
          {
            path: '/dashBoard/staff/studentManage',
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StaffStudentManage />,
              },
            ],
          },
          {
            path: '/dashBoard/staff/reservation',
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StaffRoomReservation />,
              },
            ],
          },
        ],
      },

      {
        path: '/notfound',
        element: <NotFound />,
      },
      // {
      //   path: '*',
      //   element: <Navigate to="/notfound" replace />,
      // },
    ],
  },
]);

export default router;
