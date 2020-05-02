package io.github.jaychoufans.service;

import io.github.jaychoufans.model.LendReturnList;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface LendBookService {

	int lendBook(LendReturnList lendReturnList) throws ParseException;

	List<LendReturnList> selectLendReturnRecordByUserId(Map<String, Object> map) throws ParseException;

	int getTotalRecord(Map<String, Object> map);

}