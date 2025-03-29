from flask import Flask, jsonify, send_file
import pandas as pd
import io
import mysql.connector
from mysql.connector import pooling
from dotenv import load_dotenv
import os

load_dotenv()

app = Flask(__name__)


dbconfig = {
    "user": os.getenv("DATABASE_USERNAME"),
    "password": os.getenv("DATABASE_PASSWORD"),
    "host": os.getenv("DATABASE_HOST"),
    "database": os.getenv("DATABASE_NAME"),
    "port": os.getenv("DATABASE_PORT"),
}


cnxpool = pooling.MySQLConnectionPool(pool_name="pool", pool_size=5, **dbconfig)


def get_data_from_db(student_id, course_id):

    conn = cnxpool.get_connection()
    try:
        query = """
    SELECT lecture_session, lecture_date, attendance_status
    FROM student_course_attendance
    WHERE student_id = %s AND course_id = %s
"""
        data = pd.read_sql(query, conn, params=(student_id, course_id))

    finally:
        conn.close()
    return data


@app.route("/app/courses/<int:course_id>/students/<int:student_id>", methods=["GET"])
def data(student_id, course_id):
    df = get_data_from_db(student_id, course_id)

    return jsonify(df.to_dict(orient="records"))


if __name__ == "__main__":
    app.run(debug=False)
