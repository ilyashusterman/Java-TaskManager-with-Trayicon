package test;

import task.TrayIconTask;

public class Test {
public static void main(String[] args) {
	TrayIconTask.getInstance().execute(new task.Process("hello world", "ON", "good "),"Test");
	
}
}
