import React from 'react'
import styles from "./StaffStudentManage.module.css"
import MainButton from "../../components/buttons/mainButton/MainButton"
import BaseListItem from '../../components/listItem/baseListItem/BaseListItem'

export default function StaffStudentManage() {
  return (
    <>
    <div>
      <MainButton title="학습자 등록"></MainButton>
    </div>
      <div>
        <BaseListItem content="홍길동" phone="010-1234-1234" email="educheck@educheck.com" tagTitle="수강중"></BaseListItem>
        <BaseListItem content="홍길동" phone="010-1234-1234" email="educheck@educheck.com" tagTitle="결석"></BaseListItem>
        <BaseListItem content="홍길동" phone="010-1234-1234" email="educheck@educheck.com" tagTitle="수강중"></BaseListItem>
        <BaseListItem content="홍길동" phone="010-1234-1234" email="educheck@educheck.com" tagTitle="수료"></BaseListItem>
        <BaseListItem content="홍길동" phone="010-1234-1234" email="educheck@educheck.com" tagTitle="수강중"></BaseListItem>
      </div>
    </>
  )
}
