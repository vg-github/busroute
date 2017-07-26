package com.goeuro.entities;

public class Response {
	
	String name;
	String value;
	String msg;

    public Response(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public Response(String name, String value, String msg) {
        this.name = name;
        this.value = value;
        this.msg = msg;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Response [\n\tname=" + name + ",\n\tvalue=" + value + ",\n\tmsg=" + msg + "\n]";
	}
}