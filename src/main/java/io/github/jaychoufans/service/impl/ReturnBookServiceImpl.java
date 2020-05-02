package io.github.jaychoufans.service.impl;

import io.github.jaychoufans.dao.LendReturnListMapper;
import io.github.jaychoufans.model.LendReturnList;
import io.github.jaychoufans.service.ReturnBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("returnBookService")
public class ReturnBookServiceImpl implements ReturnBookService {

	@Resource
	private LendReturnListMapper lendReturnListMapper;

	@Override
	public List<LendReturnList> selectBookInfoAndUserByBookId(Integer bookId) throws ParseException {
		List<LendReturnList> lendReturnLists = lendReturnListMapper.selectBookInfoAndUserByBookId(bookId);
		for (LendReturnList lendReturnList : lendReturnLists) {
			Date shouldReturnDate = lendReturnList.getShouldReturnDate();
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			shouldReturnDate = sdf.parse(sdf.format(shouldReturnDate));
			now = sdf.parse(sdf.format(now));
			Calendar cal = Calendar.getInstance();
			cal.setTime(shouldReturnDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(now);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			lendReturnList.setExtendedDays(Integer.parseInt(String.valueOf(between_days)));
		}
		return lendReturnLists;
	}

	@Override
	public int returnBook(LendReturnList lendReturnList) throws ParseException {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currdate = format.format(d);
		// 归还日期
		Date returnDate = format.parse(currdate);
		LendReturnList list = LendReturnList.builder().lendReturnId(lendReturnList.getLendReturnId())
				.returnDate(returnDate).isDamage(lendReturnList.getIsDamage())
				.damageDegree(lendReturnList.getDamageDegree()).damageNote(lendReturnList.getDamageNote()).build();
		return lendReturnListMapper.updateByPrimaryKeySelective(list);
	}

}
