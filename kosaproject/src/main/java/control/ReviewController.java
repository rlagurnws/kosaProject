package control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

import com.my.vo.Member;
import com.my.vo.Review;

//@RestController
//@RequestMapping("review/*")
public class ReviewController {
	@Autowired
	private Review service;
	
	@PostMapping("write")
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart   List<MultipartFile> f, //multiple속성인 경우 List타입으로 매핑 
			@RequestPart   MultipartFile fImg,
			
			Review rv) {
		String loginedId = (String)session.getAttribute("loginedId");
		rv.setmemId(loginedId);
		
		try {
			int reviewNo = service.writereview(rv);
			//파일업로드(f, fImg)작업
		
			com.my.util.Attach.upload(reviewNo, f);
			com.my.util.Attach.upload(reviewNo, fImg);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(e.getMessage(),
					                    HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	@GetMapping("reviewlist")
	public ResponseEntity<?> list(int currentPage) throws FindException{
		PageBean<Review> pb = service.getPageBean(currentPage);//service.findAll();
		return new ResponseEntity<>(pb, HttpStatus.OK);
	}
	
	
	
	@GetMapping("reviewdetail")
	public ResponseEntity<?> detail(int reviewNo) throws FindException{
		Review rv = service.findByreviewNo(reviewNo);
		//글번호별 첨부파일이름들 응답 
		List<String>fileNames = new ArrayList<>();
		String saveDirectory = "c:\\files";
		File dir = new File(saveDirectory); 
		String[] allFileNames = dir.list();
		for(String fn: allFileNames) {
			if(fn.startsWith(reviewNo + "_")) {
				fileNames.add(fn);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("rv", rv);
		map.put("fileNames", fileNames);			
		return new ResponseEntity<>(map, HttpStatus.OK);		
	}
	
	
	@PostMapping("reviewmodify")
	public ResponseEntity<?> modify(
//			@RequestPart   List<MultipartFile> f, //multiple속성인 경우 List타입으로 매핑 
//			@RequestPart   MultipartFile fImg,
			int reviewNo, 
            String reviewDes, 
            String memId,
            HttpSession session) throws ModifyException{
		Review rv = new Review();
		rv.setreviewNo(reviewNo);
		rv.setreviewDes(reviewDes);
		rv.setmemId(memId);		
		service.modify(rv);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("reviewremove")
	@ResponseBody
	public ResponseEntity<?> remove(int boardNo) throws RemoveException{
		service.remove(boardNo);
		//기존 첨부파일들을 모두 삭제 (c:\\files경로에서 boardNo값으로 시작하는 파일들을 찾아 삭제 )
		String saveDirectory = "c:\\files";
		File dir = new File(saveDirectory);
		File[] files = dir.listFiles();
		for(File f1: files) {
			if(f1.getName().startsWith(boardNo+"_")) {
				f1.delete();
			}
		}
					
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
