import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../layout/RootLayout";
import NotFound from '../pages/error/NotFound';
import Home from '../pages/home/Home';
import Login from '../pages/login/Login';
import DashBoard from '../pages/dashBoard/DashBoard';
import EditProfile from '../pages/editProfile/EditProfile';
import RegisterProfile from '../pages/registerProfile/RegisterProfile';

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
        index: '/login',
        element: <Login />,
      },
      {
        index: '/editProfile',
        element: <EditProfile />,
      },
      {
        index: '/registerProfile',
        element: <RegisterProfile />,
      },
      {
        index: '/dashBoard',
        element: <DashBoard />,
      },
    ],
  },
]);

export default router;
