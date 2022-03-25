package com.newsbig.sinmunmul.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.UnavailableException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.SigninDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void signIn(User user) {
		
		userRepository.save(User.builder()
				.userEmail(user.getUserEmail())
				.userPwd(passwordEncoder.encode(user.getUserPwd()))
				.userGender(user.getUserGender())
				.userAge(user.getUserAge())
				.userSgtype(user.getUserSgtype())
				.regDt(user.getRegDt())
				.regId(user.getUserEmail())
				.modDt(user.getModDt())
				.modId(user.getUserEmail())
				.build());
	}

	@Override
	public Map<String, Object> getUserByEmail(String email) {
		Map<String, Object> result = new HashMap<>();
		
		User user = userRepository.findBydelYnAndUserEmail("n", email).orElseThrow(() -> new NotExistsUserException());
		
		result.put("userSeq", user.getUserSeq());
		result.put("userEmail", user.getUserEmail());
		result.put("userPwd", user.getUserPwd());
		result.put("userGender", user.getUserGender());
		result.put("userAge", user.getUserAge());
		result.put("userSgType", user.getUserSgtype());
		result.put("userRegDt", user.getRegDt());
		
		return result;
	}

	@Override
	public Map<String, String> getKakaoUserInfo(String accessToken) {
		
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		Map<String, String> result = new HashMap<>();
		
	    //access_token을 이용하여 사용자 정보 조회
	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("POST");
	       conn.setDoOutput(true);
	       conn.setRequestProperty("Authorization", "Bearer " + accessToken); //전송할 header 작성, access_token전송

	       //결과 코드가 200이라면 성공
	       int responseCode = conn.getResponseCode();
	       System.out.println("responseCode : " + responseCode);

	       result.put("responseCode", Integer.toString(responseCode));
	       
	       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       String line = "";
	       String obj = "";
	       while ((line = br.readLine()) != null) {
	           obj += line;
	       }
	       System.out.println("response body : " + obj);

	       JSONParser parser = new JSONParser();
	       JSONObject element = (JSONObject) parser.parse(obj);
	       JSONObject kakao_account = (JSONObject) element.get("kakao_account");
	       
	       boolean hasEmail = (boolean) kakao_account.get("has_email");
	       boolean hasGender = (boolean) kakao_account.get("has_gender");
	       String email = "";
	       String gender = "";
	       if(hasEmail) {
	    	   email = kakao_account.get("email").toString();
	       }
	       if(hasGender) {
	    	   gender = kakao_account.get("gender").toString();
	       }
	       
	       result.put("email", email);
	       result.put("gender", gender);
	       br.close();

	       } catch (NullPointerException e) {
	    	   throw new NullPointerException();
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
		
		return result;
	}

	@Override
	public String changePassword(String email) {
		String password = getRandomPassword(10);
		
		User user = userRepository.findBydelYnAndUserEmail("n", email).orElseThrow(() -> new NotExistsUserException());
		user.setUserPwd(passwordEncoder.encode(password));
		user.setModId(email);
		user.setModDt(TimeUtils.curTime());
		userRepository.save(user);
		
		return password;
	}

	// 임시 비밀번호 발급
	public String getRandomPassword(int size) {
	    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
	            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
	            'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&' ,'*'};
	    StringBuffer sb = new StringBuffer();
	    SecureRandom sr = new SecureRandom();
	    sr.setSeed(new Date().getTime());
	    int idx = 0;
	    int len = charSet.length;
	    for (int i = 0; i < size; i++) {
	        idx = sr.nextInt(len);
	        sb.append(charSet[idx]);
		}
		return sb.toString();
	}
	
}
