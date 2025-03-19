import React from 'react'
import style from "./FilterButton.module.css"

export default function FilterButton({title, content, bgcolor}) {
  return (
    <div>
    <div className={style.filterButton} style={{backgroundColor : bgcolor}}>{title}<span className={style.text}>{content}</span></div>
    </div>
  )
}
