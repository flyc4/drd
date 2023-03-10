package com.multi.drd.member;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.multi.drd.memberbio.MemberBioDTO;
import com.multi.drd.memberbio.MemberBioService;
import com.multi.drd.personalroutine.PersonalRoutineDTO;
import com.multi.drd.routine.RoutineDTO;
import com.multi.drd.utils.JsonUtils; 

/*
 * @SessionAttributes("member") 
 *  - member: attribute 이름
 *  - 컨트롤러에서 member라는 이름으로 Model 객체에 저장된 attribute가 있다면 이를 세션에 저장. login 관련 메소드 참조.
 */

@Controller  
@RequestMapping("member")
@SessionAttributes("member") 
public class MemberController {
	MemberService memberService; 
	MemberBioService memberBioService; 
	
	public MemberController() {} 
	
	@Autowired
	public MemberController(MemberService memberService,MemberBioService memberBioService) {
		super();
		this.memberService = memberService;	
		this.memberBioService = memberBioService;	
	}     
	
	@RequestMapping(value = "/login.do",method = RequestMethod.GET)
	public String loginPage(HttpSession session) { 
		String viewName = "member/login";
		
		// 로그인이 되어 있다면 index 페이지로 이동
		if(session.getAttribute("member") != null) {
			viewName = "redirect: /dashboard/read";
		}
		return viewName;
	} 

	@RequestMapping(value = "/login.do",method = RequestMethod.POST)
	public String login(MemberDTO loginMember, Model model) {
		
		MemberDTO member = memberService.login(loginMember);
		String viewName = "";   
		
		  // 로그인 성공 시 
		  if(member != null) { 
			  model.addAttribute("member", member); 
			  viewName = "redirect: /dashboard/read";  
			   
		  } 
		  else { 
			  model.addAttribute("isLoginFailed", true);
			  viewName = "member/login"; 
		  }  
		  return viewName;
	} 
	
	@RequestMapping(value = "/register.do",method = RequestMethod.GET)
	public String registerPage(HttpSession session) { 
		String viewName = "member/register";
		
		// 로그인이 되어 있다면 index 페이지로 이동
		if(session.getAttribute("member") != null) {
			viewName = "redirect: /dashboard/read";
		}
		return viewName;
	} 
	
	@RequestMapping(value = "/register.do",method = RequestMethod.POST)
	public String register(MemberDTO member, MemberBioDTO memberBio, Model model, 
			 HttpServletRequest request) throws Exception {
		
		 // String 으로 받은 routine 정보를 파싱한 후 pRoutine에 설정
		 RoutineDTO routine = JsonUtils.parseRoutineDTO(request.getParameter("selectedRoutine")); 
		 PersonalRoutineDTO pRoutine = new PersonalRoutineDTO();
		 pRoutine.setRoutineSEQ(routine.getRoutineSEQ());
		 pRoutine.setCardioObj(routine.getCardioObj()); 
		 pRoutine.setFitnessObj(routine.getFitnessObj());  
		
		 boolean isRegistered = memberService.register(member, memberBio, pRoutine) > 0;   
		
		 String viewName = "";  
		
		 // 회원 가입 성공 시 
		 if(isRegistered) {   
			  model.addAttribute("member", member); 
			  viewName = "redirect: /dashboard/read"; 
		  } 
		  else {
			  viewName = "member/register"; 
		  }
		 
		  return viewName; 
	} 

	// 닉네임으로 사용자 검색. (회원 가입 시 사용 가능 여부 체크 등에 활용)
	@RequestMapping(value = "/findByNickName.do", method = RequestMethod.POST) 
	@ResponseBody
	public MemberDTO findByNickName(String nickName) {
		return memberService.findByNickName(nickName);
	} 
	
	// 이메일로 사용자 검색. (회원 가입 시 사용 가능 여부 체크 등에 활용)
	@RequestMapping(value = "/findByEmail.do", method = RequestMethod.POST) 
	@ResponseBody
	public MemberDTO findByEmail(String email) {
		return memberService.findByEmail(email);
	} 
	
	/*
	 * 회원 가입 시 추천 루틴 가져오기 . 
	 * 인송님께 말하기
	 * 230112 현재 질병 정보만을 기반으로 루틴을 추천하지만, 추후 확장을 위해 param 변수 생성
	 * 
	 */
	@RequestMapping(value = "/findRoutineByRegisterInfo.do", method = RequestMethod.POST) 
	@ResponseBody
	public List<RoutineDTO> findByRegisterInfo(HttpServletRequest request) {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
	    
	    Enumeration<String> enumber = request.getParameterNames();
	    
	    while (enumber.hasMoreElements()) {
	        String key = enumber.nextElement().toString();
	        String value = request.getParameter(key);

	        param.put(key, value);  
	    }  
		List<RoutineDTO> routineList = memberService.findRoutineByRegisterInfo(param);
	
		return routineList;
	} 

	@RequestMapping(value = "/index.do", method = RequestMethod.GET) 
	public String indexPage(){
		
		return "member/index";
	} 
	
	 @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	 public String logout(SessionStatus status) {
		 status.setComplete(); // 세션에 있는 memeber 객체를 제거 
		 return "main/main";
	 }  

	 // FAQ 페이지로 이동
	 @RequestMapping(value = "/faqPage", method = RequestMethod.GET)
	 public String faqPage() {
		 
		 return "faq/faq";
	 }  
	 
	 // ---- 테스트용 메소드 ----
	/* 로그인 및 회원 가입 시 세션이 제대로 생성 되었는 지 확인하기 위함
	 * 확인 방법: login혹은 register 메소드(POST)의 리턴 값을 다음의 값으로 변경 후 콘솔에 출력되는 값 확인
	 * "redirect: /member/sessiontest.do"
	 */
	@RequestMapping(value = "/sessiontest.do", method = RequestMethod.GET)
	public String sessionTest(HttpSession session) {
		
		return "member/index";
	} 
	/*
	 * Member의 기본키를 통해 MemberBio를 가져오는 기능 예시
	 */
	@RequestMapping(value = "/biotest.do", method = RequestMethod.GET)
	public String bioTest(HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute("member");
		
		return "member/index";
	} 

}
