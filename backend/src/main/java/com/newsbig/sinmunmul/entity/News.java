
package com.newsbig.sinmunmul.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성한다. 접근 권한을 설정하여 어느 곳에서나 객체를 생성할 수 있는 상황을 막는다.
@Getter
@Setter
@ToString
@AllArgsConstructor
public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_seq")
	private int newsSeq;
	
	@Column(name = "news_title")
	private String newsTitle;
	
	@Column(name = "news_link")
	private String newsLink;
	
	@Column(name = "news_press")
	private String newsPress;
	
	@Column(name = "news_author")
	private String newsAuthor;
	
	@Column(name = "news_author_email")
	private String newsAuthorEmail;
	
	@Column(name = "news_desc")
	private String newsDesc;
	
	@Column(name = "news_summary")
	private String newsSummary;
	
	@Column(name = "news_reg_dt")
	private String newsRegDt;
	
	@Column(name = "news_mod_dt")
	private String newsModDt;
	
	@Column(name = "news_photo")
	private String newsPhoto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "code_group", referencedColumnName ="code_group")
	private CommonCodeGroup commonCodeGroup;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "code", referencedColumnName = "code")
	private CommonCode commonCode;

	@Column(name = "del_yn")
	private String delYn;
	
	@Column(name = "reg_dt")
	private String regDt;
	
	@Column(name = "reg_id")
	private String regId;
	
	@Column(name = "mod_dt")
	private String modDt;
	
	@Column(name = "mod_id")
	private String modId;
}
