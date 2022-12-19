package control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.NoticeService;
import com.my.vo.Notice;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;

	@PostMapping("addnoti")
	@ResponseBody
	public Map<String,Object> addnoti(Notice noti) {
		Map<String,Object> map = new HashMap<>();
		try {
			service.addNoti(noti);
			map.put("msg", "성공!");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("msg", "실패!");
		}
		return map;	
	}
	
	@GetMapping("noticelist")
	@ResponseBody
	public PageBean<Notice> noticeList(int currentPage){
		PageBean<Notice> list = null;
		try {
			list = service.getPageBean(currentPage);
		} catch (FindException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
