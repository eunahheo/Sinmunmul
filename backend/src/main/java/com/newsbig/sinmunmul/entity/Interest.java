package com.newsbig.sinmunmul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성한다. 접근 권한을 설정하여 어느 곳에서나 객체를 생성할 수 있는 상황을 막는다.
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Interest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK, Auto_Increment로 설정해서 직접 할당 방식이 아니라, 자동으로 생성되도록 하기 위한
														// 어노테이션
	@Column(name = "interest_seq")
	private int interestSeq;
	
	@Column(name = "user_seq")
	private int userSeq;
	
	@Column(name = "code_group")
	private int codeGroup;
	
	@Column(name = "code")
	private int code;
	
	@Column(name = "del_yn")
	private String delYn = "n";
	
	@Column(name = "reg_dt")
	private String regDt;
	
	@Column(name = "reg_id")
	private String regId;
	
	@Column(name = "mod_dt")
	private String modDt;
	
	@Column(name = "mod_id")
	private String modId;

	@Builder
	public Interest(int userSeq, int codeGroup, int code, String regDt, String regId, String modDt, String modId) {
		super();
		this.userSeq = userSeq;
		this.codeGroup = codeGroup;
		this.code = code;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}
	
	

}
