package io.github.jaychoufans.service.impl;

import com.google.common.base.Preconditions;
import io.github.jaychoufans.exception.ParamException;
import io.github.jaychoufans.dao.BookTypeMapper;
import io.github.jaychoufans.model.BookType;
import io.github.jaychoufans.service.BookTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bookTypeService")
public class BookTypeServiceImpl implements BookTypeService {

	@Resource
	private BookTypeMapper bookTypeMapper;

	@Override
	public List<BookType> queryAllBookType() {
		return bookTypeMapper.queryAllBookType();
	}

	@Override
	public List<BookType> selectBookTypeListByBookTypeId(Integer bookTypeId) {
		return bookTypeMapper.selectBookTypeListByBookTypeId(bookTypeId);
	}

	@Override
	public int saveBookType(BookType bookType) {
		if (checkBookTypeNameExist(bookType.getBookTypeName(), bookType.getBookTypeId())) {
			throw new ParamException("分类名称已经存在");
		}
		BookType bookTypes = BookType.builder().bookTypeName(bookType.getBookTypeName())
				.bookTypeParentId(bookType.getBookTypeParentId()).bookTypeNote(bookType.getBookTypeNote()).build();
		return bookTypeMapper.insertSelective(bookTypes);
	}

	@Override
	public int updateBookType(BookType bookType) {
		if (checkBookTypeNameExist(bookType.getBookTypeName(), bookType.getBookTypeId())) {
			throw new ParamException("分类名称已经存在");
		}
		BookType before = bookTypeMapper.selectByPrimaryKey(bookType.getBookTypeId());
		Preconditions.checkNotNull(before, "需更新分类不存在");
		BookType after = BookType.builder().bookTypeId(bookType.getBookTypeId())
				.bookTypeName(bookType.getBookTypeName()).bookTypeParentId(bookType.getBookTypeParentId())
				.bookTypeNote(bookType.getBookTypeNote()).build();
		return bookTypeMapper.updateByPrimaryKeySelective(after);
	}

	@Override
	public int deleteBookType(Integer bookTypeId) {
		BookType before = bookTypeMapper.selectByPrimaryKey(bookTypeId);
		Preconditions.checkNotNull(before, "需删除的分类不存在");
		return bookTypeMapper.deleteByPrimaryKey(bookTypeId);
	}

	public boolean checkBookTypeNameExist(String bookTypeName, Integer bookTypeId) {
		return bookTypeMapper.countByBookTypeName(bookTypeName, bookTypeId) > 0;
	}

}
