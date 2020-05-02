package io.github.jaychoufans.service;

import io.github.jaychoufans.model.BookInfo;

import java.util.List;
import java.util.Map;

public interface BookInfoService {

	int saveBookInfo(BookInfo bookInfo);

	int updateBookInfo(BookInfo bookInfo);

	int deleteBookInfo(Integer bookId);

	List<BookInfo> selectBookInfoList(Map<String, Object> map);

	int getTotalBook(Map<String, Object> map);

	List<BookInfo> selectBookById(Integer bookId);

	BookInfo selectBookInfoById(Integer bookId);

}
