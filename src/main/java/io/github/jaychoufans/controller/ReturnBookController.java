package io.github.jaychoufans.controller;

import io.github.jaychoufans.annotation.LoginRequired;
import io.github.jaychoufans.common.JsonData;
import io.github.jaychoufans.model.BookInfo;
import io.github.jaychoufans.model.LendReturnList;
import io.github.jaychoufans.service.BookInfoService;
import io.github.jaychoufans.service.ReturnBookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class ReturnBookController {

	@Resource
	private ReturnBookService returnBookService;

	@Resource
	private BookInfoService bookInfoService;

	@PostMapping("/bookInfoAndUserByBookId")
	@LoginRequired
	public JsonData selectBookInfoAndUserByBookId(Integer bookId) throws ParseException {

		List<LendReturnList> lendReturnLists = returnBookService.selectBookInfoAndUserByBookId(bookId);
		if (lendReturnLists.size() == 0) {
			return JsonData.fail("记录不存在");
		}
		else {
			return JsonData.success(lendReturnLists);
		}
	}

	@PostMapping("/returnBook")
	@LoginRequired
	public JsonData returnBook(LendReturnList lendReturnList) throws ParseException {
		int i = returnBookService.returnBook(lendReturnList);
		// 更新图书状态为正常
		BookInfo bookInfo = BookInfo.builder().bookId(lendReturnList.getBookId()).bookState(0).build();
		bookInfoService.updateBookInfo(bookInfo);
		if (i > 0) {
			return JsonData.success(i, "还书成功");
		}
		else {
			return JsonData.fail("还书失败");
		}
	}

}
