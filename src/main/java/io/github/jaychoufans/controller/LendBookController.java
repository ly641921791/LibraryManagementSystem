package io.github.jaychoufans.controller;

import io.github.jaychoufans.annotation.LoginRequired;
import io.github.jaychoufans.common.PageBean;
import io.github.jaychoufans.common.DataGridDataSource;
import io.github.jaychoufans.common.JsonData;
import io.github.jaychoufans.model.BookInfo;
import io.github.jaychoufans.model.LendReturnList;
import io.github.jaychoufans.model.User;
import io.github.jaychoufans.service.BookInfoService;
import io.github.jaychoufans.service.LendBookService;
import io.github.jaychoufans.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class LendBookController {

	@Resource
	private LendBookService lendBookService;

	@Resource
	private BookInfoService bookInfoService;

	@Resource
	private UserService userService;

	@PostMapping("/lendBook")
	@LoginRequired
	public JsonData lendBook(LendReturnList lendReturnList) throws ParseException {
		User user = userService.findUserByUserId(lendReturnList.getUserId());
		if (user == null) {
			return JsonData.fail("用户不存在");
		}
		if (user.getUserState() == 0) {
			return JsonData.fail("用户已被禁用,无法借阅");
		}
		BookInfo info = bookInfoService.selectBookInfoById(lendReturnList.getBookId());
		if (info == null) {
			return JsonData.fail("图书不存在");
		}
		if (info.getBookState() == 1) {
			return JsonData.fail("图书已被借出,无法借阅");
		}
		int i = lendBookService.lendBook(lendReturnList);
		// 更新图书状态为借出
		BookInfo bookInfo = BookInfo.builder().bookId(lendReturnList.getBookId()).bookState(1).build();
		bookInfoService.updateBookInfo(bookInfo);
		if (i > 0) {
			return JsonData.success(i, "借阅成功");
		}
		else {
			return JsonData.fail("借阅失败");
		}

	}

	@PostMapping("/lendreturnrecord")
	@LoginRequired
	public DataGridDataSource<LendReturnList> selectLendReturnRecordByUserId(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows, HttpSession session)
			throws ParseException {

		User currentUser = (User) session.getAttribute("user");
		PageBean pageBean = new PageBean(page, rows);
		Map<String, Object> map = new HashMap<>();
		map.put("userId", currentUser.getUserId());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<LendReturnList> lendReturnLists = lendBookService.selectLendReturnRecordByUserId(map);
		int totalRecord = lendBookService.getTotalRecord(map);
		DataGridDataSource<LendReturnList> list = new DataGridDataSource<>();
		list.setTotal(totalRecord);
		list.setRows(lendReturnLists);
		return list;
	}

}
