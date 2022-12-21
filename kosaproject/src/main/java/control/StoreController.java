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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
public class StoreController{

	@Autowired
	private StoreService service;

	@PostMapping(value="new", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart           List<MultipartFile> files, 
			@RequestParam("Store") String strStore) throws AddException{

		try {
			ObjectMapper mapper = new ObjectMapper();
			Store store = mapper.readValue(strStore, new TypeReference<Store>() {});
			System.out.println(store);
			String id = (String)session.getAttribute("id");
			store.setOwnerId(id);
			int i = 0;
			int storeNo =  service.addStore(store);

			for(MultipartFile f: files) {
				Attach.upload(storeNo,f, store.getStMenuList().get(i).getMenuName());
				i++;
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
	
	@GetMapping("submitted/{currentPage}")
	public Map<String, Object> submitted(@PathVariable int currentPage){
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
	
	@PostMapping("{storeNo}")
	public Map<String, Object> selectByNo(@PathVariable int storeNo){
		Map<String, Object> map = new HashMap<>();
		try {
			Store s = service.selectByNo(storeNo);
			map.put("store", s);
			map.put("status", 1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	@PostMapping("menu/{stNum}")
	public Map<String, Object> selectMenu(@PathVariable int stNum){
		Map<String, Object> map = new HashMap<>();
		List<Menu> list = new ArrayList<>();
		
		try {
			list = service.findMenu(stNum);
			map.put("list", list);
			map.put("status", 1);
			
			List<String> menuFile = new ArrayList<>();
			String saveDirectory = "D:\\MyBACK\\kosafront\\src\\main\\webapp\\project_image\\menu";
			File dir = new File(saveDirectory);
			String[] allFileNames = dir.list();
			for(Menu m : list) {				
				for(String fn: allFileNames) {
					if(fn.startsWith(stNum+"_"+m.getMenuName())){
						menuFile.add(fn);
						break;
					}
				}
			}
			map.put("menuFile",menuFile);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	@PostMapping("loca/{stNum}")
	public Map<String, Object> findLoca(@PathVariable int stNum){
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
	public Map<String, Object> confirmStore(@PathVariable int stNum){
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}