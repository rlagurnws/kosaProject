package com.my.repository;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;

import com.my.vo.Menu;

import com.my.vo.Store;

@Repository("storeRepository")
public class StoreRepositoryOracle implements StoreRepository{

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public int insert(Store store) throws AddException {
		SqlSession session = null;
		int stNum;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.my.mybatis.StoreMapper.insert",store);
			System.out.println(store.getStNum());
			session.close();
			
			List<Menu> foodList = store.getStMenuList();			
			for(Menu menu: foodList) {
				session = sqlSessionFactory.openSession();
				menu.setStNum(store.getStNum());
				session.insert("com.my.mybatis.StoreMapper.insertfood",menu);
			}
			return store.getStNum();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
	}

	@Override
	public List<Store> selectAll() throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Store> selectByCate(int cate) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Store> selectAll() throws FindException {
//		List<Store> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = MyConnection.getConnection();
//			String selectAllPageSQL = "SELECT *\r\n"
//					+ "FROM (SELECT rownum rn, a.* \r\n"
//					+ "        FROM (\r\n"
//					+ "                SELECT * FROM ST ORDER BY st_num\r\n"
//					+ "             )a ) ";
//			pstmt = conn.prepareStatement(selectAllPageSQL);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				int store_No = rs.getInt("ST_NUM");
//				String store_name = rs.getString("ST_NAME");
//				Store s = new Store();
//				s.setSt_num(store_No);
//				s.setSt_name(store_name);
//				list.add(s);
//			}
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}finally {
//			MyConnection.close(rs, pstmt, conn);
//		}
//	}
//	
//	public List<Store> selectByCate(int cate) throws FindException{
//		List<Store> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = MyConnection.getConnection();
//			String selectAllPageSQL = "SELECT ST_name, ST_num "
//									+ "FROM ST "
//									+ "WHERE CATE = ?";
//			pstmt = conn.prepareStatement(selectAllPageSQL);
//			pstmt.setInt(1, cate);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				int store_No = rs.getInt("ST_NUM");
//				String store_name = rs.getString("ST_NAME");
//				Store s = new Store();
//				s.setSt_num(store_No);
//				s.setSt_name(store_name);
//				list.add(s);
//			}
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}finally {
//			MyConnection.close(rs, pstmt, conn);
//		}
//	}
}
