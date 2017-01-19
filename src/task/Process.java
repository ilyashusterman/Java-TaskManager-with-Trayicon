package task;


public class Process {
private String name;
private String status;
private String details;




public Process() {
	super();
	// TODO Auto-generated constructor stub
}
public Process(String name, String status, String details) {
	super();
	this.name = name;
	this.status = status;
	this.details = details;
}
@Override
public String toString() {
	return "Process [name=" + name + ", status=" + status + ", details=" + details + "]";
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public String getDetails() {
	return this.details;
}
public void setDetails(String details2) {
	this.details = details2;
}

}
