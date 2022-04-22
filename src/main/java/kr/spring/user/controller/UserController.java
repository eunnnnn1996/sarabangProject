package kr.spring.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;
import kr.spring.util.AuthBlockException;
import kr.spring.util.AuthCheckException;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public UserVO initCommand() {
		return new UserVO();
	}
	
	@GetMapping("/user/signUp.do")
	public String signUpForm() {
		return "signUp";
	}
	@PostMapping("/user/signUp.do")
	public String signUpSubmit(@Valid UserVO userVO, BindingResult result) {
		
		if (result.hasErrors()) {
			return signUpForm();
		}
		
		userService.insertUser(userVO);
		
		return "redirect:/main/main.do";
	}
	
	@PostMapping("/user/login.do")
	public String signIn(UserVO userVO, Model model, HttpSession session,
												HttpServletRequest request) {

		try {
			UserVO user = userService.selectCheckUser(userVO.getUser_id());

			boolean check = false;
			if (user != null) {
				// 비밀번호 일치 여부 체크 //사용자가 입력한 비밀번호
				check = user.isCheckedPassword(userVO.getPasswd());
			}
			if (user.getUser_auth() == 0) { // 탈퇴회원의 경우 - 아이디만 확인 후
				throw new AuthCheckException();
			} else {
				if (user.getUser_auth() == 1 && check) {// 정지회원의 경우 - 아이디, 비번 확인 후
					throw new AuthBlockException();
				}
				if (check) {
					// 인증 성공, 로그인 처리
					session.setAttribute("user_num", user.getUser_num());
					session.setAttribute("user_name", user.getUser_name());
					session.setAttribute("user_auth", user.getUser_auth());
					session.setAttribute("user_photo", user.getPhoto());

					return "redirect:/main/main.do";
				}
			}

			throw new Exception();
		} catch (AuthCheckException e) {// 로그인 오류
			// 인증 실패로 로그인 폼을 호출
			model.addAttribute("message", "로그인 오류"); 
			model.addAttribute("url", request.getContextPath() + "/main/main.do");
		} catch (AuthBlockException e) {// 정지회원의 경우
			model.addAttribute("message", "정지된 회원 입니다"); 
			model.addAttribute("url", request.getContextPath() + "/main/main.do");
		} catch (Exception e) {
			model.addAttribute("message", "아이디 또는 비밀번호 불일치"); 
			model.addAttribute("url", request.getContextPath() + "/main/main.do");	 
		}
		return "common/resultView";
	}
		//로그아웃
		@RequestMapping("/user/logout.do")
		public String processLogout(HttpSession session) {
			
			session.invalidate();

			return "redirect:/main/main.do";
		}
}
