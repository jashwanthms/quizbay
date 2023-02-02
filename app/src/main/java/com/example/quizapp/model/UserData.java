package com.example.quizapp.model;

import com.google.gson.annotations.SerializedName;

public class UserData{

	@SerializedName("isSports")
	private int isSports;

	@SerializedName("isEntertainment")
	private int isEntertainment;

	@SerializedName("isFashion")
	private int isFashion;

	@SerializedName("isTravel")
	private int isTravel;

	@SerializedName("isFood")
	private int isFood;

	@SerializedName("userId")
	private String userId;

	public void setIsSports(int isSports){
		this.isSports = isSports;
	}

	public int getIsSports(){
		return isSports;
	}

	public void setIsEntertainment(int isEntertainment){
		this.isEntertainment = isEntertainment;
	}

	public int getIsEntertainment(){
		return isEntertainment;
	}

	public void setIsFashion(int isFashion){
		this.isFashion = isFashion;
	}

	public int getIsFashion(){
		return isFashion;
	}

	public void setIsTravel(int isTravel){
		this.isTravel = isTravel;
	}

	public int getIsTravel(){
		return isTravel;
	}

	public void setIsFood(int isFood){
		this.isFood = isFood;
	}

	public int getIsFood(){
		return isFood;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	@Override
 	public String toString(){
		return 
			"UserData{" + 
			"isSports = '" + isSports + '\'' + 
			",isEntertainment = '" + isEntertainment + '\'' + 
			",isFashion = '" + isFashion + '\'' + 
			",isTravel = '" + isTravel + '\'' + 
			",isFood = '" + isFood + '\'' + 
			",userId = '" + userId + '\'' + 
			"}";
		}
}