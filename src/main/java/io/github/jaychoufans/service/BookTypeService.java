package io.github.jaychoufans.service;

import io.github.jaychoufans.model.BookType;

import java.util.List;

public interface BookTypeService {

	List<BookType> queryAllBookType();

	List<BookType> selectBookTypeListByBookTypeId(Integer bookTypeId);

	int saveBookType(BookType bookType);

	int updateBookType(BookType bookType);

	int deleteBookType(Integer bookTypeId);

}