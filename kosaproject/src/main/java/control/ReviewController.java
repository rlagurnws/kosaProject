package control;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.service.ReviewService;
import com.my.vo.Member;
import com.my.vo.Review;

@RestController
@RequestMapping("review/*")
public class ReviewController {
	@Autowired
	private ReviewService service;

	@GetMapping("a")
	public void a() {
		System.out.println("a....");
	}
	@PostMapping("write")
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart(required = false)   MultipartFile fImg,			
			Review rv) {
		//String loginedId = (String)session.getAttribute("loginedId");
		String loginedId = "";
		rv.setMemId(loginedId);

		try {
			int reviewNo = service.insert(rv);
			//파일업로드(f, fImg)작업
			if(fImg != null) {
				com.my.util.Attach.upload(reviewNo, fImg);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	
//	@PostMapping(value="modify")
//	public Map<String, Object> modify(HttpSession session, Review rv, @RequestPart MultipartFile img){
//		Map<String, Object> map = new HashMap<>();
//		try {
//			service.reviewMemory(rv);
//			map.put("status", 1);
//			map.put("msg", "수정 성공!");
//			
//			boolean flag = false;
//			String imgOriginName = img.getOriginalFilename();
//			long imgSize = img.getSize();
//			
//			if(imgSize > 0 && !"".equals(imgOriginName)) {
//				flag = true;
//			}
//			if(flag) {
//				String saveDirectory = "D:\\\\MyBACK\\\\kosafront\\\\src\\\\main\\\\webapp\\\\project_image\\\\profile";
//				File dir = new File(saveDirectory);
//				if(!dir.exists()) {
//					dir.mkdir();
//				}
//				
//				String saveFileName = rv.getMemId()+"_"+imgOriginName;
//				
//				File[] files = dir.listFiles();
//				for(File f1: files) {
//					String[] fName = f1.getName().split("_");
//					if(fName[0].equals(rv.getMemId())) {
//						f1.delete();
//					}
//				}
//				
//				File saveImg = new File(saveDirectory, saveFileName);
//				FileCopyUtils.copy(img.getBytes(), saveImg);
//			}
//		} catch (ModifyException e) {
//			e.printStackTrace();
//			map.put("status", 0);
//			map.put("msg", e.getMessage());
//		} catch (IOException e) {
//			e.printStackTrace();
//			map.put("status", 0);
//			map.put("msg", e.getMessage());
//		}
//		return map;
//	}
//	
	
	
	@DeleteMapping("delete/{reviewNo}")
	public ResponseEntity<?> delete(@PathVariable int reviewNo) throws  RemoveException  {
//		reviewNo = 6;
		service.delete(reviewNo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	//내용수정
			//		@PostMapping("update/{currentPage}")
			//		public ResponseEntity<?> update(
			//				int reviewNo,  
			//	            String reviewDes, 
			//	            String memId,
			//	            HttpSession session) throws ModifyException{
			//			Review rv = new Review();
			//			rv.setReviewNo(reviewNo);
			//			rv.setReviewDes(reviewDes);
			//			rv.setMemId(memId);
			//			service.update(rv);
			//
			//			return new ResponseEntity<>(HttpStatus.OK);
			//		}
			//		

			//	@GetMapping("list/{currentPage}")
			//	public ResponseEntity<?> list(@PathVariable int currentPage) throws FindException{
			//		PageBean<Review> pb = service.getPageBean(currentPage);//service.findAll();
			//		return new ResponseEntity<>(pb, HttpStatus.OK);
			//	}


			//	@GetMapping("detail/{currentPage}")
			//	public ResponseEntity<?> detail(String memId) throws FindException{
			//		Review rv = service.selectById(memId);
			//		//글번호별 첨부파일이름들 응답 
			//		List<String>fileNames = new ArrayList<>();
			//		String saveDirectory = "c:\\files";
			//		File dir = new File(saveDirectory); 
			//		String[] allFileNames = dir.list();
			//		for(String fn: allFileNames) {
			//			int reviewNo;
			//			if(fn.startsWith(reviewNo + "_")) {
			//				fileNames.add(fn);
			//			}
			//		}
			//		Map<String, Object> map = new HashMap<>();
			//		map.put("rv", rv);
			//		map.put("fileNames", fileNames);			
			//		return new ResponseEntity<>(map, HttpStatus.OK);		
			//	}


}
