package com.indocosmo.pms.web.reports.model;

public class NationalityReport implements Comparable<NationalityReport> {
	private String country_name;
	private String state_name;
	private int room;
	private String room_percentage;
	private int persons;
	private String person_percentage;
	private int yearly_room;
	private int yearly_person;
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getRoom_percentage() {
		return room_percentage;
	}
	public void setRoom_percentage(String room_percentage) {
		this.room_percentage = room_percentage;
	}
	public int getPersons() {
		return persons;
	}
	public void setPersons(int persons) {
		this.persons = persons;
	}
	public String getPerson_percentage() {
		return person_percentage;
	}
	public void setPerson_percentage(String person_percentage) {
		this.person_percentage = person_percentage;
	}
	public int getYearly_room() {
		return yearly_room;
	}
	public void setYearly_room(int yearly_room) {
		this.yearly_room = yearly_room;
	}
	public int getYearly_person() {
		return yearly_person;
	}
	public void setYearly_person(int yearly_person) {
		this.yearly_person = yearly_person;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name; 
		
	}
	public String getState_name() {
		return state_name;
	}
	@Override
	public int compareTo(NationalityReport arg0) {
		if(country_name!=null && arg0!=null && arg0.country_name!=null ){
		// TODO Auto-generated method stub
			return country_name.compareTo(arg0.country_name);
		}else if(country_name!=null){
			return 1;
		}else{
			return 0;
		}
		
	}
}
