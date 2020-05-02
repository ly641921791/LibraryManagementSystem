package io.github.jaychoufans.controller;

import io.github.jaychoufans.annotation.LoginRequired;
import io.github.jaychoufans.common.PageBean;
import io.github.jaychoufans.common.DataGridDataSource;
import io.github.jaychoufans.common.JsonData;
import io.github.jaychoufans.model.BookInfo;
import io.github.jaychoufans.model.BookType;
import io.github.jaychoufans.service.BookInfoService;
import io.github.jaychoufans.service.BookTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookinfo")
public class BookInfoController {

	@Resource
	private BookInfoService bookInfoService;

	@Resource
	private BookTypeService bookTypeService;

	@PostMapping("/save")
	@LoginRequired
	public JsonData saveBookInfo(BookInfo bookInfo) {
		int count = bookInfoService.saveBookInfo(bookInfo);
		if (count > 0) {
			return JsonData.success(count, "新增成功");
		}
		else {
			return JsonData.fail("新增失败");
		}
	}

	@PutMapping("/update")
	@LoginRequired
	public JsonData updateBookInfo(BookInfo bookInfo) {
		int count = bookInfoService.updateBookInfo(bookInfo);
		if (count > 0) {
			return JsonData.success(count, "编辑成功");
		}
		else {
			return JsonData.fail("编辑失败");
		}
	}

	@DeleteMapping("/delete")
	@LoginRequired
	public JsonData deleteBookInfo(Integer bookId) {
		int count = bookInfoService.deleteBookInfo(bookId);
		if (count > 0) {
			return JsonData.success(count, "删除成功");
		}
		else {
			return JsonData.fail("删除失败");
		}
	}

	@PostMapping("/list")
	@LoginRequired
	public DataGridDataSource<BookInfo> bookInfoList(
			@RequestParam(value = "bookIsbn", required = false, defaultValue = "") String bookIsbn,
			@RequestParam(value = "bookName", required = false, defaultValue = "") String bookName,
			@RequestParam(value = "bookAuthor", required = false, defaultValue = "") String bookAuthor,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
		PageBean pageBean = new PageBean(page, rows);
		Map<String, Object> map = new HashMap<>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("bookIsbn", "%" + bookIsbn + "%");
		map.put("bookName", "%" + bookName + "%");
		map.put("bookAuthor", "%" + bookAuthor + "%");
		List<BookInfo> bookInfoList = bookInfoService.selectBookInfoList(map);

		for (BookInfo bookInfo : bookInfoList) {
			List<BookType> bookTypeList = bookTypeService.selectBookTypeListByBookTypeId(bookInfo.getBookType());
			for (BookType bookType : bookTypeList) {
				bookInfo.setTypes(bookType.getBookTypeName());
			}
		}
		int totalBook = bookInfoService.getTotalBook(map);
		DataGridDataSource<BookInfo> bookInfoDataGridDataSource = new DataGridDataSource<>();
		bookInfoDataGridDataSource.setTotal(totalBook);
		bookInfoDataGridDataSource.setRows(bookInfoList);
		return bookInfoDataGridDataSource;
	}

	@GetMapping("/detail")
	@LoginRequired
	public JsonData bookInfoDetail(Integer bookId) {
		List<BookInfo> bookInfos = bookInfoService.selectBookById(bookId);
		for (BookInfo bookInfo : bookInfos) {
			List<BookType> bookTypeList = bookTypeService.selectBookTypeListByBookTypeId(bookInfo.getBookType());
			for (BookType bookType : bookTypeList) {
				bookInfo.setTypes(bookType.getBookTypeName());
			}
		}
		return JsonData.success(bookInfos);
	}

	@PostMapping("/info")
	@LoginRequired
	public JsonData bookInfo(Integer bookId) {
		return JsonData.success(bookInfoService.selectBookInfoById(bookId));
	}

}
