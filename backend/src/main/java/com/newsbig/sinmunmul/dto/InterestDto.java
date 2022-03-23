package com.newsbig.sinmunmul.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("InterestGetRequest")
public class InterestDto {
	@ApiModelProperty(name="등록된 관심사 리스트", example="[{1, 1, 1, 1, 'n', '2022-03-22 10:56:30', test@test.com, '2022-03-22 10:56:30', test@test.com}, ..]")
	List<CodeDto> yesInterest; 
	@ApiModelProperty(name="등록되어 있지 않은 관심사 리스트", example="[{1, 1, 1, 1, 'n', '2022-03-22 10:56:30', test@test.com, '2022-03-22 10:56:30', test@test.com}, ..]")
	List<CodeDto> noInterest;
}
