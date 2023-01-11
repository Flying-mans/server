package com.jejuro.server.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jejuro.server.entity.Alarm;
import com.jejuro.server.entity.Member;
import com.jejuro.server.service.AlarmService;
import com.jejuro.server.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {

	public MemberController() {

	}

	@Autowired
	private MemberService service;

	@Autowired
	private AlarmService alarmService;

	@GetMapping("signup")
	public String siginup(Member member){
		return "html/sign-up/sign-up";
	}

	/**
	 * @Valid
	 * 유효성 겅사를 위해
	 * spring-boot-starter-validation dependency 주입 후 사용 가능
	 * 
	 * @ModelAttribute 
	 * html name과 entity의 필드가 동일하다면 스프링이 setter method를 호출하면서 값을 알아서 담아줌
	 *
	 * @param member
	 * 이메일 닉네임 패스워드 핸드폰
	 * @return 로그인 페이지
	 */
	@PostMapping("signup")
	public String signup(@Valid @ModelAttribute Member member, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			return "html/sign-up/sign-up";
		}

		if(!member.getPassword().equals(member.getPasswordCK())) {
			bindingResult.rejectValue("passwordCK", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
			return "html/sign-up/sign-up";
		}

		try {
			service.add(member);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "html/sign-up/sign-up";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "html/sign-up/sign-up";
		}

		

		return "html/login/login";
	}

	// 마이페이지 기능 확인을 위한 로그인 테스트 페이지
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("member", new Member());
		return "html/sign-up/sign-up";
	}

	// 내 정보 확인 테스트 페이지
	@PostMapping("/register")
	public String register(@RequestParam("email") String email,
			@RequestParam("nickname") String nickName,
			@RequestParam("password") String password,
			@RequestParam("phoneNum") String phoneNum) {
		Member member = new Member(email, nickName, password, phoneNum);
		service.add(member);
		Member getMember = service.getByEmail(email);
		String result = "redirect:/member/myinfo/" + getMember.getMember_id();
		return result;
	}

	// 내 정보 확인
	@GetMapping("/myinfo")
	public String goMyinfo(Model model, Principal principal) {

		String username = principal.getName();
		Member member = service.getByNickname(username);

		String result = "redirect:/member/myinfo/" + member.getMember_id();
		return result;
	}

	// 내 정보 확인
	@GetMapping("/myinfo/{id}")
	public String displayMyinfo(@PathVariable("id") int id,
			Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		return "html/myinfo/myinfo";
	}

	// 내 정보 확인을 위한 테스트
	@PostMapping("/myinfo/{id}")
	public String displayMyinfo(@PathVariable("id") int id) {
		service.get(id);
		return "html/myinfo/myinfo";
	}

	// 내 정보 삭제
	@PostMapping("/myinfo/delete")
	public String deleteMember(@RequestParam("id") int id) {
		service.delete(id);
		return "redirect:/member/register";
	}

	// 내 정보 수정하기
	@GetMapping("/myinfo/update/{id}")
	public String updateMember(@PathVariable("id") int id,
			Model model	) {

		Member member = service.get(id);
		model.addAttribute("member", new Member(id, member.getEmail(), null, null, null));
		return "html/myinfo/update";
	}

	// 내 정보 수정 완료
	@PostMapping("/myinfo/update/complete")
	public String updateComplete(Member member) {
		int id = service.update(member);
		String result = "redirect:/member/myinfo/" + id;
		return result;
	}

	// 로그인 테스트
	@GetMapping("login")
	public String login() {
		return "html/login/login";
	}

	@PostMapping("login")
	public String login(String email, String password, String returnURL, HttpSession session) {
		Member member = service.getByEmail(email);

		if (member == null)
			return "redirect:login?error";
		else if (!member.getPassword().equals(password))
			return "redirect:login?error";

		session.setAttribute("email", member.getEmail());

		if (returnURL != null && returnURL.equals(""))
			return "reditect:" + returnURL;

		return "redirect:/index";
	}

	// 알람 설정===========================================

	// 알람 출력
	@GetMapping("/myinfo/alarm/{id}")
	public String displayAlarm(@PathVariable("id") int id, Model model) {

		List<Alarm> alarm = alarmService.getList(id);
		model.addAttribute("alarm", alarm);

		return "html/myinfo/alarm";

	}

	// 알람 삭제
	@PostMapping("/myinfo/alarm/delete")
	public String deleteAlarm(@RequestParam("id") int id, Model model) {

		model.addAttribute("id", id);

		// 알람 id로 회원 id를 가져온다
		int memberId = alarmService.getMemberId(id);
		alarmService.delete(id);
		return "redirect:/member/myinfo/alarm/" + memberId;
	}
}
