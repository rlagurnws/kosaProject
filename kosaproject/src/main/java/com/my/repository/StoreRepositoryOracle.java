//package com.my.repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.my.exception.AddException;
//import com.my.exception.FindException;
//import com.my.sql.MyConnection;
//import com.my.vo.Food;
//import com.my.vo.Notice;
//import com.my.vo.Store;
//
//public class StoreRepositoryOracle implements StoreRepository{
//
//	@Override
//	public void insert(Store store) throws AddException {
//		//db연결관련
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = MyConnection.getConnection();
//			String StoreSQL = "INSERT "
//					+ "INTO ST(st_num, st_des, st_hits, st_date, st_name,"
//					+ "st_score, st_loca, st_phone, cate, oner_id, st_res_no, st_post_cnt, st_st)"
//					+ " VALUES(ST_SEQ.nextVal,?,0,SYSDATE,?,"
//					+ "0,?,?,?,?,'1',0,0)";
//			pstmt = conn.prepareStatement(StoreSQL);
//			pstmt.setString(1, store.getSt_des());
//			pstmt.setString(2, store.getSt_name());
//			pstmt.setString(3, store.getSt_loca());
//			pstmt.setString(4, store.getSt_phone());
//			pstmt.setInt(5, store.getCate_num());
//			pstmt.setString(6, store.getOner_id());
//			pstmt.executeUpdate();
//			
//			
//			String foodSQL = "INSERT INTO FOOD(st_num, food_name, food_price)"
//					+ " VALUES(st_seq.currval,?,?)";
//				
//			pstmt = conn.prepareStatement(foodSQL);
//			List<Food> foodList = store.getSt_menuList();
//			
//			
//			for(Food f: foodList) {
//				pstmt.setString(1, f.getFood_name());
//				pstmt.setInt(2, f.getFood_price());
//				pstmt.executeUpdate();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			MyConnection.close(pstmt, conn);
//		}
//		
//	}
//
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
//}
