package com.kh.aop.aspect;

import java.util.Iterator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.kh.aop.annotation.Repeat;

@Aspect // spring에서 Aspect를 만들려면 @Aspect 어노테이션 사용
@Component // 바로 Aspect로 만들지 못하고 빈으로 등록 후에 만들기 때문에 두 어노테이션을 같이 사용
public class PetAspect {
	
//	@Before("execution(* com.kh.aop.pet.Cat.bark())") 포인트 경로를 Cat으로 지정하면 다른 클래스의 메소드는 실행 X
//	@Before("execution(* com.kh.aop.pet.*.bark())") 최상위 경로로 지정해주면 다른 메소드도 호출 가능
//	@Before("execution(* com.kh.aop.pet.*.bark()) && bean(cat)") // pet 패키지의 모든 메소드 호출 시 실행,  bean(cat) : 어드바이스를 지정할 빈의 아이디를 지정하여 실행
//	@Before("execution(* com.kh.aop.pet.*.bark()) && !bean(cat)") // pet 패키지의 모든 메소드 호출 시 실행,  !bean(cat) : cat을 제외한 모든 빈들이 어드바이스 적용 
//	public void before() {
		// before 메소드가 먼저 실행된 후 bark 메소드가 실행된다.
//		System.out.println("안녕~?");
//	}

//	@Around("execution(* com.kh.aop.pet.*.bark()) && @annotation(com.kh.aop.annotation.NoLogging)")
//	@Around("execution(* com.kh.aop.pet.*.bark()) && !@annotation(com.kh.aop.annotation.NoLogging)") // 제외하고 실행시킬 경우
//	public String barkAdvice(ProceedingJoinPoint jp) { // 아무값도 리턴해주지 않으면 반환값이 있다고 할 경우 null값을 준다.
//		String result = null;
//		
//		try {
//			
//			// jp.proceed()를 기준으로 상단 -> before
//			System.out.println("안녕~?");
//			
//			// 포인트컷으로 지정한 대상 메소드를 실행 후 결과값을 리턴 받음 / 파라미터 값을 변수에 넘겨줄 수 있음
//			// proceed의 타입은 오브젝트 타입이므로 형변환 필요 
//			result = (String) jp.proceed();
//			
//			// jp.proceed()를 기준으로 상단 -> after
//			System.out.println(result);
//			
//		} catch (Throwable e) {
//			System.out.println("왜 안 짖니?ㅠㅠ");
//			
//		} 
//		
//		return result;
//	}
	
	@Around("@annotation(com.kh.aop.annotation.Repeat)") // 어노테이션이 붙어있는 메소드만 적용
	public String barkAdvice(ProceedingJoinPoint jp) {
		String result = null;
		
		MethodSignature signature = (MethodSignature) jp.getSignature();
		Repeat repeat = signature.getMethod().getAnnotation(Repeat.class);
		
		// 실제 어노테이션에 넣어준 값을 꺼내올 수 있다.
		
//		System.out.println();
//		System.out.println(repeat.count());
		
		try {
			
			System.out.println("안녕~?");
			
			result = (String) jp.proceed();
			
			for (int i = 0; i < repeat.count(); i++) { // 어노테이션에 준 속성 값을 읽어와서 반복
				System.out.println(result);
			}
			
		} catch (Throwable e) {
			System.out.println("왜 안 짖니?ㅠㅠ");
			
		} 
		
		return result;
	}
}