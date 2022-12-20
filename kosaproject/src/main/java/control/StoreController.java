package control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.service.StoreService;
import com.my.util.Attach;
import com.my.vo.Member;
import com.my.vo.Store;

@RestController
@Controller
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}

	}
	//	public Map<String,Object> addstore(Store store){
	//		Map<String,Object> map = new HashMap<>();
	//		try {
	//			service.addStore(store);
	//			map.put("msg", "성공!");
	//		} catch (AddException e) {
	//			e.printStackTrace();
	//			map.put("msg", "실패!");
	//		}
	//		return map;	
	//	}
}