package net.ubermc.players.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement
public class Player {

	private String name;
	private String motto;
	private String gender;
	private long points;
	private String last;
	private int online;
	private String server_id;
	private String uuid;
	private int elohg = 1900;
	private int eloluckywars = 1600;
	private int elopvpop = 1800;
	public HashSet<String> friends_uuid = new HashSet<String>();
	
    public Player() {
    	
    }
    
	public Player(String name, String motto, String gender, long points, String last, int online, String server_id, String uuid) {
    	this.name = name;
    	this.motto = motto;
    	this.gender = gender;
    	this.points = points;
    	this.last = last;
    	this.online = online;
    	this.server_id = server_id;
    	this.uuid = uuid;
    	this.elohg = 1900;
    	this.eloluckywars = 1600;
    	this.elopvpop = 1800;
    }
	
    public int getElohg() {
		return elohg;
	}

	public void setElohg(int elohg) {
		this.elohg = elohg;
	}

	public int getEloluckywars() {
		return eloluckywars;
	}

	public void setEloluckywars(int eloluckywars) {
		this.eloluckywars = eloluckywars;
	}

	public int getElopvpop() {
		return elopvpop;
	}

	public void setElopvpop(int elopvpop) {
		this.elopvpop = elopvpop;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	
}
