package kr.spring.houseboard.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.spring.houseboard.vo.HouseLikeVO;
import kr.spring.houseboard.vo.HouseVO;
import kr.spring.houseboard.vo.PaymentVO;
import kr.spring.houseboard.vo.RateVO;
import kr.spring.user.vo.HitVO;
import kr.spring.user.vo.UserVO;

public interface HouseService {
	//집 매물 번호 생성
	public int amarketNumSelect();
	//집 디테일 저장
	public void houseDetailInsert(HouseVO houseVO);
	//집 정보 저장
	public void houseInsert(HouseVO houseVO);
	//집 목록 불러오기
	public List<HouseVO> selectList(Map<String,Object> map);
	//집 목록 갯수
	public int selectRowCount(Map<String,Object> map);
	//집 정보 불러오기
	public HouseVO selectHouse(int market_num);
	//판매글 올린 사용자의 정보
	public UserVO selectSellerInfo(int market_num);
	//집 정보 업데이트
	public void UpdateMarket(HouseVO houseVO);
	//집 삭제
	public void DeleteMarketDetail(@Param("user_num") Integer user_num, @Param("market_num") int market_num);
	//집 조회수
	public void marketHit(int market_num);
	//좋아요 누른 목록 보기
	public HouseLikeVO selectLike(@Param("user_num") Integer user_num,@Param("market_num") Integer market_num);
	//좋아요 취소
	public void deleteLike(int houselike_num);
	//좋아요 클릭
	public void insertLike(@Param("user_num") Integer user_num,@Param("market_num") Integer market_num);
	//좋아요 갯수
	public int selectLikeCount(Integer market_num);
	//결제한 방 저장
	public void insertPayment(@Param("market_num")int market_num, @Param("sum_price")int sum_price,@Param("user_num")Integer user_num,@Param("startdate")Date startdate,@Param("enddate")Date enddate);
	//예약한 집 리스트
	public List<PaymentVO> selectListPayment(Map<String,Object> map);
	//예약한 집 갯수
	public int selectRowCountPayment(Map<String,Object> map);
	//예약한 집 예약 취소
	public void cencelUpdate(@Param("user_num") Integer user_num,@Param("date_num") int date_num);
	//예약한 집 예약 다시시작
	public void cencelUpdateReset(@Param("user_num") Integer user_num,@Param("date_num") int date_num);
	//예약한 집 목록삭제
	public void deleteReservation(@Param("user_num") Integer user_num,@Param("date_num") int date_num);
	//date_num 찾기
	public int selectDateNum(int market_num);
	
	//rate 점수 보내기
	public void insertRate(RateVO vo);
	//rate 목록 불러오기
	public List<RateVO> selectListRate(Map<String,Object> map);
	//rate 목록 갯수
	public int selectRowCountRate(Map<String,Object> map);
	//댓글 개수
	public int selectRateCount(int market_num);
	//날짜별로 모든 조회수 합계
	public int houseAllHitCount();
	//월별 총 조회수를 ahitdate 테이블에 저장
	public void insertHitMonth(@Param("hit") int hit,@Param("month") int month);
	//월별 총 조회수 조회하기
	public List<HitVO> selectHitMonth();
	//수익 저장하기 
	public void incomeInsert(@Param("sumprice") int sumprice,@Param("user_num") Integer user_num, @Param("market_num") int market_num);
	//수익 더하기
	public void incomeUpdate(@Param("sumprice") int sumprice, @Param("market_num") int market_num);
	//aincome 테이블에 해당 market_num이 있는지 확인
	public int incomeSelect(int market_num);
	//유저 넘으로 총액 조회하기
	public int incomePriceSelect(Integer user_num);
}
