package com.kh.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // 어노테이션 방식
public class CharacterAspect {
	@Pointcut("execution(* com.kh.aop.character.Character.quest(..)) && args(questName)")
	public void questPointcut(String questName) {
		// 메소드 자체에 기능이 있는 게 아닌 @Pointcut를 재사용하기 위해 정의
	}
	
//	@Before("execution(* com.kh.aop.character.Character.quest(..)) and args(questName)") // 포인트 커트 지정자를 줘야 한다.
//	@Before("questPointcut(questName)")
	// 생성한 퀘스트 메소드의 파라미터 타입의 이름과는 동일할 필요는 없지만 (어드바이스쪽에서 받는) Aspect로 전달되는 파라미터의 이름과는 동일해야 한다.
	public void beforeQuest(String questName) {
		// 퀘스트를 수행하기 전에 필요한 부가 작업을 수행 (퀘스트 메소드가 수행되기 전 내용)
		
		System.out.println(questName + " 퀘스트 준비중~");
	} 
	
//	@After("execution(* com.kh.aop.character.Character.quest(..))")
	public void afterQuest() {
		// 퀘스트의 수행 결과에 상관없이(성공하든 실패하든) 필요한 부가 작업을 수행
		
		System.out.println("퀘스트 수행 완료!");
	}
	
//	@AfterReturning(
////		value = "execution(* com.kh.aop.character.Character.quest(..)) and args(questName)", 
//		value = "questPointcut(questName)",
//		returning = "result"
//	) 
	public void questSuccess(String questName, String result) {
		// 퀘스트 수행 완료 후에 필요한 부가 작업을 수행한다.
		System.out.println(result);
		System.out.println(questName + " 퀘스트 수행 완료~~!!");
	}
	
//	@AfterThrowing(
////		value = "execution(* com.kh.aop.character.Character.quest(..)) and args(questName)",
//		value = "questPointcut(questName)",
//		throwing = "exception" // 던져진 예외를 받을 파라미터의 이름
//	)
	// root-context에서 생성한 aop:after-throwing의 throwing="exception" 변수명
	public void questFail(String questName, Exception exception) {
		// 퀘스트 수행 중에 예외를 던졌을 때 필요한 부가 작업을 수행한다.
		System.out.println(exception.getMessage());
		System.out.println(questName + " 퀘스트 수행 중 에러가 발생하였습니다.");
	}

	@Around("execution(* com.kh.aop.character.Character.quest(..))")
	public String aroundQuest(ProceedingJoinPoint joinPoint) {
		String result = null;
		String questName = "<" + (String) joinPoint.getArgs()[0] + ">";
		
		try {
			// before 어드바이스에 대한 기능을 수행
			System.out.println(questName + " 퀘스트 준비중~");
		
			// 타겟 객체의 메소드를 실행시킨다.
//			joinPoint.proceed();
			
			// 타겟 객체의 메소드에 리턴값이 있는 경우 (타겟 객체가 뭘 리턴할지 모르기 때문에 인터페이스가 오브젝트 타입으로 설정을 해준다)
//			result = (String) joinPoint.proceed();
			
			// 타겟 객체의 메소드에 파라미터 값을 변경해서 전달하는 경우
			result = (String) joinPoint.proceed(new Object[] { questName });
			
			// after-returning 어드바이스에 대한 기능을 수행
			System.out.println(questName + " 퀘스트 수행 완료~~!!");
			
		} catch (Throwable e) {
			// after-throwing 어드바이스에 대한 기능을 수행
			System.out.println(questName + " 퀘스트 수행 중 에러가 발생하였습니다.");
		}
		
		return result;
	}
	
	// 실습 문제
	// Sword, Bow의 attack 메소드 실행 시
	// @Around 어드바이스를 사용하여 코드 작성하세요.
	// 1. attack 메소드 정상 동작
	//	  공격을 준비 중입니다.
	//	  "검을 휘두른다." or "활을 쏜다." 출력
	//	  공격을 성공 했습니다.
	// 2. attack 메소드 실행 중에 예외 발생 시
	// 	  공격을 준비 중입니다.
	//	  에러가 발행하였습니다.

	// "*" : 어떤 클래스든 해당하는 내용을 포함하고 있으면 실행
//	@Around("execution(* com.kh.aop.weapon.*.attack())")
	// 클래스의 상위 타입으로 지정
	@Around("execution(* com.kh.aop.weapon.Weapon.attack())")
	public String attackAdvice(ProceedingJoinPoint jp) {
		String result = null;
		
		try {
			System.out.println("공격을 준비 중입니다.");
			
			result = (String) jp.proceed(); // 어떤 타입으로 리턴될지 모르기 때문에 오브젝트 타입으로 생성 -> 형변환 필요
			
			System.out.println(result);
			System.out.println("공격을 성공 했습니다.");
			
		} catch (Throwable e) {
			
			System.out.println("에러가 발생하였습니다.");
		}

		return result;
	}
}