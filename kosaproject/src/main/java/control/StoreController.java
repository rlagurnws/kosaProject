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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.my.service.StoreService;
import com.my.util.Attach;
import com.my.vo.Notice;
import com.my.vo.Store;

@RestController
@Controller
@RequestMapping("store/*")
public class StoreController{

	@Autowired
	private StoreService service;

	@PostMapping(value="AddStore", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart           List<MultipartFile> files, 
			@RequestParam("Store") String strStore) throws AddException{
		//Store store) throws AddException{
		System.out.println("in control files.size = " + files.size());
		System.out.println(strStore);

		try {
			ObjectMapper mapper = new ObjectMapper();
			Store store = mapper.readValue(strStore, new TypeReference<Store>() {});
			System.out.println(store);
			//		System.out.println(store.getStDes());
			String loginedId = (String)session.getAttribute("loginedId");
			//store.setOwnerId(loginedId);
			store.setOwnerId("id1");
			

			int storeNo =  service.addStore(store);
			//int storeNo=1;



			for(MultipartFile f: files) {
				Attach.upload(storeNo,f);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}

	}

//	@GetMapping(value="list/{currentPage}")
//	public ResponseEntity<?> list(@PathVariable int currentPage) throws FindException{
//		PageBean<Store> pb = service.getPageBean(currentPage);
//		return new ResponseEntity<>(pb, HttpStatus.OK);
//	}
	
	@GetMapping("list/{currentPage}")
	public Map<String,Object> list(@PathVariable int currentPage) throws FindException{
		Map<String,Object> map = new HashMap<>();
		PageBean<Store> pb = null;
		pb = service.getPageBean(currentPage);
		map.put("status", 1);
		map.put("pb", pb);
		return map;
	}
	
	
}