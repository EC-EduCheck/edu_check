import React from 'react'
import styles from "./StaffRoomReservation.module.css"
import DashBoardItem from "../../components/dashBoardItem/DashBoardItem"

export default function StaffRoomReservation() {
  return (
    <div className={styles.roomDashBoardItem}>
      <DashBoardItem title="예약 현황"></DashBoardItem>
    </div>
  )
}
