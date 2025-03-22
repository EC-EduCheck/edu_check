import React, { useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

export default function NavigationHandler() {
  const path = useSelector((state) => state.sideBarItem.path);
  const navigate = useNavigate();

  useEffect(() => {
    console.log(path)
    if (path) {
      navigate(path);
    }
  }, [])

  return null;
}
