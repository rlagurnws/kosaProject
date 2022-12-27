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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.service.StoreService;
import com.my.util.Attach;
import com.my.vo.Menu;
import com.my.vo.Store;

@RestController

@RequestMapping("store/*")
public class StoreController {

	@Autowired
	private StoreService service;

	static String location = "menu/";
	
	@PostMapping(value="new", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart           List<MultipartFile> files,
			@RequestPart	MultipartFile img,
			@RequestParam("Store") String strStore) throws AddException{

		try {
			ObjectMapper mapper = new ObjectMapper();
			Store store = mapper.readValue(strStore, new TypeReference<Store>() {
			});
			System.out.println(store);
			String id = (String) session.getAttribute("id");
			store.setOwnerId(id);
			int i = 0;
			int storeNo =  service.addStore(store);
			System.out.println("여기까진 되는거야");
			for(MultipartFile f: files) {
				Attach.upload(storeNo,f,location, store.getStMenuList().get(i).getMenuName());
				i++;
			}
			
			File fDir = new File("C:/finalPro/thumb");
	         if(!fDir.exists()) {
	            fDir.mkdir();
	         }
	         long imgSize = img.getSize();
	         String imgOriginName = img.getOriginalFilename();
	         String saveFileName = store.getStNum()+"_"+imgOriginName;
	         if(imgSize ==0 || "".equals(imgOriginName)) {
	         }else {
	            File saveImg = new File("C:/finalPro/thumb", saveFileName);
	            FileCopyUtils.copy(img.getBytes(), saveImg);
	         }
	         
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("storelist/{currentPage}")
	@ResponseBody
	public ResponseEntity<?> storereadlist(@PathVariable int currentPage, String search) throws FindException{
		//search="서울";
		PageBean<Store> pb = service.stListGetPageBean(currentPage , search);
		
		
//		String strDirPath = "C:\\files\\"; 
//        
//        File path = new File( strDirPath ); 
//        File[] fList = path.listFiles();  
//		
//        for( int i = 0; i < fList.length; i++ ) { 
//            
//            if( fList[i].isFile() ) { 
//                System.out.println( "[파일] :" + fList[i].getPath() );  // 파일의 FullPath 출력 
//            } 
//            
//        } 
		
		return new ResponseEntity<>(pb, HttpStatus.OK);
	}

	@GetMapping("submitted/{currentPage}")
	public Map<String, Object> submitted(@PathVariable int currentPage) {
		Map<String, Object> map = new HashMap<>();
		try {
			PageBean pb = service.getPageBean(currentPage);
			map.put("status", 1);
			map.put("pb", pb);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

	@PostMapping("{stNum}")
	public Map<String, Object> selectByNo(@PathVariable int stNum) {
		Map<String, Object> map = new HashMap<>();
		try {
			Store s = service.selectByNo(stNum);
			service.viewCntUp(stNum);
			map.put("store", s);
			map.put("status", 1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

	@PostMapping("menu/{stNum}")
	public Map<String, Object> selectMenu(@PathVariable int stNum) {
		Map<String, Object> map = new HashMap<>();
		List<Menu> list = new ArrayList<>();

		try {
			list = service.findMenu(stNum);
			map.put("list", list);
			map.put("status", 1);


		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

	@PostMapping("loca/{stNum}")
	public Map<String, Object> findLoca(@PathVariable int stNum) {
		Map<String, Object> map = new HashMap<>();

		try {
			Store s = service.selectByNo(stNum);
			map.put("loca", s.getStLoca());
			map.put("name", s.getStName());
			map.put("status", 1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

	@PutMapping("{stNum}")
	public Map<String, Object> confirmStore(@PathVariable int stNum) {
		Map<String, Object> map = new HashMap<>();
		try {
			service.confirm(stNum);
			map.put("status", 1);
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;

	}
	
	
	@GetMapping("mylist/{memId}")
	public Map<String, Object> mylist(@PathVariable String memId) {
		Map<String, Object> map = new HashMap<>();
		List<Store> list = new ArrayList<>();
		
		try {
			list = service.selectById(memId);
			map.put("list", list); 
			map.put("status", 1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	
	@PostMapping("list/{cateNum}/{currentPage}") 
	public Map<String,Object> list(@PathVariable int cateNum, @PathVariable int currentPage){
		Map<String,Object> map = new HashMap<>();
		List<Menu> list = new ArrayList<>();
		PageBean<Store> pb;
		try {
			pb = service.getPageBeanByCate(currentPage,cateNum);
			map.put("status", 1);
			map.put("pb", pb);
			
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

	@PostMapping("storeload/{stNum}")
	@ResponseBody
	public ResponseEntity<?> selectByStoreNum(@PathVariable int stNum) throws FindException{
		Map<String, Object>map = new HashMap<>();
		List<Menu> list = new ArrayList<>();
		
		List<Store> store = service.selectStoreNo(stNum);
		
		
		map.put("store", store);
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}
}