package io.github.jaychoufans.service.impl;

import io.github.jaychoufans.dao.LendReturnListMapper;
import io.github.jaychoufans.model.LendReturnList;
import io.github.jaychoufans.service.LendBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("lendBookService")
public class LendBookServiceImpl implements LendBookService {

	@Resource
	private LendReturnListMapper lendReturnListMapper;

	@Override
	public int lendBook(LendReturnList lendReturnList) throws ParseException {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currdate = format.format(d);
		// 借出日期
		Date lendDate = format.parse(currdate);
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, lendReturnList.getShouldLendDays());// num为增加的天数，可以改变的
		d = ca.getTime();
		String enddate = format.format(d);
		// 根据应借天数计算应还时间
		Date shouldReturnDate = format.parse(enddate);
		LendReturnList list = LendReturnList.builder().bookId(lendReturnList.getBookId())
				.userId(lendReturnList.getUserId()).lendDate(lendDate)
				.shouldLendDays(lendReturnList.getShouldLendDays()).shouldReturnDate(shouldReturnDate).build();
		return lendReturnListMapper.insertSelective(list);
	}

	@Override
	public List<LendReturnList> selectLendReturnRecordByUserId(Map<String, Object> map) throws ParseException {
		List<LendReturnList> lendReturnLists = lendReturnListMapper.selectLendReturnRecordByUserId(map);
		for (LendReturnList lendReturnList : lendReturnLists) {
			Date shouldReturnDate = lendReturnList.getShouldReturnDate();
			Date returnDate = lendReturnList.getReturnDate();
			if (returnDate == null) {
				returnDate = new Date();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			shouldReturnDate = sdf.parse(sdf.format(shouldReturnDate));
			returnDate = sdf.parse(sdf.format(returnDate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(shouldReturnDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(returnDate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			lendReturnList.setExtendedDays(Integer.parseInt(String.valueOf(between_days)));

			lendReturnList.setBookNames(lendReturnList.getBookInfo().getBookName());
		}
		return lendReturnLists;
	}

	@Override
	public int getTotalRecord(Map<String, Object> map) {
		return lendReturnListMapper.getTotalRecord(map);
	}

}
