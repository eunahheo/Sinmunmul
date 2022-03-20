package com.newsbig.sinmunmul.dto;

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
}
