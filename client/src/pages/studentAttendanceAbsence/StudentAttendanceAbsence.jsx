import React from 'react'
import styles from "./StudentAttendanceAbsence.module.css"
import LeftLineListItem from "../../components/listItem/leftLineListItem/LeftLineListItem"
import DashBoardItem from '../../components/dashBoardItem/DashBoardItem'
import RoundButton from '../../components/buttons/roundButton/RoundButton'
import InputBox2 from '../../components/inputBox/InputBox2/InputBox2'
import InputBox3 from '../../components/inputBox/InputBox3/InputBox3'
import MainButton from '../../components/buttons/mainButton/MainButton'


export default function StudentAttendanceAbsence() {
  return (
      <>
        <div className={styles.LeftLineListItemDisplay}>
          <div className={styles.absenceLeftLineListItem}>
          <LeftLineListItem
            isClickable={false}
            // status={}
            children={"조퇴"}
          ></LeftLineListItem>
          <LeftLineListItem
            isClickable={false}
            // status={}
            children={"결석"}
          ></LeftLineListItem>
          <LeftLineListItem
            isClickable={false}
            // status={}
            children={"결석"}
          ></LeftLineListItem>
          <LeftLineListItem
            isClickable={false}
            // status={}
            children={"결석"}
          ></LeftLineListItem>
          </div>
          <div className={styles.absenceDashBoardItem}>
          <DashBoardItem>
            <RoundButton title="결석"></RoundButton>
            <RoundButton title="조퇴" isActiveIndex={false}></RoundButton>
            <RoundButton title="지각" isActiveIndex={false}></RoundButton>
            <div className={styles.InputBox}>
            <InputBox2
              // type={ }
              title={"날짜선택하기"}
              // disabled={ }
              // onChange={onChange}
              label={"기간"}
              // content={ }
              // name={ }
            ></InputBox2>
            <InputBox2
              title={"파일 선택 또는 끌어놓기..."}
              label={"서류"}
              // onChange={onChange}
            ></InputBox2>
            <InputBox3
              label={"사유"}
              // onChange={onChange}
              ></InputBox3>
            </div>
            <div className={styles.mainButton}>
            <MainButton
            title={"신청"}
              ></MainButton>
            </div>
          </DashBoardItem>
        </div>
        </div>
      </>
  )
}
