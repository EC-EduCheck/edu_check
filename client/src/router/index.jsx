import { Navigate, createBrowserRouter } from 'react-router-dom';
import RootLayout from '../layout/RootLayout';
import NotFound from '../pages/error/NotFound';
import Home from '../pages/home/Home';
import Login from '../pages/login/Login';
import DashBoard from '../pages/dashBoard/DashBoard';
import StudentAttendance from '../pages/studentAttendance/StudentAttendance';
import StudentSetting from '../pages/studentSetting/StudentSetting';
import StudentAttendanceAbsence from '../pages/studentAttendanceAbsence/StudentAttendanceAbsence';
import StaffAttendance from '../pages/staffAttendance/StaffAttendance';
import StaffAttendanceDetail from '../pages/staffAttendanceDetail/StaffAttendanceDetail';
import StaffAttendanceAbsence from '../pages/staffAttendanceAbsence/StaffAttendanceAbsence';
import StaffStudentManage from '../pages/staffStudentManage/StaffStudentManage';
// import StaffRoomReservation from '../pages/staffRoomReservation/StaffRoomReservation';
import TmpLayout from '../layout/TmpLayout';
import { URL_PATHS } from '../constants/urlPaths';
import RoomReservation from '../pages/roomReservation/RoomReservation';
import AttendanceSheet from '../pages/attendanceSheet/AttendanceSheet';

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
        path: URL_PATHS.STUDENT.BASE,
        element: <DashBoard />,
        children: [
          {
            index: true,
            element: <Navigate to={URL_PATHS.STUDENT.ATTENDANCE.BASE} replace />,
          },
          {
            path: URL_PATHS.STUDENT.ATTENDANCE.BASE,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StudentAttendance />,
              },
              {
                path: URL_PATHS.STUDENT.ATTENDANCE.ABSENCE,
                element: <StudentAttendanceAbsence />,
              },
            ],
          },
          {
            path: URL_PATHS.STUDENT.RESERVATION,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <RoomReservation />,
              },
            ],
          },
          {
            path: URL_PATHS.STUDENT.SETTING,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StudentSetting />,
              },
            ],
          },
        ],
      },

      {
        path: URL_PATHS.MIDDLE_ADMIN.BASE,
        element: <DashBoard />,
        children: [
          {
            index: true,
            element: <Navigate to={URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.BASE} replace />,
          },
          {
            path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.BASE,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StaffAttendance />,
              },
              {
                path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.DETAIL_PATTERN,
                element: <StaffAttendanceDetail />,
              },
              {
                path: URL_PATHS.MIDDLE_ADMIN.ATTENDANCE.ABSENCE,
                element: <StaffAttendanceAbsence />,
              },
            ],
          },
          {
            path: URL_PATHS.MIDDLE_ADMIN.STUDENT_MANAGE,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <StaffStudentManage />,
              },
            ],
          },
          {
            path: URL_PATHS.MIDDLE_ADMIN.RESERVATION,
            element: <TmpLayout />,
            children: [
              {
                index: true,
                element: <RoomReservation />,
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

      {
        path: '/attendanceSheet',
        element: <AttendanceSheet />,
      },
    ],
  },
]);

export default router;
