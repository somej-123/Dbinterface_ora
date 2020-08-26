package org.edu.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.edu.dao.IF_SampleDAO;
import org.edu.service.IF_SampleService;
import org.edu.vo.MemberVO;
// import org.edu.dao.SampleSelectProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration //JUnit과 AOP동시사용 에러 처리를 위해서 추가
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class SampleMapperTest {
	
	/**
	 * hsql 사용시 아래 설정은 무시하셔도 됩니다.
	 * 실습시 Mysql 에 아래 3가지 쿼리를 실행 하고 작업 시작 합니다.
	 * CREATE SCHEMA `interface` DEFAULT CHARACTER SET utf8 ;
	 * create table member
	(
	 userid varchar(50) not null
	    ,userpw varchar(50) not null
	    ,username varchar(50) not null
	    ,email varchar(100)
	    ,regdate timestamp default now()
	    ,updatedate timestamp default now()
	    ,primary key(userid)
	)
	* INSERT INTO member VALUES ('user2','1234','kimilguk','user02@test.com',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
	
	* 오라클 실습시(아래)
	* create table member
	(
	 userid varchar(50) not null
	    ,userpw varchar(50) not null
	    ,username varchar(50) not null
	    ,email varchar(100)
	    ,regdate DATE DEFAULT SYSDATE NOT NULL
	    ,updatedate DATE DEFAULT SYSDATE NOT NULL
	    ,CONSTRAINT MEMBERS_PK PRIMARY KEY (userid)
	)	
	* INSERT INTO member VALUES ('user2','1234','kimilguk','user02@test.com',SYSDATE,SYSDATE);
	* 프로젝트폴더경로 DbInterface/src/main/webapp/WEB-INF/spring/root-context.xml (Mysql서버접속정보 변경해야 합니다.)
	*/
	
	// interface 로 Mybatis 쿼리 사용 DI처리(Dependency Injcetion)
	@Inject
	private DataSource ds;
	
	@Inject
	private IF_SampleService sampleService; //인터페이스를 실행가능하게 mapper변수로 지정.
	
	@Test
	public void testJunit() {
		System.out.println("Junit테스트 확인");
	}
	
	@Test
	public void testDbConnect() throws SQLException {
		Connection con = ds.getConnection();
		System.out.println("데이터베이스 커넥션 결과:" + con);
	}
		
	@Test
	public void testInsertMember() throws Exception {
		int randomInt = (int) (Math.random()*100);
		testSelectMember();
		System.out.println("위쪽은 입력 전 리스트 입니다.");
		MemberVO vo = new MemberVO();
		vo.setUserid("user_" + randomInt);
		vo.setUserpw("1234");
		vo.setUsername("각시탈");
		vo.setEmail("user10@test.com");
		sampleService.insertMember(vo);
		System.out.println("아래쪽은 입력 후 리스트 입니다.");
		testSelectMember();
	}
	@Test
	public void testSelectMember() throws Exception {
		List<MemberVO> list = sampleService.selectMember();
		int cnt = 1;
		for(MemberVO vo:list) {
			System.out.println(
					"번호: " + cnt++ + "번 " +
					"아이디: " + vo.getUserid() +
					" 암호: " + vo.getUserpw() +
					" 이름: " + vo.getUsername() +
					" 이메일: " + vo.getEmail()
					);
		}
	}
	
	@Test
	public void testUpdateMember() throws Exception {
		testSelectMember();
		System.out.println("위에서 수정 전 이름을 이름을 확인 하세요");
		
		MemberVO vo = new MemberVO();
		//수정은 여러개의 변수값을 변경하기 하기 때문에 MemberVO클래스 변수를 매개변수로 사용한다.
		vo.setUserid("user2");
		vo.setUserpw("1234");
		vo.setUsername("바야바");
		vo.setEmail("abc@abc.com");
		sampleService.updateMember(vo);
		
		System.out.println("아래는 수정 후 이름을 확인 하세요");
		testSelectMember();
	}
	
	@Test
	public void testDeleteMember() throws Exception {
		testSelectMember();
		sampleService.deleteMember("user_1");
		System.out.println("아래는 지운 후 회원리스트 입니다.");
		testSelectMember();
	}
		
}
