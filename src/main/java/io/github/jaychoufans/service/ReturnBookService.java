package io.github.jaychoufans.service;

import io.github.jaychoufans.model.LendReturnList;

import java.text.ParseException;
import java.util.List;

public interface ReturnBookService {

	List<LendReturnList> selectBookInfoAndUserByBookId(Integer bookId) throws ParseException;

	int returnBook(LendReturnList lendReturnList) throws ParseException;

}
