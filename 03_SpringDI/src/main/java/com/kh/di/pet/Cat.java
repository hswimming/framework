package com.kh.di.pet;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cat extends Pet {
	
//	private String name;
	
	public Cat(String name) {
		super(name);
	}
	
	@Override
	public String bark() {
		return "야옹~!";
	}

}