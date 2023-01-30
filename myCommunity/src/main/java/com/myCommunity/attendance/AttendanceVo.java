package com.myCommunity.attendance;

public class AttendanceVo {
	private int id;
	private int userId;
	private String attendanceDate;
	private int count;
	
	public AttendanceVo() {}
	public AttendanceVo(int id, int userId, String attendanceDate, int count) {
		super();
		this.id = id;
		this.userId = userId;
		this.attendanceDate = attendanceDate;
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}

//컬럼 수정 => alter table 테이블명 alter column 컬럼명 int not null auto_increment;

//create table Attendance
//(
// id bigint generated by default as identity,
// user_id varchar(255) not null,
// attendance_date varchar(100) not null,
// count int not null default 0,
// primary key (id)
//)