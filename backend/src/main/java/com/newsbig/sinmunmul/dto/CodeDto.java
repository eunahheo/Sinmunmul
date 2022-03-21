package com.newsbig.sinmunmul.dto;

<<<<<<< HEAD
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Service
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class CodeDto {
	private int codeGroup;
	private int code;
=======
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel("CodeDto")
public class CodeDto {
	@ApiModelProperty(name="변경 전 비밀번호", example="beforePwd!@#")
	int codegroup;
	@ApiModelProperty(name="변경 후 비밀번호", example="afterPwd!@#")
	int code;
>>>>>>> feature/be-58-updateInterest
}
