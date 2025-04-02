from flask import Flask, Response, send_file
from flask_cors import CORS
import pandas as pd
import io
import mysql.connector
from mysql.connector import pooling
from dotenv import load_dotenv
import os
import xlsxwriter
import json

load_dotenv()

app = Flask(__name__)
CORS(app, resources={r"/app/*": {"origins": "http://localhost:5173"}})


dbconfig = {
    "user": os.getenv("DATABASE_USERNAME"),
    "password": os.getenv("DATABASE_PASSWORD"),
    "host": os.getenv("DATABASE_HOST"),
    "database": os.getenv("DATABASE_NAME"),
    "port": os.getenv("DATABASE_PORT"),
}


cnxpool = pooling.MySQLConnectionPool(pool_name="pool", pool_size=5, **dbconfig)


def get_data_from_db(member_id, course_id):

    status_mapper = {
        "ATTENDANCE": "출석",
        "LATE": "지각",
        "EARLY_LEAVE": "조퇴",
        "ABSENT": "결석",
    }

    conn = cnxpool.get_connection()
    try:
        query = """
    SELECT lecture_session, lecture_date, attendance_status
    FROM student_course_attendance
    WHERE member_id = %s AND course_id = %s
"""
        data = pd.read_sql(query, conn, params=(member_id, course_id))
        data["attendance_status"] = data["attendance_status"].replace(status_mapper)
        data["lecture_date"] = data["lecture_date"].apply(
            lambda x: str(x) if pd.notnull(x) else None
        )
        data.rename(
            columns={
                "lecture_session": "회차",
                "lecture_date": "날짜",
                "attendance_status": "출석 상태",
            },
            inplace=True,
        )

    finally:
        conn.close()
    return data


def get_student_name(member_id):
    conn = cnxpool.get_connection()
    try:
        query = """
        SELECT name
        FROM member
        WHERE id = %s
        """
        cursor = conn.cursor()
        cursor.execute(query, (member_id,))
        result = cursor.fetchone()
        return result[0]
    finally:
        conn.close()


@app.route("/app/courses/<int:course_id>/members/<int:member_id>", methods=["GET"])
def data(member_id, course_id):
    df = get_data_from_db(member_id, course_id)

    json_str = json.dumps(df.to_dict(orient="records"), ensure_ascii=False)

    return Response(json_str, mimetype="application/json")


@app.route(
    "/app/courses/<int:course_id>/members/<int:member_id>/download", methods=["GET"]
)
def download(member_id, course_id):
    data = get_data_from_db(member_id, course_id)
    output = io.BytesIO()
    with pd.ExcelWriter(output, engine="xlsxwriter") as writer:
        data.to_excel(writer, index=False, sheet_name="Data")
    output.seek(0)
    member_name = get_student_name(member_id)

    return send_file(
        output, download_name=f"{member_name}_출석부.xlsx", as_attachment=True
    )


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
