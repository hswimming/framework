package com.kh.aop.character;

import com.kh.aop.weapon.Weapon;

import lombok.Data;

// 생성자가 없을 경우 JVM이 기본 생성자만 준다.

@Data
public class Character {
	
	private String name;
	
	private int level;
	
	private Weapon weapon;
	
	public String quest(String questName) throws Exception {
		
//		if(true) {
//			throw new Exception("퀘스트 처리 중 예외 발생");
//		}
		
//		System.out.println(questName + " 퀘스트 진행중...");
		
		// return 값을 aop:after-returning에서 받아온다.
		return "[" + questName + "] 퀘스트 진행중...";
	}
	
}