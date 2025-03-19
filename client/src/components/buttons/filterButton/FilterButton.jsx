import React from 'react'
import style from "./FilterButton.module.css"

export default function FilterButton({title, content, bgcolor}) {
  return (
    <div>
    <button className={style.filterButton} style={{backgroundColor : bgcolor}}>{title}<p className={style.text}>{content}</p>
    </button>
    </div>
  )
}
