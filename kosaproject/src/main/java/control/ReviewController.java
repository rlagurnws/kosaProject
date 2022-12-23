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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.exception.AddException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.service.ReviewService;
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
	public ResponseEntity<?> write(HttpSession session, int reviewNo,
			@RequestPart(required = false)   MultipartFile fImg,			
			Review rv) {
		//String loginedId = (String)session.getAttribute("loginedId");
		String loginedId = "";
		rv.setMemId(loginedId);

		try {
			  service.insert(rv);
			//파일업로드(f, fImg)작업
			if(fImg != null) {
				//고쳐야함
				//				com.my.util.Attach.upload(reviewNo, fImg);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping(value="modify/{reviewNo}")
	public Map<String, Object> modify(HttpSession session,@PathVariable int reviewNo, Review rv, @RequestPart MultipartFile img){
		Map<String, Object> map = new HashMap<>();
		try {
			rv.setReviewNo(reviewNo);
			service.reviewMemory(rv);
			map.put("status", 1);
			map.put("msg", "수정 성공!");

			boolean flag = false;
			String imgOriginName = img.getOriginalFilename();
			long imgSize = img.getSize();

			if(imgSize > 0 && !"".equals(imgOriginName)) {
				flag = true;
			}
			if(flag) {
				String saveDirectory = "C:\\Users\\skdud\\OneDrive\\바탕 화면\\review";
				File dir = new File(saveDirectory);
				if(!dir.exists()) {
					dir.mkdir();
				}

				String saveFileName = rv.getMemId()+"_"+ rv.getStNum()+"_" + imgOriginName;

				File[] files = dir.listFiles();
				for(File f1: files) {
					String[] fName = f1.getName().split("_");
					if(fName[0].equals(rv.getMemId()) && fName[1].equals(""+rv.getStNum())) {
						f1.delete();
					}
				}

				File saveImg = new File(saveDirectory, saveFileName);
				FileCopyUtils.copy(img.getBytes(), saveImg);
			}
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}



	@DeleteMapping("delete/{reviewNo}")
	public ResponseEntity<?> delete(@PathVariable int reviewNo) throws  RemoveException  {
		//		reviewNo = 6;
		service.delete(reviewNo);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	


}
