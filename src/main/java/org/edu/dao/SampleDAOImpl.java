package org.edu.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.edu.vo.MemberVO;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDAOImpl implements IF_SampleDAO {

	@Inject
	private SqlSession sqlSession;
	//오바라이드-다형성
	@Override
	public void insertMember(MemberVO vo) {
		sqlSession.insert("org.edu.dao.IF_SampleDAO.insertMember", vo);		
	}

	@Override
	public List<MemberVO> selectMember() {
		return sqlSession.selectList("org.edu.dao.IF_SampleDAO.selectMember");
	}

	@Override
	public void updateMember(MemberVO vo) {
		sqlSession.update("org.edu.dao.IF_SampleDAO.updateMember", vo);		
	}

	@Override
	public void deleteMember(String userid) {
		sqlSession.delete("org.edu.dao.IF_SampleDAO.deleteMember", userid);		
	}

}
