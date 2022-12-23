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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.ReviewService;
import com.my.vo.Review;

@RestController
@RequestMapping("review/*")
public class ReviewController {
	@Autowired
	private ReviewService service;
	
	static String location = "review/";
	
	@PostMapping(value="write", produces = "application/json;charset=utf-8" )
	public ResponseEntity<?> write1(HttpSession session,
			@RequestPart   MultipartFile chooseFile,
			Review rv){
		String id = (String)session.getAttribute("id");
		Map<String, Object> map = new HashMap<>();
		try {
			int reviewNo = service.insert(rv);
			System.out.println(reviewNo);
//			파일업로드(f, fImg)작업
//			com.my.util.Attach.upload(rv.getStNum(), chooseFile, location, id+reviewNo);
			//-----------------
			File fDir = new File("d://project//review//");
			if(!fDir.exists()) { //업로드 경로가 없는 경우
				fDir.mkdir();
			}
			String originName = chooseFile.getOriginalFilename();
			long fileLength = chooseFile.getSize();
			System.out.println("파일이름:" + originName + ", 파일크기:" + fileLength);

			if(fileLength == 0 || "".equals(originName)) {
				throw new AddException("첨부파일이 비었거나 파일이름이 없습니다");
			}

			String saveFileName =rv.getStNum() + "_" + id+reviewNo + "_" + originName; 

			File saveFile = new File("d://project//review//", saveFileName);
			try {
				//원본의 내용을 복사본에 붙여넣기
				FileCopyUtils.copy(chooseFile.getBytes(), saveFile);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AddException(e.getMessage());
			} 
			//--------------------
			map.put("status", 1);
			return new ResponseEntity<>(map,HttpStatus.OK);
		}catch(AddException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),
					                    HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("list/{stNum}")
	public Map<String, Object> list(@PathVariable int stNum){
		Map<String, Object>map = new HashMap<>();
		List<Review> list = new ArrayList<>(); 
		try {
			list = service.selectBystNumNew(stNum);
			map.put("list", list);
			map.put("status", 1);
			
			List<String> reviewFile = new ArrayList<>();
			String saveDirectory = "D://project/review/";
			File dir = new File(saveDirectory);
			String[] allFileNames = dir.list();
			for(Review rv : list) {				
				for(String fn: allFileNames) {
					if(fn.startsWith(stNum+"_"+rv.getMemId()+rv.getReviewNo())){
						reviewFile.add(fn);
						break;
					}
				}
			}
			map.put("reviewFile",reviewFile);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	
	
//	@GetMapping("reviewdetail")
//	public ResponseEntity<?> detail(int reviewNo) throws FindException{
//		Review rv = service.findByreviewNo(reviewNo);
//		//글번호별 첨부파일이름들 응답 
//		List<String>fileNames = new ArrayList<>();
//		String saveDirectory = "c:\\files";
//		File dir = new File(saveDirectory); 
//		String[] allFileNames = dir.list();
//		for(String fn: allFileNames) {
//			if(fn.startsWith(reviewNo + "_")) {
//				fileNames.add(fn);
//			}
//		}
//		Map<String, Object> map = new HashMap<>();
//		map.put("rv", rv);
//		map.put("fileNames", fileNames);			
//		return new ResponseEntity<>(map, HttpStatus.OK);		
//	}
	
	
//	@PostMapping("reviewmodify")
//	public ResponseEntity<?> modify(
////			@RequestPart   List<MultipartFile> f, //multiple속성인 경우 List타입으로 매핑 
////			@RequestPart   MultipartFile fImg,
//			int reviewNo, 
//            String reviewDes, 
//            String memId,
//            HttpSession session) throws ModifyException{
//		Review rv = new Review();
//		rv.setreviewNo(reviewNo);
//		rv.setreviewDes(reviewDes);
//		rv.setmemId(memId);		
//		service.modify(rv);
//
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
//	@GetMapping("reviewremove")
//	@ResponseBody
//	public ResponseEntity<?> remove(int boardNo) throws RemoveException{
//		service.remove(boardNo);
//		//기존 첨부파일들을 모두 삭제 (c:\\files경로에서 boardNo값으로 시작하는 파일들을 찾아 삭제 )
//		String saveDirectory = "c:\\files";
//		File dir = new File(saveDirectory);
//		File[] files = dir.listFiles();
//		for(File f1: files) {
//			if(f1.getName().startsWith(boardNo+"_")) {
//				f1.delete();
//			}
//		}
//					
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	

}
