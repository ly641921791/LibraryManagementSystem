package io.github.jaychoufans.service.impl;

import com.google.common.base.Preconditions;
import io.github.jaychoufans.dao.BookInfoMapper;
import io.github.jaychoufans.model.BookInfo;
import io.github.jaychoufans.service.BookInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("bookInfoService")
public class BookInfoServiceImpl implements BookInfoService {

	@Resource
	private BookInfoMapper bookInfoMapper;

	@Override
	public int saveBookInfo(BookInfo bookInfo) {
		BookInfo bookInfos = BookInfo.builder().bookIsbn(bookInfo.getBookIsbn()).bookName(bookInfo.getBookName())
				.bookAuthor(bookInfo.getBookAuthor()).bookPublish(bookInfo.getBookPublish())
				.bookPrice(bookInfo.getBookPrice()).bookState(bookInfo.getBookState()).bookType(bookInfo.getBookType())
				.bookShelf(bookInfo.getBookShelf()).bookIntroduction(bookInfo.getBookIntroduction()).build();
		return bookInfoMapper.insertSelective(bookInfos);
	}

	@Override
	public int updateBookInfo(BookInfo bookInfo) {

		BookInfo before = bookInfoMapper.selectByPrimaryKey(bookInfo.getBookId());
		Preconditions.checkNotNull(before, "需更新的图书不存在");
		BookInfo after = BookInfo.builder().bookId(bookInfo.getBookId()).bookIsbn(bookInfo.getBookIsbn())
				.bookName(bookInfo.getBookName()).bookAuthor(bookInfo.getBookAuthor())
				.bookPublish(bookInfo.getBookPublish()).bookPrice(bookInfo.getBookPrice())
				.bookState(bookInfo.getBookState()).bookType(bookInfo.getBookType()).bookShelf(bookInfo.getBookShelf())
				.bookIntroduction(bookInfo.getBookIntroduction()).build();
		return bookInfoMapper.updateByPrimaryKeySelective(after);
	}

	@Override
	public int deleteBookInfo(Integer bookId) {
		BookInfo before = bookInfoMapper.selectByPrimaryKey(bookId);
		Preconditions.checkNotNull(before, "需删除的图书不存在");
		return bookInfoMapper.deleteByPrimaryKey(bookId);
	}

	@Override
	public List<BookInfo> selectBookInfoList(Map<String, Object> map) {
		return bookInfoMapper.selectBookInfoList(map);
	}

	@Override
	public int getTotalBook(Map<String, Object> map) {
		return bookInfoMapper.getTotalBook(map);
	}

	@Override
	public List<BookInfo> selectBookById(Integer bookId) {
		return bookInfoMapper.selectBookById(bookId);
	}

	@Override
	public BookInfo selectBookInfoById(Integer bookId) {
		BookInfo before = bookInfoMapper.selectByPrimaryKey(bookId);
		Preconditions.checkNotNull(before, "图书不存在");
		return bookInfoMapper.selectByPrimaryKey(bookId);
	}

}
