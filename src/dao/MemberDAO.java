package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBUtil;
import dto.MemberDTO;

public class MemberDAO {
	public List<MemberDTO> getMemberList(){                 //모든 회원리스트
		List<MemberDTO> memberList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM member;");
			rs = stmt.executeQuery();
			while(rs.next()){
				MemberDTO member = new MemberDTO();
				member.setMno(rs.getInt(1));
				member.setMid(rs.getString(2));
				member.setMpasswd(rs.getString(3));
				member.setMname(rs.getString(4));
				member.setMtoken(rs.getString(5));
				memberList.add(member);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn,stmt,rs);
		}
		
		return memberList;
	}
	
	public List<MemberDTO> searchMemberList(String mid){           // 문자열포함된 아이디로 리스트 검색
		List<MemberDTO> memberList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM member WHERE mid LIKE ? OR "
					+ "mid LIKE ? OR mid LIKE ? OR mid LIKE ?;");
			stmt.setString(1, mid);
			stmt.setString(2, mid+"_");
			stmt.setString(3, mid+"__");
			stmt.setString(4, mid+"___");
			rs = stmt.executeQuery();
			while(rs.next()){
				MemberDTO member = new MemberDTO();
				member.setMno(rs.getInt(1));
				member.setMid(rs.getString(2));
				member.setMpasswd(rs.getString(3));
				member.setMname(rs.getString(4));
				member.setMtoken(rs.getString(5));
				memberList.add(member);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stmt, rs);
		}
		return memberList;
	}
	
	public MemberDTO getMember(String mid){                    //회원 아이디로 검색
		MemberDTO member = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM member WHERE mid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mid);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				member = new MemberDTO();
				member.setMno(rs.getInt(1));
				member.setMid(rs.getString(2));
				member.setMpasswd(rs.getString(3));
				member.setMname(rs.getString(4));
				member.setMtoken(rs.getString(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stmt, rs);
		}
		
		return member;
	}
	
	public MemberDTO getMember(int mno){                     //회원번호로 검색
		MemberDTO member = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM member WHERE mno=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mno);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				member = new MemberDTO();
				member.setMno(rs.getInt(1));
				member.setMid(rs.getString(2));
				member.setMpasswd(rs.getString(3));
				member.setMname(rs.getString(4));
				member.setMtoken(rs.getString(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stmt, rs);
		}
		
		return member;
	}
	
	public int setMember(MemberDTO member){                    //회원 가입
		int resultCount = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO member(mid, mpasswd, mname) VALUES(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMid());
			stmt.setString(2, member.getMpasswd());
			stmt.setString(3, member.getMname());
			
			resultCount = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stmt);
		}
		return resultCount;
	}
	
	public int setToken(int mno, String mtoken){
		int resultCount = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "UPDATE member SET mtoken=? WHERE mno=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mtoken);
			stmt.setInt(2, mno);
			
			resultCount = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stmt);
		}
		return resultCount;
		
	}
	public List<MemberDTO> getPartMemberList(int crno, int mno){                 //모든 회원리스트
		List<MemberDTO> memberList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "SELECT m.mno, m.mid, m.mtoken FROM participate AS p INNER JOIN member AS m ON "
					+ "p.mno = m.mno WHERE crno=? AND m.mno NOT IN (?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, crno);
			stmt.setInt(2, mno);
			rs = stmt.executeQuery();
			while(rs.next()){
				MemberDTO member = new MemberDTO();
				member.setMno(rs.getInt(1));
				member.setMid(rs.getString(2));
				member.setMtoken(rs.getString(3));
				memberList.add(member);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn,stmt,rs);
		}
		
		return memberList;
	}
}
