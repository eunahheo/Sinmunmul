package com.newsbig.sinmunmul.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Builder
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK, Auto_Increment로 설정해서 직접 할당 방식이 아니라, 자동으로 생성되도록 하기 위한
														// 어노테이션
	@Column(name = "user_seq")
	private int userSeq;
	
	@Column(name = "user_email")
	private String userEmail;
	
	@Column(name = "user_pwd")
	private String userPwd;
	
	@Column(name = "user_gender")
	private String userGender;
	
	@Column(name = "user_age")
	private int userAge;
	
	@Column(name = "user_sgtype")
	private String userSgtype;
	
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
	
//	@ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
	
	@Builder
	public User(String userEmail, String userPwd, String userGender, int userAge, String userSgtype, String delYn, String regDt, String regId,
			String modDt, String modId) {
		super();
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.userGender = userGender;
		this.userAge = userAge;
		this.userSgtype = userSgtype;
		this.delYn = "n";
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority("ROLE_USER"));
		return auth;
		
//		return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userPwd;
	}

	@Override
	public String getUsername() {
		return userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
