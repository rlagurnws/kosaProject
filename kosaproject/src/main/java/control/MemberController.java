package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.service.MemberService;
import com.my.vo.Member;

@Controller
public class MemberController {

	@Autowired
	MemberService service;
	
	@PostMapping("iddupchk")
	@ResponseBody
	public Map<String, Object> iddupchk(String id) {
		Map<String, Object> map = new HashMap<>();
		try {
			service.idDupChk(id);
			map.put("status", 0);
			map.put("msg", "아이디 사용불가");
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 1);
			map.put("msg", "아이디 사용가능");
		}
		return map;
	}
	
	@PostMapping("signup")
	@ResponseBody
	public Map<String, Object> signup(Member m){
		Map<String, Object> map = new HashMap<>();
		try {
			service.signUp(m);
			map.put("status", 1);
			map.put("msg", "회원가입 성공!");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	@GetMapping("login")
	@ResponseBody
	public Map<String,Object> login(HttpSession session, String id, String pwd){
		Map<String, Object> map = new HashMap<>();
		try {
			Member m = service.searchById(id);
			if(m.getMemPwd().equals(pwd)) {
				if(m.getMemState() == 0) {
					throw new RemoveException();
				}
				session.setAttribute("id", id);
				session.setAttribute("power", m.getMemPower());
				session.setAttribute("nick", m.getMemNick());
				System.out.println("------------------------로그인 컨트롤 ----------------");
				System.out.println(session.getAttribute("power"));
				System.out.println("------------------------로그인 컨트롤 ----------------");
				map.put("status", 1);
				map.put("msg", "로그인 성공");
			}else {
				throw new FindException();
			}
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "회원 정보가 일치하지 않습니다.");
		} catch (RemoveException e) {
			map.put("status", 0);
			map.put("msg", "탈퇴한 계정입니다.");
		}
		return map;
	}
	
	@GetMapping("logout")
	@ResponseBody
	public void logout(HttpSession session){
		session.invalidate();
	}
	
	@GetMapping("mypage")
	@ResponseBody
	public Map<String, Object> mypage(HttpSession session){
		String id = (String)session.getAttribute("id");
		Map<String, Object> map = new HashMap<>();
		try {
			Member m = service.searchById(id);
			map.put("status",1);
			map.put("member", m);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	@PostMapping("memmodi")
	@ResponseBody
	public Map<String, Object> memmodi(Member m){
		Map<String, Object> map = new HashMap<>();
		try {
			service.memMody(m);
			map.put("status", 1);
			map.put("msg", "변경 !");
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "변경 실패!");
		}
		return map;
	}
	
	@GetMapping("delmem")
	@ResponseBody
	public Map<String, Object> delmem(HttpSession session){
		String id = (String)session.getAttribute("id");
		Map<String, Object> map = new HashMap<>();
		
		try {
			service.deleteMem(id);
			session.invalidate();
			map.put("status", 1);
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	@GetMapping("session")
	@ResponseBody
	public Map<String, Object> session(HttpSession session){
		Map<String, Object> map = new HashMap<>();
		String id = (String)session.getAttribute("id");
		if(id == null) {
			map.put("status", 0);
			return map;
		}else {
			int power = (Integer)session.getAttribute("power");
			String nick = (String)session.getAttribute("nick");
			map.put("status", 1);
			map.put("id", id);
			map.put("power", power);
			map.put("nick", nick);
			return map;
		}
	}
	
}
