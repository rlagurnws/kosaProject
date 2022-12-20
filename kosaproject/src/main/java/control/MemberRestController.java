package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.service.MemberService;
import com.my.vo.Member;

//@Controller
@RestController
@RequestMapping("member/*")
public class MemberRestController {
	@Autowired
	MemberService service;
	
	@PostMapping(value="iddupchk")
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
	
	@PostMapping(value="signup")
	public Map<String, Object> signup(@RequestBody Member m){
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
	
	@PostMapping(value="login")
	public Map<String,Object> login(HttpSession session,@RequestBody Member m ){
		Map<String, Object> map = new HashMap<>();
		try {
			Member m2 = service.searchById(m.getMemId());
			if(m2.getMemPwd().equals(m.getMemPwd())) {
				if(m2.getMemState() == 0) {
					throw new RemoveException();
				}
				session.setAttribute("id", m2.getMemId());
				session.setAttribute("power", m2.getMemPower());
				session.setAttribute("nick", m2.getMemNick());
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
	
	@GetMapping(value="logout")	
	public void logout(HttpSession session){
		session.invalidate();
	}
	
	@GetMapping(value="mypage")
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
	
	@PostMapping(value="modify")
	public Map<String, Object> memmodi(@RequestBody Member m){
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
	
	@GetMapping(value="delete")
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
		
	@GetMapping(value="session")
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
