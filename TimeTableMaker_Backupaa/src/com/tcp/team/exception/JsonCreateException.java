package com.tcp.team.exception;

/**
 * @author Leopard
 * 			Json이 생성되지 않은 경우에 이 예외를 발생시킨다
 *
 */
public class JsonCreateException extends Exception {

	private static final long serialVersionUID = 1L;

	public JsonCreateException(String msg) {
		super(msg);
	}
	
	/**
	 * @param args
	 * @설명	Json이 생성되지 않았을 경우에 사용한다
	 * 			즉, Json 유효성 검사를 해서 생성되지 않은 경우에 발생
	 */
	public static void main(String[] args) {
		
		try {
			throw new JsonCreateException("Test");		
		} catch(JsonCreateException exp) {
			System.out.println(exp.toString());
		}
	}
}
