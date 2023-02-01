package com.example.quizapp.model;

import com.google.gson.annotations.SerializedName;

public class Status{

	@SerializedName("index")
	private int index;

	@SerializedName("timeLeft")
	private int timeLeft;

	@SerializedName("remainingTime")
	private int remainingTime;

	@SerializedName("status")
	private String status;

	public void setIndex(int index){
		this.index = index;
	}

	public int getIndex(){
		return index;
	}

	public void setTimeLeft(int timeLeft){
		this.timeLeft = timeLeft;
	}

	public int getTimeLeft(){
		return timeLeft;
	}

	public void setRemainingTime(int remainingTime){
		this.remainingTime = remainingTime;
	}

	public int getRemainingTime(){
		return remainingTime;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Status{" + 
			"index = '" + index + '\'' + 
			",timeLeft = '" + timeLeft + '\'' + 
			",remainingTime = '" + remainingTime + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}