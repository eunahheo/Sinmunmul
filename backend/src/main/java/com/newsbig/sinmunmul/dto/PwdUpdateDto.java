package com.newsbig.sinmunmul.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PwdUpdatePutRequest")
public class PwdUpdateDto {
	@ApiModelProperty(name="변경 전 비밀번호", example="beforePwd!@#")
	String userPwd;
	@ApiModelProperty(name="변경 후 비밀번호", example="afterPwd!@#")
	String newUserPwd;
}
