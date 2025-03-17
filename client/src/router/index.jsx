import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../layout/RootLayout";
import NotFound from '../pages/error/NotFound';
import Home from '../pages/home/Home';
import Login from '../pages/login/Login';
import DashBoard from '../pages/dashBoard/DashBoard';

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
        index: '/dashBoard',
        element: <DashBoard />,
      },
    ],
  },
]);

export default router;
